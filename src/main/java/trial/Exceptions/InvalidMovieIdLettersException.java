package trial.Exceptions;

public class InvalidMovieIdLettersException extends InvalidMovieException {
    public InvalidMovieIdLettersException(String movieId) {
        super("Movie Id letters " + movieId + " are wrong");
    }
}
