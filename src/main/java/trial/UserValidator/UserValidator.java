package trial.UserValidator;

import trial.Exceptions.InvalidUserException;
import trial.User;

public class UserValidator {
    
    public static User isValidUser(String[] userInfo, String[] likedMovies) throws InvalidUserException {
        // Check if array is null or doesn't have exactly 2 elements
        if (userInfo == null || userInfo.length != 2) {
            throw new InvalidUserException("Invalid input: Must provide exactly 2 elements (name and ID)");
        }

        String name = userInfo[0];
        String id = userInfo[1];

        // Check for null elements
        if (name == null || id == null) {
            throw new InvalidUserException("Name and ID cannot be null");
        }

        if (!isValidUserFields(name, id)) {
            throw new InvalidUserException(buildErrorMessage(name, id));
        }

        // If valid, create and return new User
        User user = new User();
        user.name = name;
        user.id = id;
        user.likedMovies = likedMovies;
        return user;
    }

    public static boolean isValidUserFields(String name, String id) {
        return UserNameValidator.isValid(name) && UserIdValidator.isValid(id);
    }

    private static String buildErrorMessage(String name, String id) {
        StringBuilder errorMessage = new StringBuilder();

        if (!UserNameValidator.isValid(name)) {
            errorMessage.append(UserNameValidator.getErrorMessage(name)).append("\n");
        }

        if (!UserIdValidator.isValid(id)) {
            errorMessage.append(UserIdValidator.getErrorMessage(id)).append("\n");
        }

        return errorMessage.toString().trim();
    }
}