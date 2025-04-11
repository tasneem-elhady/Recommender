package trial.FileHandler;
import java.io.BufferedReader;
import java.io.IOException;
public class FILEReader {
    private String content; 
    public void readFile(String filePath) {
        StringBuilder stringBuilder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
          
            if (stringBuilder.length() > 0) {
                stringBuilder.setLength(stringBuilder.length() - 1);
            }
            this.content = stringBuilder.toString();
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
            this.content = ""; 
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
