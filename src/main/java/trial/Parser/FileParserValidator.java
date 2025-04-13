package trial.Parser;

import trial.Exceptions.InvalidFileFormatException;

import java.util.ArrayList;

/**
 * The FileParserValidator class provides methods to validate the structure of input data.
 * It ensures that the input is not empty and follows the expected format.
 */
public class FileParserValidator {

    /**
     * Validates the input string for correct format and structure.
     *
     * @param input the input string to be validated
     * @return an array of valid lines from the input
     * @throws InvalidFileFormatException if the input is empty or has an invalid structure
     */
    public static String[] validate(String input) throws InvalidFileFormatException {
        // Check if the input is empty
        if (input.isEmpty()) {
            throw new InvalidFileFormatException("File is empty or missing content.");
        }

        // Split the input into lines
        String[] lines = input.split("\n");
        ArrayList<String> validLines = new ArrayList<>();

        // Filter out empty lines and add valid lines to the list
        for (String line : lines) {
            if (line.isEmpty()) {
                continue;
            }
            validLines.add(line);
        }

        // Check if the number of valid lines is even
        if (validLines.size() % 2 != 0) {
            throw new InvalidFileFormatException("Invalid file structure: Each movie or user must have exactly two lines.");
        }

        // Validate the structure of each pair of lines
        for (int i = 0; i < validLines.size(); i += 2) {
            String firstLine = validLines.get(i);
            if (firstLine.split(",").length > 2 ||
                    (firstLine.split(",").length == 2 && firstLine.endsWith(",")) ||
                    firstLine.split(",").length == 1 && !firstLine.endsWith(",")) {
                throw new InvalidFileFormatException("Invalid file structure: Expected 2 comma-separated values.");
            }
        }

        // Return the valid lines as an array
        return validLines.toArray(new String[0]);
    }
}