package trial.MovieValidator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import trial.Exceptions.InvalidMovieIdLettersException;
import trial.Exceptions.InvalidMovieIdNumbersException;
import trial.Exceptions.InvalidMovieTitleException;
import trial.Movie;

public class MovieValidatorTest {
    @Test
    void testNoIssuesWithMovieData() {
        String[] movieInfo = {"The Shawshank Redemption", "TSR123"};
        String[] genres = {"Drama", "Thriller"};
        
        assertDoesNotThrow(() ->{
            Movie movie = new Movie(movieInfo[0], movieInfo[1], genres);
            Movie validated = MovieValidator.validate(movieInfo, genres);
            assertEquals(movie.title,validated.title);
            assertEquals(movie.id,validated.id);
            assertEquals(movie.genre,validated.genre);
        });    
    }

    @Test
    void testIssueWithMovieTitleOnly() {
        String[] movieInfo = {"The shawshank Redemption", "TSR123"};
        String[] genres = {"Drama", "Thriller"};
        
        InvalidMovieTitleException exception = assertThrows(InvalidMovieTitleException.class, () -> {
            MovieValidator.validate(movieInfo, genres);
        });
        assertEquals("Movie Title The shawshank Redemption is wrong", exception.getMessage());
    }
    
    @Test
    void testIssueWithMovieIdLettersOnly() {
        String[] movieInfo = {"The Shawshank Redemption", "TsR123"};
        String[] genres = {"Drama", "Thriller"};
        
        InvalidMovieIdLettersException exception = assertThrows(InvalidMovieIdLettersException.class, () -> {
            MovieValidator.validate(movieInfo, genres);
        });
        assertEquals("Movie Id letters TsR123 are wrong", exception.getMessage());
    }

    @Test
    void testIssueWithMovieIdNumbersOnly() {
        String[] movieInfo = {"The Shawshank Redemption", "TSR13"};
        String[] genres = {"Drama", "Thriller"};
        
        InvalidMovieIdNumbersException exception = assertThrows(InvalidMovieIdNumbersException.class, () -> {
            MovieValidator.validate(movieInfo, genres);
        });
        assertEquals("Movie Id numbers TSR13 are wrong (too few)", exception.getMessage());
    }
    
    @Test
    void testIssueWithMovieTitleAndId() {
        String[] movieInfo = {"The shawshank Redemption", "TsR23"};
        String[] genres = {"Drama", "Thriller"};
        
        InvalidMovieTitleException exception = assertThrows(InvalidMovieTitleException.class, () -> {
            MovieValidator.validate(movieInfo, genres);
        });
        assertEquals("Movie Title The shawshank Redemption is wrong", exception.getMessage());
    }
}
