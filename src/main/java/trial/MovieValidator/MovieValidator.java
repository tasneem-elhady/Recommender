package trial.MovieValidator;

import trial.Exceptions.InvalidMovieException;
import trial.Movie;

public class MovieValidator {
    public static Movie validate(String[] movieInfo, String[] genres) throws InvalidMovieException {
        // movieInfo: a list [name of movie, id of movie]
        
        if (movieInfo.length > 2)
            throw new InvalidMovieException("Unexpected parameters after movie name and ID.");
        if (movieInfo.length < 2)
            throw new InvalidMovieException("Missing parameters from movie name and ID.");
        
        String title = movieInfo[0];
        String id = movieInfo[1];
        
        MovieTitleValidator.validate(title);
        MovieIdValidator.validate(title, id);
        
        Movie validated = new Movie(title, id, genres);
        return validated;        
    }
}
