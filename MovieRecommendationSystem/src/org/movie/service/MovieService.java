package org.movie.service;


import java.util.List;

import org.movie.model.MovieMasterModel;
import org.movie.repository.MovieRepository;

public class MovieService {

	MovieRepository movieRepo = new MovieRepository();

	public boolean addNewMovie(MovieMasterModel model) {

		return movieRepo.addNewMovie(model);
	}

	public List<MovieMasterModel> getAllMovie() {

		return movieRepo.getAllMovie();
	}
	public int  getMovieIdAutomatic() 
	{
		return movieRepo.getMovieIdAutomatic();
	}

	public List<MovieMasterModel> searchMovieById(int mid) {
		return movieRepo.searchMovieById(mid);
	}

	public List<MovieMasterModel> searchMovieByName(String movieName) {
		return movieRepo.searchMovieByName(movieName);
	}

	public boolean updateMovieById(int upid, String newTitle, String newDate,int newGenreID) {
		return movieRepo.updateMovieById(upid, newTitle, newDate,newGenreID);
	}

	public boolean updateMovieByName(String upName, String newTitle, String newDate) {
		return movieRepo.updateMovieByName(upName, newTitle, newDate);
	}

	public boolean movieExistsById(int mid) {
		return movieRepo.movieExistsById(mid);
	}

	public boolean movieExistsByName(String movieName) {
		return movieRepo.movieExistsByName(movieName);
	}

	public boolean deleteMovieById(int upid) {
		return movieRepo.deleteMovieById(upid);
	}

	public boolean deleteMovieByName(String upName) {
		return movieRepo.deleteMovieByName(upName);
	}
	
	public List<MovieMasterModel> getLatestThreeMovies() {

		return movieRepo.getLatestThreeMovies();
	}

	
}
