package trial.Exceptions;

public class InvalidMovieIdNumbersException extends InvalidMovieException {
    public InvalidMovieIdNumbersException(String movieId) {
        super("Movie Id numbers " + movieId + " are wrong (too few)");
    }
}
