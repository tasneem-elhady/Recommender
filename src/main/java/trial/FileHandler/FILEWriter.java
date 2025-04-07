package trial.FileHandler;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class FILEWriter {
    private String content; // String field containing the content to write

    // Constructor
    public FILEWriter(String content) {
        this.content = content;
    }

    // Default constructor
    public FILEWriter() {
        this.content = "";
    }

    // Method to write the content to a file
    public void writeFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter(filePath))) {
            writer.write(this.content);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Getter for the content
    public String getContent() {
        return content;
    }

    // Setter for the content
    public void setContent(String content) {
        this.content = content;
    }
}
/*Example usage of FileWriter
FileWriter fileWriter = new FileWriter();
fileWriter.setContent("This is the content to be written to the file.");
fileWriter.writeFile("output.txt");*/