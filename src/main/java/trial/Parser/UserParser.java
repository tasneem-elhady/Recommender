package trial.Parser;

import trial.Exceptions.DuplicateException;
import trial.Exceptions.InvalidFileFormatException;
import trial.Exceptions.InvalidUserException;
import trial.Exceptions.WrittenError;
import trial.User;
import trial.UserValidator.UserValidator;

import java.util.HashMap;
import java.util.Map;

/**
 * responsible for parsing user data from a given input string.
 * It validates the input format, checks for duplicate user IDs, and stores users in a map.
 */
public class UserParser extends FileParser {

    public Map<String, User> usersMap = new HashMap<>();

    /**
     * Constructs a UserParser and parses the input data.
     *
     * @param input the input string containing user data
     * @throws InvalidFileFormatException if the input format is incorrect
     * @throws WrittenError if there is an error during user validation
     * @throws DuplicateException if a duplicate user ID is found
     */
    public UserParser(String input) throws InvalidFileFormatException{
        super(input);
    }

    /**
     * Parses the input string to extract user information and liked movies.
     *
     * @throws DuplicateException if a duplicate user ID is found
     * @throws WrittenError if there is an error during user validation
     */
    @Override
    public void parseLines() throws DuplicateException, WrittenError {
        String[] lines = getLines();

        // Iterate over the lines, processing each user and their liked movies
        for (int i = 0; i < lines.length; i += 2) {
            String[] userInfo = lines[i].split(",");
            String[] likedMovies = lines[i + 1].split(",");

            // Validate and create a User object
            User user = validateAndCreateUser(userInfo, likedMovies);

            // Check for duplicate user IDs
            if (usersMap.containsKey(user.getId())) {
                throw new DuplicateException("Duplicate user id found: " + user.getId());
            }

            // Add the user to the map
            usersMap.put(user.getId(), user);
        }
    }

    /**
     * Validates user information and creates a User object.
     *
     * @param userInfo an array containing username and ID
     * @param likedMovies an array of movie IDs liked by the user
     * @return a validated User object
     * @throws WrittenError if there is an error during validation
     */
    private User validateAndCreateUser(String[] userInfo, String[] likedMovies) throws WrittenError {
        try {
            return UserValidator.validate(userInfo, likedMovies);
        } catch (InvalidUserException e) {
            throw new WrittenError("ERROR:" + e.getMessage());
        }
    }

    /**
     * Returns the map of users with their IDs as keys.
     *
     * @return a map of user IDs to User objects
     */
    public Map<String, User> getUsersMap() {
        return usersMap;
    }
}