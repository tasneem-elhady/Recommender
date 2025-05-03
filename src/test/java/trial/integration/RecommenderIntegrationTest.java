package trial.integration;

import org.junit.jupiter.api.Test;
import trial.Parser.MovieParser;
import trial.Parser.UserParser;
import trial.Recommender.MovieRecommender;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RecommenderIntegrationTest {

    @Test
    void fullResource_recommendsByGenreCorrectly() throws Exception {
        String moviesRaw = Files.readString(
                Paths.get("src/test/java/trial/integration/movies.txt")
        );
        String usersRaw = Files.readString(
                Paths.get("src/test/java/trial/integration/users.txt")
        );

        MovieParser mp = new MovieParser(moviesRaw);
        mp.parseLines();
        UserParser up = new UserParser(usersRaw);
        up.parseLines();

        MovieRecommender rec = new MovieRecommender(
                mp.getMovieMap(),
                up.getUsersMap(),
                mp.getGenreMovies()
        );
        Map<String, List<String>> results = rec.generateRecommendations();

        List<String> johnRecs = results.get("100000001");
        assertNotNull(johnRecs, "John should have a recommendations list");
        assertFalse(johnRecs.contains("The Matrix"),
                "John should not get back a movie he already liked");
        assertTrue(johnRecs.contains("Jurassic Park"),
                "John should get Jurassic Park (another sci-fi)");


        List<String> charlieRecs = results.get("100000005");
        assertNotNull(charlieRecs, "Charlie should have recommendations");
        assertTrue(charlieRecs.contains("Pulp Fiction"),
                "Charlie should get Pulp Fiction (thriller/drama)");
        assertTrue(charlieRecs.contains("Titanic"),
                "Charlie should get Titanic (drama/romance)");

    }
}
