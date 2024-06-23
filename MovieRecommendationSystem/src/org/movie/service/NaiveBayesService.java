package org.movie.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.movie.model.MovieMasterModel;
import org.movie.model.MovieRating;
import org.movie.model.RatingModel;
import org.movie.repository.MovieRepository;
import org.movie.repository.RatingRepository;

public class NaiveBayesService {

    private MovieRepository movieRepo;
    private RatingRepository ratingRepo;

    public NaiveBayesService(MovieRepository movieRepo, RatingRepository ratingRepo) {
        this.movieRepo = movieRepo;
        this.ratingRepo = ratingRepo;
    }

    public List<MovieRating> recommendMovies(int topN) {
        List<MovieMasterModel> allMovies = movieRepo.getAllMovie();
        List<RatingModel> allRatings = ratingRepo.getAllRatings();

        // Calculate average ratings for each movie
        Map<Integer, List<Float>> movieRatingsMap = new HashMap<>();
        for (RatingModel rating : allRatings) {
            int movieId = movieRepo.getMovieIdByUmId(rating.getUmId());
            if (movieId != -1) {
                movieRatingsMap.computeIfAbsent(movieId, k -> new ArrayList<>()).add(rating.getRating());
            }
        }

        // Calculate average rating and store in a map
        Map<MovieMasterModel, Float> movieAverageRatings = new HashMap<>();
        for (MovieMasterModel movie : allMovies) {
            List<Float> ratings = movieRatingsMap.get(movie.getMovieId());
            if (ratings != null && !ratings.isEmpty()) {
                float averageRating = calculateAverage(ratings);
                movieAverageRatings.put(movie, averageRating);
            }
        }

        // Sort and select top N movies based on average rating
        List<MovieRating> sortedTopMovies = movieAverageRatings.entrySet().stream()
                .sorted(Map.Entry.<MovieMasterModel, Float>comparingByValue().reversed())
                .limit(topN)
                .map(entry -> new MovieRating(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return sortedTopMovies;
    }

    private float calculateAverage(List<Float> ratings) {
        if (ratings == null || ratings.isEmpty()) return 0.0f;
        float sum = 0.0f;
        for (float rating : ratings) {
            sum += rating;
        }
        return sum / ratings.size();
    }
}
