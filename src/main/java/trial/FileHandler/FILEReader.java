package trial.FileHandler;
import java.io.BufferedReader;
import java.io.IOException;
public class FILEReader {
    private String content; // String field to store the read content

    // Method to read file content into the string field
    public void readFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            // Remove the last newline character if the file isn't empty
            if (stringBuilder.length() > 0) {
                stringBuilder.setLength(stringBuilder.length() - 1);
            }
            this.content = stringBuilder.toString();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            this.content = ""; // Set empty string if error occurs
        }
    }

    // Getter for the content
    public String getContent() {
        return content;
    }

    // Setter for the content (optional)
    public void setContent(String content) {
        this.content = content;
    }
}
/* Example usage of FileReader
FileReader fileReader = new FileReader();
fileReader.readFile("input.txt");
System.out.println("File content: " + fileReader.getContent());*/