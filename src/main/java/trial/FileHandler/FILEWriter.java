package trial.FileHandler;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
public class FILEWriter {
    private String content;
    

    public FILEWriter(String content) {
        this.content = content;
    }

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
