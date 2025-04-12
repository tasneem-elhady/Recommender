package trial.Parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.mockito.MockedStatic;
import trial.Exceptions.DuplicateException;
import trial.Exceptions.InvalidFileFormatException;
import trial.Movie;
import trial.MovieValidator.MovieValidator;


import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;


class MovieParserTest {

    private static String validInput;
    private static String validInputMultipleOfTheSameGenre;
    private static String invalidFormatInputSingleInfo;
    private static String invalidFormatInputMoreThan2Info;
    private static String invalidFormatInputLessThan2Lines;
    private static String duplicateIdInput;
    private static Movie matrix;
    private static Movie pulpFiction;
    private static Movie Nemo;

    @BeforeAll
    static void setUp() {
        matrix = new Movie("The Matrix", "TM432", new String[]{"Action", "Sci-Fi"});
        pulpFiction = new Movie("Pulp Fiction", "PF567", new String[]{"Crime", "Drama"});
        Nemo = new Movie("Finding Nemo", "FN001", new String[]{"Animation","Adventure","Family","Action"});
        validInput = "The Matrix,TM432\nAction,Sci-Fi\nPulp Fiction,PF567\nCrime,Drama\n";
        validInputMultipleOfTheSameGenre = "The Matrix,TM432\n" +
                                            "Action,Sci-Fi\n" +
                                            "Pulp Fiction,PF567\n" +
                                            "Crime,Drama\n" +
                                            "Finding Nemo,FN001\n" +
                                            "Animation,Adventure,Family,Action";

        invalidFormatInputSingleInfo = "The Matrix,TM432\nAction,Sci-Fi\nPulp Fiction\nCrime,Drama\n";
        invalidFormatInputMoreThan2Info = "The Matrix,TM432\nAction,Sci-Fi\nPulp Fiction,PF567,1234\nCrime,Drama\n";
        invalidFormatInputLessThan2Lines = "The Matrix,TM432\nPulp Fiction\nCrime,Drama\n";
        duplicateIdInput = "The Matrix,TM432\nAction,Sci-Fi\nPulp Fiction,TM432\nCrime,Drama\n";

    }

    @Test
    void testInvalidFormatInputSingleInfo() {
        assertThrows(InvalidFileFormatException.class, () -> new MovieParser(invalidFormatInputSingleInfo));
    }
    @Test
    void testInvalidFormatInputMoreThan2Info() {
        assertThrows(InvalidFileFormatException.class, () -> new MovieParser(invalidFormatInputMoreThan2Info));
    }
    @Test
    void testInvalidFormatInputLessThan2Lines() {
        assertThrows(InvalidFileFormatException.class, () -> new MovieParser(invalidFormatInputLessThan2Lines));
    }


//    test parse lines

