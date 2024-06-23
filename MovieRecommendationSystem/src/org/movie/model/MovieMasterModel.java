package org.movie.model;

public class MovieMasterModel extends GenresMasterModel{

	private int movieId;
	private String movTitle;
	private String movDate;
	
	private float rating;
	private String text;

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getMovieId() {
		return movieId;
	}

	public void setMovieId(int movieId) {
		this.movieId = movieId;
	}

	public String getMovTitle() {
		return movTitle;
	}

	public void setMovTitle(String movTitle) {
		this.movTitle = movTitle;
	}

	public String getMovDate() {
		return movDate;
	}

	public void setMovDate(String movDate) {
		this.movDate = movDate;
	}

}
