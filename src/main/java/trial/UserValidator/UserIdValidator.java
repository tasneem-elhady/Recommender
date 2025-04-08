package trial.UserValidator;


public class UserIdValidator {
    public static boolean isValid(String id) {
        if (id == null || id.length() != 9) {
                return false;
            }
        // Check if ID starts with numbers and optionally ends with one letter
        return id.matches("^[0-9]{8}[a-zA-Z]$") || id.matches("^[0-9]{9}$");
        }
    
        public static String getErrorMessage() {
            return "User ID must be exactly 9 characters long, start with numbers, and may end with one alphabetic character.";
        }
    
}
