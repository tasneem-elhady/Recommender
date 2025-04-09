package trial.UserValidator;

public class UserNameValidator {
    public static boolean isValid(String name) {
        if (name == null || name.isEmpty() || name.startsWith(" ")) {
            return false;
        }
        // Check if name contains only alphabetic characters and spaces
        return name.matches("^[a-zA-Z][a-zA-Z ]*$");
    }

    public static String getErrorMessage(String name) {
        return String.format("User Name {%s} is wrong", name);
    }
}
