package trial.unitTesting.UserValidator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import trial.Exceptions.InvalidUserException;
import trial.User;
import trial.UserValidator.UserValidator;

import static org.junit.jupiter.api.Assertions.*;

class UserValidatorTest {

        @Test
        @DisplayName("Test Case 1: Simple valid username + userId (all digits)")
        void validCase1() {
            String[] userInfo = { "John", "123456789" };
            String[] likedMovies = { "Matrix", "Inception" };
            assertDoesNotThrow(() -> {
                User result = UserValidator.validate(userInfo, likedMovies);
                assertNotNull(result);
                assertEquals("John", result.getName());
                assertEquals("123456789", result.getId());
            });
        }

        @Test
        @DisplayName("Test Case 2: Username with space + userId (digits, ends with letter)")
        void validCase2() {
            String[] userInfo = { "Mary Jane", "98765432A" };
            String[] likedMovies = {};
            assertDoesNotThrow(() -> {
                User result = UserValidator.validate(userInfo, likedMovies);
                assertNotNull(result);
                assertEquals("Mary Jane", result.getName());
                assertEquals("98765432A", result.getId());
            });
        }

        @Test
        @DisplayName("Test Case 3: Username multiple spaces + userId (digits)")
        void validCase3() {
            String[] userInfo = { "Michael Douglas Junior", "000000001" };
            String[] likedMovies = { "Interstellar" };
            assertDoesNotThrow(() -> {
                User result = UserValidator.validate(userInfo, likedMovies);
                assertNotNull(result);
                assertEquals("Michael Douglas Junior", result.getName());
                assertEquals("000000001", result.getId());
            });
        }

        @Test
        @DisplayName("Test Case 11: Username, userId that ends with single letter")
        void validCase11() {
            String[] userInfo = { "Diana Prince", "12345678Z" };
            String[] likedMovies = { "Wonder Woman" };
            assertDoesNotThrow(() -> {
                User result = UserValidator.validate(userInfo, likedMovies);
                assertNotNull(result);
                assertEquals("Diana Prince", result.getName());
                assertEquals("12345678Z", result.getId());
            });
        }

        @Test
        @DisplayName("Test Case 12: Username, userId with a letter in the middle and ends with digit")
        void validCase12() {
            String[] userInfo = { "Bob Marley", "1234X6789" };
            String[] likedMovies = { "No Woman No Cry" };
            assertDoesNotThrow(() -> {
                User result = UserValidator.validate(userInfo, likedMovies);
                assertNotNull(result);
                assertEquals("Bob Marley", result.getName());
                assertEquals("1234X6789", result.getId());
            });
        }


        @Test
        @DisplayName("Test Case 4: userName starts with space")
        void invalidCase4() {
            String[] userInfo = { "  Alex", "111111111" };
            String[] likedMovies = { "Batman" };
            assertThrows(InvalidUserException.class, () -> {
                UserValidator.validate(userInfo, likedMovies);
            }, "Expected to throw InvalidUserException due to userName starting with space");
        }

        @Test
        @DisplayName("Test Case 5: userName contains digit")
        void invalidCase5() {
            String[] userInfo = { "Dave2", "666666666" };
            String[] likedMovies = { "Gladiator" };
            assertThrows(InvalidUserException.class, () -> {
                UserValidator.validate(userInfo, likedMovies);
            }, "Expected to throw InvalidUserException due to digit in userName");
        }

        @Test
        @DisplayName("Test Case 6: userName contains special character")
        void invalidCase6() {
            String[] userInfo = { "Sam-Patrick", "333333333" };
            String[] likedMovies = { "Tenet" };
            assertThrows(InvalidUserException.class, () -> {
                UserValidator.validate(userInfo, likedMovies);
            }, "Expected to throw InvalidUserException due to special character in userName");
        }

        @Test
        @DisplayName("Test Case 7: userId too short (8 chars)")
        void invalidCase7() {
            String[] userInfo = { "Bruce Wayne", "12345678" };
            String[] likedMovies = { "The Dark Knight" };
            assertThrows(InvalidUserException.class, () -> {
                UserValidator.validate(userInfo, likedMovies);
            }, "Expected to throw InvalidUserException due to userId being only 8 characters long");
        }

        @Test
        @DisplayName("Test Case 8: userId too long (10 chars)")
        void invalidCase8() {
            String[] userInfo = { "Clark Kent", "1234567890" };
            String[] likedMovies = { "Man of Steel" };
            assertThrows(InvalidUserException.class, () -> {
                UserValidator.validate(userInfo, likedMovies);
            }, "Expected to throw InvalidUserException due to userId being 10 characters long");
        }

        @Test
        @DisplayName("Test Case 9: userId does not start with digit")
        void invalidCase9() {
            String[] userInfo = { "Peter Parker", "A23456789" };
            String[] likedMovies = { "Spider-Man" };
            assertThrows(InvalidUserException.class, () -> {
                UserValidator.validate(userInfo, likedMovies);
            }, "Expected to throw InvalidUserException due to userId starting with a letter");
        }

        @Test
        @DisplayName("Test Case 10: userId ends with two letters (invalid)")
        void invalidCase10() {
            String[] userInfo = { "Tony Stark", "1234567AB" };
            String[] likedMovies = { "Iron Man", "Avengers" };
            assertThrows(InvalidUserException.class, () -> {
                UserValidator.validate(userInfo, likedMovies);
            }, "Expected to throw InvalidUserException because userId ends with two letters");
        }

}