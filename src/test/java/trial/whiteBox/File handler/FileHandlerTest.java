package trial.FileHandler.test;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import trial.FileHandler.FILEReader;
import trial.FileHandler.FILEWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;


public class FileHandlerTest {
    private static final String TEST_DIR = "test_files";
    private static final String TEST_FILE = TEST_DIR + "/test.txt";
    private static final String TEST_CONTENT = "This is test content\nWith multiple lines\nFor testing purposes";
    private static final String EMPTY_FILE = TEST_DIR + "/empty.txt";
    private static final String NON_EXISTENT_FILE = TEST_DIR + "/nonexistent.txt";

    @BeforeAll
    static void setupTestEnvironment() throws IOException {
        // Create test directory
        File directory = new File(TEST_DIR);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        // Create file with content
        Files.write(Paths.get(TEST_FILE), TEST_CONTENT.getBytes());

        // Create empty file
        Files.write(Paths.get(EMPTY_FILE), "".getBytes());
    }

    @AfterAll
    static void cleanupTestEnvironment() {
        // Clean up files
        new File(TEST_FILE).delete();
        new File(EMPTY_FILE).delete();
        new File(TEST_DIR).delete();
    }

    // =========== FILEReader Tests =============

    /**
     * Statement Coverage Test for FILEReader
     * - Tests successful file reading
     * - Tests handling empty files
     * - Tests handling non-existent files
     */
    @Test
    void testFILEReaderStatementCoverage() {
        // Test Case 1: Reading a file that exists with content
        FILEReader reader = new FILEReader();
        reader.readFile(TEST_FILE);
        assertEquals(TEST_CONTENT, reader.getContent());

        // Test Case 2: Reading an empty file
        reader.readFile(EMPTY_FILE);
        assertEquals("", reader.getContent());

        // Test Case 3: Reading a file that doesn't exist (tests exception handling)
        reader.readFile(NON_EXISTENT_FILE);
        assertEquals("", reader.getContent());

        // Test Case 4: Testing the setContent method
        reader.setContent("Manual content");
        assertEquals("Manual content", reader.getContent());
    }

    /**
     * Branch Coverage Test for FILEReader
     * Tests both branches of the if statement in readFile method
     */
    @Test
    void testFILEReaderBranchCoverage() {
        FILEReader reader = new FILEReader();

        // Branch 1: StringBuilder length > 0 (the trimming branch is executed)
        reader.readFile(TEST_FILE);
        assertEquals(TEST_CONTENT, reader.getContent());

        // Branch 2: StringBuilder length == 0 (the trimming branch is skipped)
        reader.readFile(EMPTY_FILE);
        assertEquals("", reader.getContent());
    }

    /**
     * Condition Coverage Test for FILEReader
     * Tests the conditional expressions in the while loop in readFile method
     */
    @ParameterizedTest
    @ValueSource(strings = {TEST_FILE, EMPTY_FILE, NON_EXISTENT_FILE})
    void testFILEReaderConditionCoverage(String filePath) {
        FILEReader reader = new FILEReader();
        reader.readFile(filePath);

        if (filePath.equals(TEST_FILE)) {
            assertEquals(TEST_CONTENT, reader.getContent());
        } else {
            assertEquals("", reader.getContent());
        }
    }

    /**
     * Path Coverage Test for FILEReader
     * Tests all possible execution paths through the readFile method
     */
    @Test
    void testFILEReaderPathCoverage() {
        FILEReader reader = new FILEReader();

        // Path 1: File exists with content -> read content -> trim StringBuilder -> set content
        reader.readFile(TEST_FILE);
        assertEquals(TEST_CONTENT, reader.getContent());

        // Path 2: File exists but empty -> read content (loop not entered) -> no trim needed -> set content
        reader.readFile(EMPTY_FILE);
        assertEquals("", reader.getContent());

        // Path 3: File doesn't exist -> catch exception -> set content to empty string
        reader.readFile(NON_EXISTENT_FILE);
        assertEquals("", reader.getContent());
    }

