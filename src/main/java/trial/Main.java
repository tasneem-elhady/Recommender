package trial;

import trial.Exceptions.NonWrittenError;
import trial.Exceptions.WrittenError;
import trial.FileHandler.FILEReader;
import trial.FileHandler.FILEWriter;
import trial.Parser.MovieParser;
import trial.Parser.UserParser;
import trial.Recommender.MovieRecommender;

import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        FILEReader moviesFileReader = new FILEReader();
        FILEReader userFileReader = new FILEReader();
        moviesFileReader.readFile("src/main/java/trial/movies.txt");
        userFileReader.readFile("src/main/java/trial/users.txt");
        FILEWriter fileWriter = new FILEWriter();

        try {
            MovieParser MovieFile = new MovieParser(moviesFileReader.getContent());
            MovieFile.parseLines();
            UserParser UserFile = new UserParser(userFileReader.getContent());
            UserFile.parseLines();
            MovieRecommender recommender = new MovieRecommender(MovieFile.getMovieMap(),
                                                                UserFile.getUsersMap(),
                                                                MovieFile.getGenreMovies());
            String recommendationsOutput = recommender.getRecommendationsString();
            fileWriter.setContent(recommendationsOutput);
        } catch (NonWrittenError e) {
            fileWriter.setContent("");
            throw new RuntimeException(e.getMessage());
        }catch (WrittenError e){
            fileWriter.setContent(e.getMessage());
        }finally {
            System.out.println(fileWriter.getContent());
            fileWriter.writeFile("src/main/java/trial/recommendations.txt");
        }

    }
}