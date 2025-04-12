package trial.MovieValidator;

import trial.Exceptions.InvalidMovieTitleException;

import trial.Movie;

public class MovieTitleValidator {
    public static void validate(String movieTitle) throws InvalidMovieTitleException {
        for (String word : movieTitle.split(" ")) {
            if (word.isEmpty() || !Character.isLetter(word.charAt(0)) || !Character.isUpperCase(word.charAt(0))) {
                throw new InvalidMovieTitleException(movieTitle);
            }
        }
    }
}
    
