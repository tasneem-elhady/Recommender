package trial.Recommender;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trial.Movie;
import trial.User;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class MovieRecommenderTest {
    private MovieRecommender recommender;
    private Map<String, Movie> movieMap;
    private Map<String, User> userMap;
    private Map<String, Set<String>> genreMovies;

    @BeforeEach
    void setUp() {
        movieMap = new HashMap<>();
        userMap = new HashMap<>();
        genreMovies = new HashMap<>();

        genreMovies.put("action", new HashSet<>());
        genreMovies.put("sci-fi", new HashSet<>());
        genreMovies.put("drama", new HashSet<>());
        genreMovies.put("thriller", new HashSet<>());
        genreMovies.put("comedy", new HashSet<>());
        genreMovies.put("horror", new HashSet<>());
        genreMovies.put("romance", new HashSet<>());

        Movie[] movies = {
                new Movie("The Matrix", "MTX123", new String[]{"action", "sci-fi"}),
                new Movie("Inception", "ICP456", new String[]{"sci-fi", "thriller"}),
                new Movie("The Shawshank Redemption", "TSR789", new String[]{"drama"}),
                new Movie("Pulp Fiction", "PFN123", new String[]{"thriller", "drama"}),
                new Movie("The Dark Knight", "TDK456", new String[]{"action", "thriller"}),
                new Movie("Forrest Gump", "FGP789", new String[]{"drama", "romance"}),
                new Movie("The Silence of the Lambs", "SOL123", new String[]{"thriller", "horror"}),
                new Movie("Jurassic Park", "JRP456", new String[]{"action", "sci-fi"}),
                new Movie("The Hangover", "THG789", new String[]{"comedy"}),
                new Movie("Titanic", "TTN123", new String[]{"romance", "drama"})
        };

        for (Movie movie : movies) {
            movieMap.put(movie.getId(), movie);
            for (String genre : movie.getGenre()) {
                genreMovies.get(genre).add(movie.getId());
            }
        }

        User[] users = {
                createUser("John Doe", "123456781", new String[]{"MTX123", "ICP456"}),
                createUser("Jane Smith", "123456782", new String[]{"TSR789", "PFN123"}),
                createUser("Bob Johnson", "123456783", new String[]{"TDK456"}),
                createUser("Alice Brown", "123456784", new String[]{"FGP789", "TTN123"}),
                createUser("Charlie Davis", "123456785", new String[]{"SOL123"}),
                createUser("Eva Wilson", "123456786", new String[]{"JRP456", "THG789"}),
                createUser("David Miller", "123456787", new String[]{"MTX123", "TDK456", "JRP456"}),
                createUser("Sarah Taylor", "123456788", new String[]{"TTN123", "FGP789", "TSR789"}),
                createUser("Michael Anderson", "123456789", new String[]{"PFN123", "SOL123", "ICP456"}),
                createUser("Emma White", "123456790", new String[]{"THG789"})
        };

        for (User user : users) {
            userMap.put(user.getId(), user);
        }

        recommender = new MovieRecommender(movieMap, userMap, genreMovies);
    }

    private User createUser(String name, String id, String[] likedMovies) {
        User user = new User();
        user.name = name;
        user.id = id;
        user.likedMovies = likedMovies;
        return user;
    }

    @Test
    void testRecommendationContent() {
        String actualOutput = recommender.getRecommendationsString();
        Map<String, Set<String>> actualRecommendations = parseRecommendations(actualOutput);

        Map<String, Set<String>> expectedRecommendations = new HashMap<>();

        expectedRecommendations.put("123456781", new HashSet<>(Arrays.asList(
                "The Dark Knight", "Jurassic Park", "Pulp Fiction", "The Silence of the Lambs")));

        expectedRecommendations.put("123456782", new HashSet<>(Arrays.asList(
                "Forrest Gump", "Titanic", "The Silence of the Lambs", "Inception", "The Dark Knight")));

        expectedRecommendations.put("123456783", new HashSet<>(Arrays.asList(
                "The Matrix", "Jurassic Park", "Inception", "Pulp Fiction", "The Silence of the Lambs")));

        expectedRecommendations.put("123456784", new HashSet<>(Arrays.asList(
                "The Shawshank Redemption", "Pulp Fiction")));

        expectedRecommendations.put("123456785", new HashSet<>(Arrays.asList(
                "Inception", "Pulp Fiction", "The Dark Knight")));

        expectedRecommendations.put("123456786", new HashSet<>(Arrays.asList(
                "The Matrix", "The Dark Knight", "Inception")));

        expectedRecommendations.put("123456787", new HashSet<>(Arrays.asList(
                "Inception", "Pulp Fiction", "The Silence of the Lambs")));

        expectedRecommendations.put("123456788", new HashSet<>(Arrays.asList(
                "Pulp Fiction")));

        expectedRecommendations.put("123456789", new HashSet<>(Arrays.asList(
                "The Dark Knight", "Jurassic Park", "The Matrix", "Forrest Gump", "Titanic",
                "The Shawshank Redemption")));

        expectedRecommendations.put("123456790", new HashSet<>());

        assertEquals(expectedRecommendations.keySet(), actualRecommendations.keySet(),
                "Should have recommendations for all users");

        for (String userId : expectedRecommendations.keySet()) {
            assertEquals(expectedRecommendations.get(userId), actualRecommendations.get(userId),
                    "Recommendations for user " + userId + " should match regardless of order");

            System.out.println("User: " + userMap.get(userId).getName());
            System.out.println("Expected: " + expectedRecommendations.get(userId));
            System.out.println("Actual: " + actualRecommendations.get(userId));
            System.out.println();
        }
    }

    @Test
    void testUserWithNoRecommendations() {
        Map<String, Movie> testMovieMap = new HashMap<>();
        Map<String, User> testUserMap = new HashMap<>();
        Map<String, Set<String>> testGenreMovies = new HashMap<>();

        Movie actionMovie = new Movie("Action Movie", "ACT123", new String[]{"action"});
        testMovieMap.put("ACT123", actionMovie);
        testGenreMovies.put("action", new HashSet<>(Collections.singletonList("ACT123")));

        User actionFan = createUser("Action Fan", "999999999", new String[]{"ACT123"});
        testUserMap.put("999999999", actionFan);

        MovieRecommender testRecommender = new MovieRecommender(testMovieMap, testUserMap, testGenreMovies);
        String output = testRecommender.getRecommendationsString();
        Map<String, Set<String>> recommendations = parseRecommendations(output);

        assertTrue(recommendations.get("999999999").isEmpty(),
                "User who watched all movies in their genres should get no recommendations");
    }

    @Test
    void testMultipleGenreOverlap() {
        Map<String, Movie> testMovieMap = new HashMap<>();
        Map<String, User> testUserMap = new HashMap<>();
        Map<String, Set<String>> testGenreMovies = new HashMap<>();

        testGenreMovies.put("action", new HashSet<>());
        testGenreMovies.put("thriller", new HashSet<>());

        Movie movie1 = new Movie("Movie1", "MOV1", new String[]{"action", "thriller"});
        Movie movie2 = new Movie("Movie2", "MOV2", new String[]{"action"});
        Movie movie3 = new Movie("Movie3", "MOV3", new String[]{"thriller"});

        for (Movie movie : new Movie[]{movie1, movie2, movie3}) {
            testMovieMap.put(movie.getId(), movie);
            for (String genre : movie.getGenre()) {
                testGenreMovies.get(genre).add(movie.getId());
            }
        }

        User user = createUser("Test User", "TEST123", new String[]{"MOV1"});
        testUserMap.put("TEST123", user);

        MovieRecommender testRecommender = new MovieRecommender(testMovieMap, testUserMap, testGenreMovies);
        String output = testRecommender.getRecommendationsString();
        Map<String, Set<String>> recommendations = parseRecommendations(output);

        Set<String> expectedMovies = new HashSet<>(Arrays.asList("Movie2", "Movie3"));
        assertEquals(expectedMovies, recommendations.get("TEST123"),
                "Should recommend movies from both genres without duplicates");
    }

    @Test
    void testFormatValidity() {
        String output = recommender.getRecommendationsString();
        String[] lines = output.split("\n");

        assertEquals(userMap.size() * 2, lines.length, "Should have two lines per user");

        for (int i = 0; i < lines.length; i += 2) {
            String[] userInfo = lines[i].split(",");
            assertEquals(2, userInfo.length, "User info should have name and ID");
            assertTrue(userMap.containsKey(userInfo[1]), "User ID should exist in user map");

            if (!lines[i + 1].isEmpty()) {
                String[] recommendations = lines[i + 1].split(",");
                for (String movieTitle : recommendations) {
                    boolean movieExists = movieMap.values().stream()
                            .anyMatch(movie -> movie.getTitle().equals(movieTitle));
                    assertTrue(movieExists, "Recommended movie should exist in movie map");
                }
            }
        }
    }

    private Map<String, Set<String>> parseRecommendations(String output) {
        Map<String, Set<String>> recommendations = new HashMap<>();
        String[] lines = output.split("\n");

        for (int i = 0; i < lines.length; i++) {
            String[] userInfo = lines[i].split(",");
            String userId = userInfo[1];
            Set<String> movies = new HashSet<>();
            if (i + 1 < lines.length && !lines[i + 1].contains(",")) {
                if (!lines[i + 1].trim().isEmpty()) {
                    movies.addAll(Arrays.asList(lines[i + 1].split(",")));
                }
                i++;
            }
            recommendations.put(userId, movies);
        }

        return recommendations;
    }
}