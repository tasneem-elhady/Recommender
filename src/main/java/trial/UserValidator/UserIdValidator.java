package trial.UserValidator;


public class UserIdValidator {
    public static boolean isValid(String id) {
        if (id == null || id.length() != 9) {
                return false;
            }
        // Check if ID starts with numbers and optionally ends with one letter
        return id.matches("^[0-9][0-9A-Za-z]{7}[0-9]$") || id.matches("^[0-9][0-9A-Za-z]{6}[0-9][A-Za-z]$");
        }
    
        public static String getErrorMessage(String id) {
            return String.format("User ID {%s} is wrong", id);
        }
    
}
