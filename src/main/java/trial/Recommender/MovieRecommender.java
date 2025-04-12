package trial.Recommender;
import trial.Movie;
import trial.UserValidator;
import trial.Parser.MovieParser;
import trial.Parser.UserParser;

import java.util.*;

public class MovieRecommender {
    private final Map<String, Movie> movieMap;
    private final Map<String, User> userMap;
    private final Map<String, Set<String>> genreMovies;

    public MovieRecommender(Map<String, Movie> movieMap, Map<String, User> userMap, Map<String, Set<String>> genreMovies) {
        this.movieMap = movieMap;
        this.userMap = userMap;
        this.genreMovies = genreMovies;
    }

    public Map<String, List<String>> generateRecommendations() {
        Map<String, List<String>> recommendations = new HashMap<>();

        for (User user : userMap.values()) {
            Set<String> recommendedMovies = new HashSet<>();

            for (String likedMovieId : user.getLikedMovies()) {
                Movie likedMovie = movieMap.get(likedMovieId);
                if (likedMovie != null) {
                    for (String genre : likedMovie.getGenre()) {
                        Set<String> moviesInGenre = genreMovies.get(genre);
                        if (moviesInGenre != null) {
                            recommendedMovies.addAll(moviesInGenre);
                        }
                    }
                }
            }

            recommendedMovies.removeAll(Arrays.asList(user.getLikedMovies()));

            List<String> recommendedTitles = new ArrayList<>();
            for (String movieId : recommendedMovies) {
                Movie movie = movieMap.get(movieId);
                if (movie != null) {
                    recommendedTitles.add(movie.getTitle());
                }
            }
            recommendations.put(user.getId(), recommendedTitles);
        }
        return recommendations;
    }

    public String getRecommendationsString() {
        Map<String, List<String>> recommendations = generateRecommendations();
        StringBuilder output = new StringBuilder();
        boolean isFirst = true;

        for (Map.Entry<String, User> userEntry : userMap.entrySet()) {
            User user = userEntry.getValue();
            List<String> userRecommendations = recommendations.get(user.getId());

            if (!isFirst) {
                output.append("\n");
            }
            isFirst = false;

            output.append(user.getName()).append(",").append(user.getId());

            output.append("\n");
            if (userRecommendations != null && !userRecommendations.isEmpty()) {
                output.append(String.join(",", userRecommendations));
            }
        }

        return output.toString();
    }
}