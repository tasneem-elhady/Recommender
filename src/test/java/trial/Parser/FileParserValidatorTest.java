package trial.Parser;

import org.junit.jupiter.api.Test;
import trial.Exceptions.InvalidFileFormatException;

import static org.junit.jupiter.api.Assertions.*;

class FileParserValidatorTest {
    String[] validLines = {
            "The Shawshank Redemption,TSR728" ,
            "Drama,Crime" ,

            "Inception,I395" ,
            "Action,Sci-Fi,Thriller" ,

            "The Dark Knight,TDK187" ,
            "Action,Crime,Drama" ,

            "Jurassic Park,JP564" ,
            "Adventure,Sci-Fi,Action"

    };

    @Test
    void testValidFileStructure() {
        String input = "The Shawshank Redemption,TSR728\n" +
                        "Drama,Crime\n" +

                        "Inception,I395\n" +
                        "Action,Sci-Fi,Thriller\n" +

                        "The Dark Knight,TDK187\n" +
                        "Action,Crime,Drama\n" +

                        "Jurassic Park,JP564\n" +
                        "Adventure,Sci-Fi,Action";
        assertDoesNotThrow(() ->{
                    String[] lines = FileParserValidator.validate(input);
                    assertArrayEquals(validLines,lines);
                });

    }
    @Test
    void testEmptyLines() {
        String input = "The Shawshank Redemption,TSR728\n" +
                "Drama,Crime\n" +

                "Inception,I395\n\n" +
                "Action,Sci-Fi,Thriller\n" +

                "The Dark Knight,TDK187\n" +
                "Action,Crime,Drama\n" +

                "Jurassic Park,JP564\n" +
                "Adventure,Sci-Fi,Action";
        assertDoesNotThrow(() ->{
            String[] lines = FileParserValidator.validate(input);
            assertArrayEquals(validLines,lines);
        });    }

    @Test
    void testEmptyFileThrowsException() {
        String input = "";
        Exception exception = assertThrows(InvalidFileFormatException.class, () ->
                FileParserValidator.validate(input)
        );
        assertEquals("File is empty or missing content.", exception.getMessage());
    }

    @Test
    void testOddNumberOfLinesThrowsExceptionLastLine() {
        String input = "The Shawshank Redemption,TSR728\n" +
                        "Drama,Crime\n" +

                        "Inception,I395\n" +
                        "Action,Sci-Fi,Thriller\n" +

                        "The Dark Knight,TDK187\n" +
                        "Action,Crime,Drama\n" +

                        "Jurassic Park,JP564\n"; // Missing genres
        Exception exception = assertThrows(InvalidFileFormatException.class, () ->
                FileParserValidator.validate(input)
        );
        assertEquals("Invalid file structure: Each movie or user must have exactly two lines.", exception.getMessage());
    }

    @Test
    void testOddNumberOfLinesThrowsExceptionFirstLine() {
        String input = "The Shawshank Redemption,TSR728\n" +
                "Drama,Crime\n" +

                "Inception,I395\n" +
                "Action,Sci-Fi,Thriller\n" +

                "Action,Crime,Drama\n" ; // Missing identification
        Exception exception = assertThrows(InvalidFileFormatException.class, () ->
                FileParserValidator.validate(input)
        );
        assertEquals("Invalid file structure: Each movie or user must have exactly two lines.", exception.getMessage());
    }

    @Test
    void testIdentificationLineThrowsException1() {
        String input ="The Shawshank Redemption,TSR728\n" +
                        "Drama,Crime\n" +

                        "Inception,I395\n" +
                        "Action,Sci-Fi,Thriller\n" +

                        "The Dark Knight,TDK187,1234\n" +
                        "Action,Crime,Drama\n" +

                        "Jurassic Park,JP564\n" +
                        "Adventure,Sci-Fi,Action"; // Missing comma
        Exception exception = assertThrows(InvalidFileFormatException.class, () ->
                FileParserValidator.validate(input)
        );
        assertEquals("Invalid file structure: Expected 2 comma-separated values.", exception.getMessage());
    }
    @Test
    void testIdentificationLineThrowsException2() {
        String input ="The Shawshank Redemption,TSR728\n" +
                "Drama,Crime\n" +

                "Inception,I395\n" +
                "Action,Sci-Fi,Thriller\n" +

                "The Dark Knight\n" +
                "Action,Crime,Drama\n" +

                "Jurassic Park,JP564\n" +
                "Adventure,Sci-Fi,Action"; // Missing comma
        Exception exception = assertThrows(InvalidFileFormatException.class, () ->
                FileParserValidator.validate(input)
        );
        assertEquals("Invalid file structure: Expected 2 comma-separated values.", exception.getMessage());
    }
}