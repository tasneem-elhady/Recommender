package trial.MovieValidator;

import trial.Exceptions.InvalidMovieIdLettersException;
import trial.Exceptions.InvalidMovieIdNumbersException;

public class MovieIdValidator {
    public static void validate(String movieTitle, String movieId) throws InvalidMovieIdLettersException, InvalidMovieIdNumbersException {
        if (movieId.length() >= 3 && movieId.matches(".*\\d{3}$")) {
            String letters = movieId.substring(0, movieId.length() - 3);
            String numbers = movieId.substring(movieId.length() - 3);
        
            String[] words = movieTitle.split(" ");
            StringBuilder initials = new StringBuilder();
            for (String word : words) {
                if (!word.isEmpty())
                    initials.append(word.charAt(0));
            }
            if (!letters.equals(initials.toString()))
                throw new InvalidMovieIdLettersException(movieId);
        } else {
            throw new InvalidMovieIdNumbersException(movieId);
        }
    }
}
