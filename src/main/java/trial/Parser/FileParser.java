package trial.Parser;

import trial.Exceptions.DuplicateException;
import trial.Exceptions.InvalidFileFormatException;
import trial.Exceptions.WrittenError;

/**
 * serves as an abstract base class for parsing input data.
 * It validates the input and provides a structure for parsing lines.
 */
public abstract class FileParser {
    // Array to store lines of input after validation
    private final String[] lines;

    /**
     * Constructs a FileParser and validates the input data structure.
     *
     * @param input the input string to be parsed
     * @throws InvalidFileFormatException if the input format is invalid
     */
    FileParser(String input) throws InvalidFileFormatException {
        lines = FileParserValidator.validate(input);
    }

    /**
     * Returns the validated lines of input.
     *
     * @return an array of strings representing the lines of input
     */
    public String[] getLines() {
        return lines;
    }

    /**
     * Abstract method to parse lines, to be implemented by subclasses.
     *
     * @throws DuplicateException if a duplicate entry is found
     * @throws WrittenError if there is an error during parsing
     */
    protected abstract void parseLines() throws DuplicateException, WrittenError;
}