    /**
     * Data Flow Testing for FILEReader
     */
    @Test
    void testFILEReaderDataFlow() {
        FILEReader reader = new FILEReader();

        // Test the content variable's data flow: undefined -> defined through readFile
        assertNull(reader.getContent());
        reader.readFile(TEST_FILE);
        assertEquals(TEST_CONTENT, reader.getContent());

        // Test the content variable's data flow: defined -> redefined through setContent
        reader.setContent("New content");
        assertEquals("New content", reader.getContent());

        // Test the content variable's data flow: defined -> redefined through readFile
        reader.readFile(EMPTY_FILE);
        assertEquals("", reader.getContent());
    }

    // =========== FILEWriter Tests =============

    /**
     * Statement Coverage Test for FILEWriter
     * - Tests default constructor
     * - Tests parameterized constructor
     * - Tests writing content to file
     * - Tests get/set methods
     */
    @Test
    void testFILEWriterStatementCoverage() throws IOException {
        // Test Case 1: Using default constructor
        FILEWriter writer1 = new FILEWriter();
        assertEquals("", writer1.getContent());

        // Test Case 2: Using parameterized constructor
        FILEWriter writer2 = new FILEWriter("Content from constructor");
        assertEquals("Content from constructor", writer2.getContent());

        // Test Case 3: Setting content and writing to file
        writer1.setContent("New content to write");
        String outputFile = TEST_DIR + "/output.txt";
        writer1.writeFile(outputFile);

        // Verify file was written correctly
        String fileContent = Files.readString(Path.of(outputFile));
        assertEquals("New content to write", fileContent);

        // Clean up
        new File(outputFile).delete();
    }

    /**
     * Branch Coverage Test for FILEWriter
     */
    @Test
    void testFILEWriterBranchCoverage() {
        FILEWriter writer = new FILEWriter("Test content");

        // Branch 1: Normal operation (no exception)
        String validFile = TEST_DIR + "/valid.txt";
        writer.writeFile(validFile);
        assertTrue(new File(validFile).exists());

        // Branch 2: Exception handling (write to invalid location)
        String invalidFile = "/invalid/location/file.txt";
        writer.writeFile(invalidFile);
        assertFalse(new File(invalidFile).exists());

        // Clean up
        new File(validFile).delete();
    }

    /**
     * Condition Coverage Test for FILEWriter
     */
    @ParameterizedTest
    @ValueSource(strings = {"Simple content", "", "Content\nWith\nMultiple\nLines"})
    void testFILEWriterConditionCoverage(String content) throws IOException {
        FILEWriter writer = new FILEWriter(content);
        String outputFile = TEST_DIR + "/condition_test.txt";
        writer.writeFile(outputFile);

        // Verify file was written correctly
        String fileContent = Files.readString(Path.of(outputFile));
        assertEquals(content, fileContent);

        // Clean up
        new File(outputFile).delete();
    }

    /**
     * Path Coverage Test for FILEWriter
     * Tests all possible execution paths through the writeFile method
     */
    @Test
    void testFILEWriterPathCoverage() throws IOException {
        // Path 1: Write content successfully to a file
        FILEWriter writer = new FILEWriter("Path test content");
        String outputFile = TEST_DIR + "/path_test.txt";
        writer.writeFile(outputFile);

        String fileContent = Files.readString(Path.of(outputFile));
        assertEquals("Path test content", fileContent);

        // Path 2: Attempt to write to an invalid location (exception path)
        String invalidFile = "/invalid/location/file.txt";
        writer.writeFile(invalidFile);
        assertFalse(new File(invalidFile).exists());

        // Clean up
        new File(outputFile).delete();
    }

    /**
     * Data Flow Testing for FILEWriter
     * Focuses on testing the flow of data through the content variable
     */
    @Test
    void testFILEWriterDataFlow() throws IOException {
        // Test content flow: initialization through constructor
        FILEWriter writer = new FILEWriter("Initial content");
        assertEquals("Initial content", writer.getContent());

        // Test content flow: modification through setContent
        writer.setContent("Modified content");
        assertEquals("Modified content", writer.getContent());

        // Test content flow: writing to file
        String outputFile = TEST_DIR + "/dataflow_test.txt";
        writer.writeFile(outputFile);

        String fileContent = Files.readString(Path.of(outputFile));
        assertEquals("Modified content", fileContent);

        // Clean up
        new File(outputFile).delete();
    }
}
