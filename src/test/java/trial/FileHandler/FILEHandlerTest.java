package trial.FileHandler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class FileHandlerTest {

    private FILEReader reader;
    private FILEWriter writer;

    @TempDir
    Path tempDir;

    private File testFile;
    private final String TEST_CONTENT = "Line 1\nLine 2\nLine 3";

    @BeforeEach
    public void setup() throws IOException {
        reader = new FILEReader();
        writer = new FILEWriter();

        testFile = new File(tempDir.toFile(), "testFile.txt");
        Files.write(testFile.toPath(), TEST_CONTENT.getBytes());
    }

    @AfterEach
    public void cleanup() {

    }

    @Test
    public void testFileReaderReadValidFile() {
        reader.readFile(testFile.getAbsolutePath());
        assertEquals(TEST_CONTENT, reader.getContent(), "Content should match what was written to file");
    }

    @Test
    public void testFileReaderNonExistentFile() {
        reader.readFile(tempDir + "/nonexistent.txt");
        assertEquals("", reader.getContent(), "Content should be empty string when file doesn't exist");
    }

    @Test
    public void testFileReaderEmptyFile() throws IOException {
        File emptyFile = new File(tempDir.toFile(), "empty.txt");
        Files.write(emptyFile.toPath(), "".getBytes());

        reader.readFile(emptyFile.getAbsolutePath());
        assertEquals("", reader.getContent(), "Content should be empty string when file is empty");
    }

    @Test
    public void testFileWriterWriteToFile() throws IOException {
        String content = "Test content for writing";
        writer.setContent(content);

        File outputFile = new File(tempDir.toFile(), "output.txt");
        writer.writeFile(outputFile.getAbsolutePath());

        String fileContent = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals(content, fileContent, "Written content should match what was set");
    }

    @Test
    public void testFileWriterWithConstructor() throws IOException {
        String content = "Content from constructor";
        FILEWriter writerWithContent = new FILEWriter(content);

        File outputFile = new File(tempDir.toFile(), "output2.txt");
        writerWithContent.writeFile(outputFile.getAbsolutePath());

        String fileContent = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals(content, fileContent, "Written content should match what was passed to constructor");
    }

    @Test
    public void testFileWriterWriteEmptyContent() throws IOException {
        writer.setContent("");

        File outputFile = new File(tempDir.toFile(), "empty_output.txt");
        writer.writeFile(outputFile.getAbsolutePath());

        String fileContent = new String(Files.readAllBytes(outputFile.toPath()));
        assertEquals("", fileContent, "File should be empty when empty content is written");
    }

    @Test
    public void testFileReaderWriterIntegration() throws IOException {
        String content = "Integration test content\nMultiple lines\nMore content";
        writer.setContent(content);

        File outputFile = new File(tempDir.toFile(), "integration.txt");
        writer.writeFile(outputFile.getAbsolutePath());

        reader.readFile(outputFile.getAbsolutePath());

        assertEquals(content, reader.getContent(), "Content read should match what was written");
    }
}
