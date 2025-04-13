package trial.MovieValidator;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import trial.Exceptions.InvalidMovieIdLettersException;
import trial.Exceptions.InvalidMovieIdNumbersException;

public class MovieIdValidatorTest {
    @Test 
    void testNoIssuesWithMovieId() {
        String movieTitle = "Se7en", movieId = "S001";
        assertDoesNotThrow(() ->{
            MovieIdValidator.validate(movieTitle, movieId);
        });    
    }
    
    @Test 
    void testTooFewLettersInMovieId() {
        String movieTitle = "Se7en", movieId = "001";
        assertThrows(InvalidMovieIdLettersException.class, () ->{
            MovieIdValidator.validate(movieTitle, movieId);
        }); 
    }
    
    @Test 
    void testTooFewNumbersInMovieId() {
        String movieTitle = "Se7en", movieId = "S01";
        assertThrows(InvalidMovieIdNumbersException.class, () ->{
            MovieIdValidator.validate(movieTitle, movieId);
        }); 
    }
    @Test 
    void testTooManyNumbersInMovieId() {
        String movieTitle = "Se7en", movieId = "S0001";
        assertThrows(InvalidMovieIdLettersException.class, () ->{
            MovieIdValidator.validate(movieTitle, movieId);
        }); 
    }
    @Test 
    void testTooManyLettersInMovieId() {
        String movieTitle = "Se7en", movieId = "SS001";
        assertThrows(InvalidMovieIdLettersException.class, () ->{
            MovieIdValidator.validate(movieTitle, movieId);
        }); 
    }
    
    @Test 
    void testLowercaseLettersInMovieId() {
        String[] movieTitles = {"Se7en", "The Shawshank Redemption", "The Shawshank Redemption", "The Shawshank Redemption", "The Shawshank Redemption"};
        String[] movieIds = {"s001", "tSR001", "TsR001", "TSr001", "tSr001"};

        for (int i = 0; i < movieTitles.length; i++) {
            String title = movieTitles[i];
            String id = movieIds[i];
            assertThrows(InvalidMovieIdLettersException.class, () -> {
                MovieIdValidator.validate(title, id);
            }); 
        }
    }
    @Test 
    void testNumbersInMovieId() {
        String[] movieTitles = {"Se7en", "The Shawshank Redemption", "The Shawshank Redemption", "The Shawshank Redemption", "The Shawshank Redemption"};
        String[] movieIds = {"0001", "0SR001", "T0R001", "TS0001", "0S0001"};

        for (int i = 0; i < movieTitles.length; i++) {
            String title = movieTitles[i];
            String id = movieIds[i];
            assertThrows(InvalidMovieIdLettersException.class, () -> {
                MovieIdValidator.validate(title, id);
            }); 
        }
    }
    
    @Test 
    void testWrongLettersInMovieId() {
        String[] movieTitles = {"Se7en", "The Shawshank Redemption", "The Shawshank Redemption", "The Shawshank Redemption", "The Shawshank Redemption"};
        String[] movieIds = {"X001", "XSR001", "TXR001", "TSX001", "XSX001"};

        for (int i = 0; i < movieTitles.length; i++) {
            String title = movieTitles[i];
            String id = movieIds[i];
            assertThrows(InvalidMovieIdLettersException.class, () -> {
                MovieIdValidator.validate(title, id);
            }); 
        }
    }
}
