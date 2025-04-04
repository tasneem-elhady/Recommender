package trial.Parser;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import trial.Exceptions.DuplicateException;
import trial.Exceptions.InvalidFileFormatException;
import trial.Movie;


import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class MovieParserTest {

    private static String validInput;
    private static String invalidFormatInput1;
    private static String invalidFormatInput2;
    private static String invalidFormatInput3;
    private static String duplicateIdInput;


    @BeforeAll
    static void setUp() {
        validInput = "The Matrix,TM432\nAction,Sci-Fi\nPulp Fiction,PF567\nCrime,Drama\n";
        invalidFormatInput1 = "The Matrix,TM432\nAction,Sci-Fi\nPulp Fiction\nCrime,Drama\n";
        invalidFormatInput2 = "The Matrix,TM432\nAction,Sci-Fi\nPulp Fiction,PF567,1234\nCrime,Drama\n";
        invalidFormatInput3 = "The Matrix,TM432\nPulp Fiction\nCrime,Drama\n";
        duplicateIdInput = "The Matrix,TM432\nAction,Sci-Fi\nPulp Fiction,TM432\nCrime,Drama\n";
    }

    @Test
    void testValidInput() throws Exception {
        MovieParser parser = new MovieParser(validInput);
        Map<String, Movie> movieMap = parser.getMovieMap();

        assertEquals(2, parser.getMovieMap().size());
        Movie matrix = movieMap.get("TM432");
        assertNotNull(matrix);
        assertEquals("The Matrix", matrix.getTitle());
        assertEquals("TM432",matrix.getId());

        Movie pulpFiction = movieMap.get("PF567");
        assertNotNull(pulpFiction);
        assertEquals("Pulp Fiction", pulpFiction.getTitle());
        assertEquals("PF567",pulpFiction.getId());

        // Test genreMovies
        Map<String, Set<String>> genreMovies = parser.getGenreMovies();
        assertEquals(3, genreMovies.size());

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

    @Test
    void testInvalidFormatInput1() {
        assertThrows(InvalidFileFormatException.class, () -> new MovieParser(invalidFormatInput1));
    }
    @Test
    void testInvalidFormatInput2() {
        assertThrows(InvalidFileFormatException.class, () -> new MovieParser(invalidFormatInput2));
    }
    @Test
    void testInvalidFormatInput3() {
        assertThrows(InvalidFileFormatException.class, () -> new MovieParser(invalidFormatInput3));
    }

    @Test
    void testDuplicateIdInput() {
        assertThrows(DuplicateException.class, () -> new MovieParser(duplicateIdInput));
    }
}