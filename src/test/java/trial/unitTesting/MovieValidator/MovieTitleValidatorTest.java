package trial.unitTesting.MovieValidator;

import org.junit.jupiter.api.Test;
import trial.Exceptions.InvalidMovieTitleException;
import trial.MovieValidator.MovieTitleValidator;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MovieTitleValidatorTest {
    @Test 
    void testNoIssuesWithMovieTitle() {
        String movieTitle = "Se7en";
        assertDoesNotThrow(() ->{
            MovieTitleValidator.validate(movieTitle);
        });    
    }
    
    @Test 
    void testOneWordNotCapitalized() {
        String movieTitle = "se7en";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        }); 
    }
    
    @Test
    void testOneWordStartsWithNumber() {
        String movieTitle = "7esen";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        }); 
    }
    
        @Test 
    void testTwoWordsCap() {
        String movieTitle = "Se7en Movie";
        assertDoesNotThrow(() -> {
            MovieTitleValidator.validate(movieTitle);
        });    
    }
    
    @Test
    void testTwoWordsNoCapFirst() {
        String movieTitle = "se7en Movie";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }
    
    @Test
    void testTwoWordsNoCapSecond() {
        String movieTitle = "Se7en movie";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }
    
    @Test
    void testTwoWordsNumberFirst() {
        String movieTitle = "7esen Movie";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }
    
    @Test
    void testTwoWordsNumberSecond() {
        String movieTitle = "Se7en 7ovie";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }

    
    @Test
    void testMultipleWordsNoIssues() {
        String movieTitle = "The Shaw5hank Redemption";
        assertDoesNotThrow(() ->{
            MovieTitleValidator.validate(movieTitle);
        }); 
    }
    
    @Test
    void testMultipleWordsNoCapMiddle() {
        String movieTitle = "The shaw5hank Redepmption";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }

    @Test
    void testMultipleWordsNoCapFirst() {
        String movieTitle = "the Shaw5hank Redemption";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }

    @Test
    void testMultipleWordsNoCapLast() {
        String movieTitle = "The Shaw5hank redemption";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }

    @Test
    void testMultipleWordsNumberMiddle() {
        String movieTitle = "The 5hawshank Redemption";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }

    @Test
    void testMultipleWordsNumberFirst() {
        String movieTitle = "7he Shawshank Redemption";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }

    @Test
    void testMultipleWordsNumberLast() {
        String movieTitle = "The Shawshank 5edemption";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }

    @Test
    void testMultipleWordsNoCapAndNumbers() {
        String movieTitle = "The quick 5rown fox3";
        assertThrows(InvalidMovieTitleException.class, () -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }
    @Test
    void testConsecutiveSpaces() {
        String movieTitle = "The  Godfather";
        assertDoesNotThrow(() -> {
            MovieTitleValidator.validate(movieTitle);
        });
    }
}
