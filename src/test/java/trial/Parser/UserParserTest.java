package trial.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trial.Exceptions.DuplicateException;
import trial.Exceptions.InvalidFileFormatException;
import trial.Parser.UserParser;
import trial.User;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class UserParserTest {

    private static String validInput;
    private static String invalidFormatInput1;
    private static String invalidFormatInput2;
    private static String invalidFormatInput3;

    private static String duplicateIdInput;

    @BeforeAll
    static void setUp() {
        validInput = "Alice Johnson,98765432B\nTM432,PF567\nBob Smith,123456789\nPF567,TM432\n";
        invalidFormatInput1 = "Alice Johnson,98765432B\nTM432,PF567\nBob Smith\nPF567,TM432\n";
        invalidFormatInput2 = "Alice Johnson,98765432B\nTM432,PF567\nBob Smith,123456789,1234\nPF567,TM432\n";
        invalidFormatInput3 = "Alice Johnson,98765432B\nTM432,PF567\nPF567,TM432\n";
        duplicateIdInput = "Alice Johnson,98765432B\nTM432,PF567\nAlice Johnson,98765432B\nPF567,TM432\n";
    }

    @Test
    void testValidInput() throws Exception {
        UserParser userParser = new UserParser(validInput);

        // Test usersMap
        Map<String, User> usersMap = userParser.getUsersMap();
        assertEquals(2, usersMap.size());

        User alice = usersMap.get("98765432B");
        assertNotNull(alice);
        assertEquals("Alice Johnson", alice.getName());
        assertArrayEquals(new String[]{"TM432", "PF567"}, alice.getLikedMovies());

        User bob = usersMap.get("123456789");
        assertNotNull(bob);
        assertEquals("Bob Smith", bob.getName());
        assertArrayEquals(new String[]{"PF567", "TM432"}, bob.getLikedMovies());
    }

    @Test
    void testInvalidFormatInput1() {
        assertThrows(InvalidFileFormatException.class, () -> new UserParser(invalidFormatInput1));
    }
    @Test
    void testInvalidFormatInput2() {
        assertThrows(InvalidFileFormatException.class, () -> new UserParser(invalidFormatInput2));
    }
    @Test
    void testInvalidFormatInput3() {
        assertThrows(InvalidFileFormatException.class, () -> new UserParser(invalidFormatInput3));
    }

    @Test
    void testDuplicateIdInput() {
        assertThrows(DuplicateException.class, () -> new UserParser(duplicateIdInput));
    }
}