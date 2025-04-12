package trial.Exceptions;

public class InvalidMovieTitleException extends InvalidMovieException {
    public InvalidMovieTitleException(String movieTitle) {
        super("Movie Title " + movieTitle + " is wrong");
    }
}
