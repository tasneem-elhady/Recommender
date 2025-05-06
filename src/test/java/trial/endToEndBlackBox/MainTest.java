package trial.endToEndBlackBox;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trial.Exceptions.DuplicateException;
import trial.Exceptions.InvalidFileFormatException;
import trial.Exceptions.WrittenError;
import trial.Main;
import trial.Parser.FileParserValidator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    private static final String MOVIES_PATH = "src/main/java/trial/movies.txt";
    private static final String USERS_PATH = "src/main/java/trial/users.txt";
    private static final String OUTPUT_PATH = "src/main/java/trial/recommendations.txt";

    @BeforeEach
    public void setUp() throws IOException {
        // Reset files before each test
        Files.write(Paths.get(MOVIES_PATH), new byte[0]); // Clear content
        Files.write(Paths.get(USERS_PATH), new byte[0]);
        Files.write(Paths.get(OUTPUT_PATH), new byte[0]);
    }
    @Test
    public void testValidInputProducesCorrectRecommendationOutput() throws IOException {
        // Valid input files
        Files.write(Paths.get(MOVIES_PATH), """
                The Matrix,TM123
                Sci-Fi,Action
                The Godfather,TG456
                Crime,Drama
                Finding Nemo,FN789
                Animation,Family
                Pulp Fiction,PF124
                Thriller,Drama
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                John Doe,12345678A
                TM123,FN789
                Jane Smith,987654321
                TG456
                """.getBytes());

        Main.main(null);

        String output = readFile(OUTPUT_PATH);
        copyRecommendationToDump(Thread.currentThread().getStackTrace()[1].getMethodName(), output);
        assertEquals("Jane Smith,987654321\n" +
                "Pulp Fiction\n" +
                "John Doe,12345678A\n",output);

    }

    @Test
    public void testInvalidMovieTitleProducesError() throws IOException {
        Files.write(Paths.get(MOVIES_PATH), """
                the matrix,TM123
                Sci-Fi,Action
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                John Doe,12345678A
                TM123
                """.getBytes());

        Main.main(null);

        String output = readFile(OUTPUT_PATH);
        copyRecommendationToDump(Thread.currentThread().getStackTrace()[1].getMethodName(), output);
        assertEquals("ERROR:Movie Title the matrix is wrong",output);    }

    @Test
    public void testInvalidMovieIdLetters() throws IOException {
        Files.write(Paths.get(MOVIES_PATH), """
                The Matrix,T123
                Sci-Fi
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                John Doe,12345678A
                T123
                """.getBytes());

        Main.main(null);

        String output = readFile(OUTPUT_PATH);
        copyRecommendationToDump(Thread.currentThread().getStackTrace()[1].getMethodName(), output);
        assertEquals("ERROR:Movie Id letters T123 are wrong",output);
}

    @Test
    public void testInvalidMovieIdDuplicateNumbers() throws IOException {
        Files.write(Paths.get(MOVIES_PATH), """
                The Matrix,TM111
                Action
                The Godfather,TG111
                Crime,Drama
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                John Doe,12345678A
                TM111
                """.getBytes());

        Main.main(null);

        String output = readFile(OUTPUT_PATH);
        copyRecommendationToDump(Thread.currentThread().getStackTrace()[1].getMethodName(), output);
        assertEquals("ERROR: Movie Id numbers TG111 aren’t unique",output);    }

    @Test
    public void testInvalidUserName() throws IOException {
        Files.write(Paths.get(MOVIES_PATH), """
                The Matrix,TM123
                Action
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                 John Doe,12345678A
                TM123
                """.getBytes());

        Main.main(null);

        String output = readFile(OUTPUT_PATH);
        copyRecommendationToDump(Thread.currentThread().getStackTrace()[1].getMethodName(), output);
        assertEquals("ERROR:User Name { John Doe} is wrong",output);    }

    @Test
    public void testInvalidUserIdFormat() throws IOException {
        Files.write(Paths.get(MOVIES_PATH), """
                The Matrix,TM123
                Action
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                John Doe,ABC123456
                TM123
                """.getBytes());

        Main.main(null);

        String output = readFile(OUTPUT_PATH);
        copyRecommendationToDump(Thread.currentThread().getStackTrace()[1].getMethodName(), output);
        assertEquals("ERROR:User ID {ABC123456} is wrong",output);
    }
    @Test
    public void testInvalidUserIdNotUnique() throws IOException {
        Files.write(Paths.get(MOVIES_PATH), """
                The Matrix,TM123
                Action
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                John Doe,1BC123456
                TM123
                jack sparrow,1BC123456
                TM123
                """.getBytes());

        Exception exception = assertThrows(RuntimeException.class, () ->
                Main.main(null)
        );
        assertEquals("Duplicate user id found: 1BC123456", exception.getMessage());
    }
    @Test
    public void testInvalidMovieMissingInfo() throws IOException {
        Files.write(Paths.get(MOVIES_PATH), """
                The Matrix,
                Action
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                John Doe,1BC123456
                TM123
                """.getBytes());
        Main.main(null);
        String output = readFile(OUTPUT_PATH);
        copyRecommendationToDump(Thread.currentThread().getStackTrace()[1].getMethodName(), output);
        assertEquals("ERROR:Missing parameters from movie name and ID.",output);
    }
    @Test
    public void testInvalidUserMissingInfo() throws IOException {
        Files.write(Paths.get(MOVIES_PATH), """
                The Matrix,TM123
                Action
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                John Doe,
                TM123
                jack sparrow,1BC123456
                TM123
                """.getBytes());

        Main.main(null);
        String output = readFile(OUTPUT_PATH);
        copyRecommendationToDump(Thread.currentThread().getStackTrace()[1].getMethodName(), output);
        assertEquals("ERROR:Invalid input: Must provide exactly 2 elements (name and ID)",output);
    }
    @Test
    public void testInvalidUserFileStructure() throws IOException {
        Files.write(Paths.get(MOVIES_PATH), """
                The Matrix,TM123
                Action
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                John Doe
                TM123
                jack sparrow,1BC123456
                TM123
                """.getBytes());

        Exception exception = assertThrows(RuntimeException.class, () ->
                Main.main(null)
        );
        assertEquals("Invalid file structure: Expected 2 comma-separated values.", exception.getMessage());
    }
    @Test
    public void testInvalidMovieFileStructure() throws IOException {
        Files.write(Paths.get(MOVIES_PATH), """
                The Matrix
                Action
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                John Doe
                TM123
                """.getBytes());

        Exception exception = assertThrows(RuntimeException.class, () ->
                Main.main(null)
        );
        assertEquals("Invalid file structure: Expected 2 comma-separated values.", exception.getMessage());
    }
    @Test
    public void testMoreThanOneError() throws IOException {
        Files.write(Paths.get(MOVIES_PATH), """
                The Matrix
                Action
                """.getBytes());

        Files.write(Paths.get(USERS_PATH), """
                John Doe,1BC123456
                TM123
                jack sparrow,1BC123456
                TM123
                """.getBytes());

        Exception exception = assertThrows(RuntimeException.class, () ->
                Main.main(null)
        );
        assertEquals("Invalid file structure: Expected 2 comma-separated values.", exception.getMessage());
    }



    private String readFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
    private static void copyRecommendationToDump(String testName, String output) {
        Path dumpDir = Paths.get("src/test/resources/dump");

        try {
            // Ensure the dump directory exists
            if (!Files.exists(dumpDir)) {
                Files.createDirectories(dumpDir);
            }

            // Construct the destination file path with the test name appended
            Path destFile = dumpDir.resolve("recommendations_" + testName + ".txt");

            // Write the output to the file
            Files.write(destFile, output.getBytes());

            System.out.println("✅ Dumped output to: " + destFile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to dump recommendations.txt for test: " + testName, e);
        }
    }
}