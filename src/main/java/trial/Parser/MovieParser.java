package trial.Parser;

import trial.Exceptions.DuplicateException;
import trial.Exceptions.InvalidFileFormatException;
import trial.Exceptions.WrittenError;
import trial.Movie;
import trial.MovieValidator.MovieValidator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * responsible for parsing movie data from a given input string.
 * It validates the input format, checks for duplicate movie IDs, and organizes movies by genre.
 */
public class MovieParser extends FileParser {
    private final Map<String, Movie> movieMap = new HashMap<>();
    private final Map<String, Set<String>> genreMovies = new HashMap<>();

    /**
     * Constructs a MovieParser and parses the input data.
     *
     * @param input the input string containing movie data
     * @throws InvalidFileFormatException if the input format is incorrect
     * @throws DuplicateException if a duplicate movie ID is found
     * @throws WrittenError if there is an error during movie validation
     */
    public MovieParser(String input) throws InvalidFileFormatException, DuplicateException, WrittenError {
        super(input);
        parseLines();
    }

    /**
     * Parses the input string to extract movie information and genres.
     *
     * @throws DuplicateException if a duplicate movie ID is found
     * @throws WrittenError if there is an error during movie validation
     */
    @Override
    protected void parseLines() throws DuplicateException, WrittenError {
        String[] lines = getLines();

        for (int i = 0; i < lines.length; i += 2) {
            String[] movieInfo = lines[i].split(",");
            String[] genres = lines[i + 1].split(",");

            Movie movie = validateAndCreateMovie(movieInfo, genres);

            if (movieMap.containsKey(movie.getId())) {
                throw new DuplicateException("Duplicate movie id found: " + movie.getId());
            }

            movieMap.put(movie.getId(), movie);
            addMovieToGenres(movie, genres);
        }
    }

    /**
     * Validates movie information and creates a Movie object.
     *
     * @param movieInfo an array containing movie title and ID
     * @param genres an array of genres associated with the movie
     * @return a validated Movie object
     * @throws WrittenError if there is an error during validation
     */
    private Movie validateAndCreateMovie(String[] movieInfo, String[] genres) throws WrittenError {
        try {
            return MovieValidator.validate(movieInfo, genres);
        } catch (Exception e) {
            throw new WrittenError("ERROR:" + e.getMessage());
        }
    }

    /**
     * Adds a movie to the genreMovies map, organizing movies by genre.
     *
     * @param movie the Movie object to be added
     * @param genres an array of genres associated with the movie
     */
    private void addMovieToGenres(Movie movie, String[] genres) {
        for (String genre : genres) {
            genreMovies.computeIfAbsent(genre, _ -> new HashSet<>()).add(movie.getId());
        }
    }

    /**
     * Returns the map of movies with their IDs as keys.
     *
     * @return a map of movie IDs to Movie objects
     */
    public Map<String, Movie> getMovieMap() {
        return movieMap;
    }

    /**
     * Returns the map of genres with sets of movie IDs.
     *
     * @return a map of genres to sets of movie IDs
     */
    public Map<String, Set<String>> getGenreMovies() {
        return genreMovies;
    }
}