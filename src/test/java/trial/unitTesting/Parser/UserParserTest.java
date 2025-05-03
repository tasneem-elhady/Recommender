package trial.unitTesting.Parser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import trial.Exceptions.DuplicateException;
import trial.Exceptions.InvalidFileFormatException;
import trial.Exceptions.InvalidUserException;
import trial.Exceptions.WrittenError;
import trial.Parser.UserParser;
import trial.User;
import trial.UserValidator.UserValidator;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;

class UserParserTest {

    private static String validInput;
    private static String invalidUserInput;
    private static String invalidFormatInputSingleInfo;
    private static String invalidFormatInputMoreThan2Info;
    private static String invalidFormatInputLessThan2Lines;

    private static String duplicateIdInput;
    private static User Alice;
    private static User Bob;

    @BeforeAll
    static void setUp() {
        validInput = "Alice Johnson,98765432B\nTM432,PF567\nBob Smith,123456789\nPF567,TM432\n";
        invalidUserInput = "Alice Johnson,432B\nTM432,PF567\nBob Smith,123456789\nPF567,TM432\n";
        invalidFormatInputSingleInfo = "Alice Johnson,98765432B\nTM432,PF567\nBob Smith\nPF567,TM432\n";
        invalidFormatInputMoreThan2Info = "Alice Johnson,98765432B\nTM432,PF567\nBob Smith,123456789,1234\nPF567,TM432\n";
        invalidFormatInputLessThan2Lines = "Alice Johnson,98765432B\nTM432,PF567\nPF567,TM432\n";
        duplicateIdInput = "Alice Johnson,98765432B\nTM432,PF567\nAlice Johnson,98765432B\nTM432,PF567\n";
        Alice = new User("Alice Johnson","98765432B",new String[]{"TM432","PF567"});
        Bob = new User("Bob Smith","123456789",new String[]{"PF567","TM432"});
    }
    @Test
    void testInvalidFormatInputSingleInfo() {
        assertThrows(InvalidFileFormatException.class, () -> new UserParser(invalidFormatInputSingleInfo));
    }

    @Test
    void testInvalidFormatInputMoreThan2Info() {
        assertThrows(InvalidFileFormatException.class, () -> new UserParser(invalidFormatInputMoreThan2Info));
    }

    @Test
    void testInvalidFormatInputLessThan2Lines() {
        assertThrows(InvalidFileFormatException.class, () -> new UserParser(invalidFormatInputLessThan2Lines));
    }

//    test parse lines

    @Test
    void testDuplicateIdInput() {
        try (MockedStatic<UserValidator> mockedValidator = org.mockito.Mockito.mockStatic(UserValidator.class)) {


            mockedValidator.when(() -> UserValidator.validate(
                            eq(new String[]{"Alice Johnson", "98765432B"}),
                            eq(new String[]{"TM432", "PF567"})))
                    .thenReturn(Alice);
            mockedValidator.when(() -> UserValidator.validate(
                            eq(new String[]{"Alice Johnson", "98765432B"}),
                            eq(new String[]{"TM432", "PF567"})))
                    .thenReturn(Alice);

            assertThrows(DuplicateException.class, () -> {
                UserParser parser = new UserParser(duplicateIdInput);
                parser.parseLines();
            });
        }
    }

    @Test
    void testInvalidUserInput() {
        try (MockedStatic<UserValidator> mockedValidator = org.mockito.Mockito.mockStatic(UserValidator.class)) {


            mockedValidator.when(() -> UserValidator.validate(
                            eq(new String[]{"Alice Johnson", "432B"}),
                            eq(new String[]{"TM432", "PF567"})))
                    .thenThrow(InvalidUserException.class);

            assertThrows(WrittenError.class, () -> {
                UserParser parser = new UserParser(invalidUserInput);
                parser.parseLines();
            });
        }
    }

    @Test
    void testValidInput() throws Exception {
        try (MockedStatic<UserValidator> mockedValidator = org.mockito.Mockito.mockStatic(UserValidator.class)) {

            mockedValidator.when(() -> UserValidator.validate(
                            eq(new String[]{"Alice Johnson", "98765432B"}),
                            eq(new String[]{"TM432", "PF567"})))
                    .thenReturn(Alice);

            mockedValidator.when(() -> UserValidator.validate(
                            eq(new String[]{"Bob Smith", "123456789"}),
                            eq(new String[]{"PF567", "TM432"})))
                    .thenReturn(Bob);

            UserParser parser = new UserParser(validInput);
            parser.parseLines();

            // Test usersMap
            Map<String, User> usersMap = parser.getUsersMap();
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
    }

}