package trial.integration;

import org.junit.jupiter.api.Test;
import trial.Parser.MovieParser;
import trial.Parser.UserParser;
import trial.Exceptions.WrittenError;
import trial.Movie;
import trial.User;

import java.awt.desktop.SystemEventListener;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ParserValidatorIntegrationTest {

    @Test
    void resourceMoviesFile_parseAndValidateAll() throws Exception {
        String moviesRaw = Files.readString(
                Paths.get("src/test/java/trial/integration/movies.txt")
        );

        MovieParser mp = new MovieParser(moviesRaw);
        mp.parseLines();

        Map<String, Movie> movies = mp.getMovieMap();
        assertEquals(11, movies.size(), "Should parse exactly 11 movies");

        assertTrue(movies.containsKey("TM133"));
        assertEquals("The Matrix", movies.get("TM133").getTitle());

        assertTrue(movies.containsKey("SPR234"));
        assertEquals("Saving Private Ryan", movies.get("SPR234").getTitle());
    }

    @Test
    void invalidMovieId_throwsWrittenError() {
        String badRaw =
                "Bad Movie,BM12\n" +
                        "action";

        MovieParser mp = new MovieParser(badRaw);
        WrittenError ex = assertThrows(WrittenError.class, mp::parseLines);
        assertTrue(
                ex.getMessage().contains("ERROR:Movie Id numbers BM12 are wrong"),
                "Expect MovieIdValidator to throw"
        );
    }

    @Test
    void resourceUsersFile_parseAndValidateAll() throws Exception {
        String usersRaw = Files.readString(
                Paths.get("src/test/java/trial/integration/users.txt")
        );

        UserParser up = new UserParser(usersRaw);
        up.parseLines();  // invokes UserNameValidator & UserIdValidator

        Map<String, User> users = up.getUsersMap();
        assertEquals(5, users.size(), "Should parse exactly 5 users");

        // spot-check a couple
        assertTrue(users.containsKey("100000001"));
        assertEquals("John Doe", users.get("100000001").getName());

        assertTrue(users.containsKey("100000005"));
        assertEquals("Charlie Davis", users.get("100000005").getName());
    }

    @Test
    void invalidUserName_throwsWrittenError() {
        String badRaw =
                " Alice,100000006\n" +  // leading space → invalid
                        "MTX123";

        UserParser up = new UserParser(badRaw);
        WrittenError ex = assertThrows(WrittenError.class, up::parseLines);
        System.out.println("Caught message: " + ex.getMessage());
        assertTrue(
                ex.getMessage().contains("ERROR:User Name { Alice} is wrong"),
                "Expect UserNameValidator to throw"
        );
    }
}
