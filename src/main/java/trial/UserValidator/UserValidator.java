package trial.UserValidator;

import trial.User;

public class UserValidator {
    
    public static boolean isValidUser(User user) {
        if (user == null) {
            return false;
        }
        return isValidUserFields(user.getName(), user.getId());
    }

    public static boolean isValidUserFields(String name, String id) {
        return UserNameValidator.isValid(name) && UserIdValidator.isValid(id);
    }

    // Private helper method to build error message
    private static String buildErrorMessage(User user) {
        StringBuilder errorMessage = new StringBuilder();

        if (!UserNameValidator.isValid(user.getName())) {
            errorMessage.append(UserNameValidator.getErrorMessage()).append("\n");
        }

        if (!UserIdValidator.isValid(user.getId())) {
            errorMessage.append(UserIdValidator.getErrorMessage()).append("\n");
        }

        return errorMessage.toString().trim();
    }

    public static String getErrorMessage(User user) {
        if (user == null) {
            return "User object cannot be null";
        }

        String errors = buildErrorMessage(user);
        return errors.isEmpty() ? "Valid user" : errors;
    }

    public static void validateUser(User user) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("User object cannot be null");
        }

        String errors = buildErrorMessage(user);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(errors);
        }
    }
}