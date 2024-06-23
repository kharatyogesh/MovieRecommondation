package org.movie.model;

public class MovieRating {
    private MovieMasterModel movie;
    private float averageRating;

    public MovieRating(MovieMasterModel movie, float averageRating) {
        this.movie = movie;
        this.averageRating = averageRating;
    }

    public MovieMasterModel getMovie() {
        return movie;
    }

    public float getAverageRating() {
        return averageRating;
    }
}
