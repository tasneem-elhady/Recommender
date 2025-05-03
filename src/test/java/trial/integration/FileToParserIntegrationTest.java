package trial.integration;

import org.junit.jupiter.api.Test;
import trial.FileHandler.FILEReader;
import trial.Parser.MovieParser;
import trial.Parser.UserParser;

import static org.junit.jupiter.api.Assertions.*;

class FileToParserIntegrationTest {

    @Test
    void resourceMoviesFile_readsAndSplitsLines() throws Exception {
        String moviesPath = "src/test/java/trial/integration/movies.txt";

        FILEReader reader = new FILEReader();
        reader.readFile(moviesPath);
        String raw = reader.getContent();

        MovieParser mp = new MovieParser(raw);
        String[] lines = mp.getLines();

        assertEquals(22, lines.length);
        assertEquals("The Matrix,TM133",             lines[0]);
        assertEquals("action,sci-fi",                 lines[1]);
        assertEquals("Saving Private Ryan,SPR234",    lines[20]);
        assertEquals("action,drama",                  lines[21]);
    }

    @Test
    void resourceUsersFile_readsAndSplitsLines() throws Exception {
        String usersPath = "src/test/java/trial/integration/users.txt";

        FILEReader reader = new FILEReader();
        reader.readFile(usersPath);
        String raw = reader.getContent();

        UserParser up = new UserParser(raw);
        String[] lines = up.getLines();

        assertEquals(10, lines.length);
        assertEquals("John Doe,100000001",       lines[0]);
        assertEquals("TM133,I456,TSR789",     lines[1]);
        assertEquals("Charlie Davis,100000005",  lines[8]);
        assertEquals("TSOTL163,THG789",            lines[9]);
    }
}
