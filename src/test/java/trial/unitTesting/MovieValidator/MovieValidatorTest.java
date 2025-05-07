    package trial.unitTesting.MovieValidator;

    import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
    import static org.junit.jupiter.api.Assertions.assertEquals;
    import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import trial.Exceptions.InvalidMovieIdLettersException;
import trial.Exceptions.InvalidMovieIdNumbersException;
import trial.Exceptions.InvalidMovieTitleException;
import trial.Movie;
import trial.MovieValidator.MovieValidator;

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



        // Test case: Empty title
        @Test
        void validate_EmptyTitle_ThrowsException() {
            String[] movieInfo = {"", "EMPTY123"};
            assertThrows(InvalidMovieTitleException.class,
                () -> MovieValidator.validate(movieInfo, new String[]{"Drama"}),
                "Expected exception for empty title"
            );
        }

        // Test case: Title with numbers/special chars
        @Test
        void validate_TitleWithNumbers_ThrowsException() {
            String[] movieInfo = {"Matrix 2", "MATRIX123"};
            assertThrows(InvalidMovieTitleException.class,
                () -> MovieValidator.validate(movieInfo, new String[]{"Sci-Fi"})
            );
        }

        // Test case: Minimum valid title (1 word)
        @Test
        void validate_SingleWordTitle_Works() {
            String[] movieInfo = {"Joker", "J123"};
            assertDoesNotThrow(() -> MovieValidator.validate(movieInfo, new String[]{"Thriller"}));
        }

        // Test case: Maximum valid title (e.g., 10 words)
        @Test
        void validate_LongTitle_Works() {
            String longTitle = "A B C D E F G H I J";
            String[] movieInfo = {longTitle, "ABCDEFGHIJ123"};
            assertDoesNotThrow(() -> MovieValidator.validate(movieInfo, new String[]{"Drama"}));
        }

        /**
         * Data Flow Annotations
            For each variable:
            * Defined (d): Where the variable gets its value.
            * Used (u): Where the variable is read/processed.
            * Killed (k): Where the variable is no longer needed.
         */

         // Test Case 1: Track title Data Flow
        @Test
        void validate_TitleDataFlow_DefinedUsedKilled() {
            // Define (d)
            String[] movieInfo = {"Inception", "I123"};
            String[] genres = {"Sci-Fi"};

            // Use (u) + Kill (k) verification
            Movie movie = assertDoesNotThrow(() -> MovieValidator.validate(movieInfo, genres));

            // Assert data flowed correctly
            assertEquals("Inception", movie.title, "Title not passed from input to output");
        }

        // Test Case 2: Track id Data Flow with Exception
        @Test
        void validate_IdDataFlow_UsedAndKilledByException() {
            // Define (d)
            String[] movieInfo = {"Inception", "invalidID"}; // Invalid ID

            // Use (u) + Kill (k) via exception
            InvalidMovieIdNumbersException ex = assertThrows(InvalidMovieIdNumbersException.class,
                () -> MovieValidator.validate(movieInfo, new String[]{"Sci-Fi"})
            );

            // Verify ID was used before being killed
            assertTrue(ex.getMessage().contains("invalidID"), 
                "ID was not processed before exception");
        }

    }