    @Test
    void testDuplicateIdInput() {
        try (MockedStatic<MovieValidator> mockedValidator = org.mockito.Mockito.mockStatic(MovieValidator.class)) {
            Movie matrix_d = new Movie("The Matrix", "TM432", new String[]{"Action", "Sci-Fi"});
            Movie pulpFiction_d = new Movie("Pulp Fiction", "TM432", new String[]{"Crime", "Drama"});
            mockedValidator.when(() -> MovieValidator.validate(
                            eq(new String[]{"The Matrix", "TM432"}),
                            eq(new String[]{"Action", "Sci-Fi"})))
                    .thenReturn(matrix_d);

            mockedValidator.when(() -> MovieValidator.validate(
                            eq(new String[]{"Pulp Fiction", "TM432"}),
                            eq(new String[]{"Crime", "Drama"})))
                    .thenReturn(pulpFiction_d);

            assertThrows(DuplicateException.class, () -> {
                MovieParser parser = new MovieParser(duplicateIdInput);
                parser.parseLines();
            });
        }
    }
    @Test
    void testValidInputMultipleOfTheSameGenre() throws Exception {
        try (MockedStatic<MovieValidator> mockedValidator = org.mockito.Mockito.mockStatic(MovieValidator.class)) {

            mockedValidator.when(() -> MovieValidator.validate(
                            eq(new String[]{"The Matrix", "TM432"}),
                            eq(new String[]{"Action", "Sci-Fi"})))
                    .thenReturn(matrix);

            mockedValidator.when(() -> MovieValidator.validate(
                            eq(new String[]{"Pulp Fiction", "PF567"}),
                            eq(new String[]{"Crime", "Drama"})))
                    .thenReturn(pulpFiction);

            mockedValidator.when(() -> MovieValidator.validate(
                            eq(new String[]{"Finding Nemo", "FN001"}),
                            eq(new String[]{"Animation","Adventure","Family","Action"})))
                    .thenReturn(Nemo);


            MovieParser parser = new MovieParser(validInputMultipleOfTheSameGenre);
            parser.parseLines();
            Map<String, Movie> movieMap = parser.getMovieMap();

            assertEquals(3, parser.getMovieMap().size());
            Movie matrix = movieMap.get("TM432");
            assertNotNull(matrix);
            assertEquals("The Matrix", matrix.getTitle());
            assertEquals("TM432", matrix.getId());

            Movie pulpFiction = movieMap.get("PF567");
            assertNotNull(pulpFiction);
            assertEquals("Pulp Fiction", pulpFiction.getTitle());
            assertEquals("PF567", pulpFiction.getId());

            // Test genreMovies
            Map<String, Set<String>> genreMovies = parser.getGenreMovies();
            assertEquals(7, genreMovies.size());

            Set<String> actionMovies = genreMovies.get("Action");
            assertNotNull(actionMovies);
            assertTrue(actionMovies.contains("TM432"));
            assertTrue(actionMovies.contains("FN001"));
            assertEquals(2, actionMovies.size());

        }
    }

    @Test
    void testValidInput() throws Exception {
        try (MockedStatic<MovieValidator> mockedValidator = org.mockito.Mockito.mockStatic(MovieValidator.class)) {

            mockedValidator.when(() -> MovieValidator.validate(
                            eq(new String[]{"The Matrix", "TM432"}),
                            eq(new String[]{"Action", "Sci-Fi"})))
                    .thenReturn(matrix);

            mockedValidator.when(() -> MovieValidator.validate(
                            eq(new String[]{"Pulp Fiction", "PF567"}),
                            eq(new String[]{"Crime", "Drama"})))
                    .thenReturn(pulpFiction);


            MovieParser parser = new MovieParser(validInput);
            parser.parseLines();
            Map<String, Movie> movieMap = parser.getMovieMap();

            assertEquals(2, parser.getMovieMap().size());
            Movie matrix = movieMap.get("TM432");
            assertNotNull(matrix);
            assertEquals("The Matrix", matrix.getTitle());
            assertEquals("TM432", matrix.getId());

            Movie pulpFiction = movieMap.get("PF567");
            assertNotNull(pulpFiction);
            assertEquals("Pulp Fiction", pulpFiction.getTitle());
            assertEquals("PF567", pulpFiction.getId());

            // Test genreMovies
            Map<String, Set<String>> genreMovies = parser.getGenreMovies();
            assertEquals(4, genreMovies.size());

            Set<String> actionMovies = genreMovies.get("Action");
            assertNotNull(actionMovies);
            assertTrue(actionMovies.contains("TM432"));

            Set<String> sciFiMovies = genreMovies.get("Sci-Fi");
            assertNotNull(sciFiMovies);
            assertTrue(sciFiMovies.contains("TM432"));

            Set<String> crimeMovies = genreMovies.get("Crime");
            assertNotNull(crimeMovies);
            assertTrue(crimeMovies.contains("PF567"));

            Set<String> dramaMovies = genreMovies.get("Drama");
            assertNotNull(dramaMovies);
            assertTrue(dramaMovies.contains("PF567"));
        }
    }

}