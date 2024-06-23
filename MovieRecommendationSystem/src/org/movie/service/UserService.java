package org.movie.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.movie.model.MovieMasterModel;
import org.movie.model.UserMasterModel;
import org.movie.model.UserRating;
import org.movie.repository.UserRepository;
import org.movie.repository.RatingRepository;

public class UserService {
	private RatingRepository ratingRepo = new RatingRepository();
	private Map<Integer, Map<Integer, Double>> userRatings = new HashMap<>();
    private Map<Integer, Integer> movieGenreMap = new HashMap<>();
    private Map<Integer, List<Double>> genreRatings = new HashMap<>();
    private Map<Integer, Double> genreRatingProbabilities = new HashMap<>();

	UserRepository userRepo=new UserRepository();
	RatingRepository rateRepo=new RatingRepository();
	public boolean isAddUser(UserMasterModel userModel) 
	{
		return userRepo.isAddUser(userModel);
	}
	
	public boolean isUserValid(String con, String userpass)
	{
		return userRepo.isUserValid(con,userpass);
	}
	public int getUserIdByContact(String contact) {
        return userRepo.getUserIdByContact(contact);
    }
	public boolean assignMovieToUser(int userId, int movieId) 
	{
		return userRepo.assignMovieToUser(userId, movieId);
	}
	
	public int getMovieUserJoinId(int userId, int movieId) {
        return userRepo.getMovieUserJoinId(userId, movieId);
    }
	
	public boolean addRating(int umId, float rating,String text) {
        return userRepo.addRating(umId, rating,text);
    }
	
	public List<MovieMasterModel> getMoviesByGenreId(int genreId) 
	{
		return userRepo.getMoviesByGenreId(genreId);
	}
	public List<MovieMasterModel> getMoviesByRatingRange(float minRating, float maxRating) {
        return userRepo.findMoviesByRatingRange(minRating, maxRating);
    }
	public boolean checkTheUsername(String user) 
	{
		return userRepo.checkTheUsername(user);
	}
	public boolean changePass(String user,String newPass) 
	{
		return userRepo.changePass(user,newPass);
	}
	
	 public List<MovieMasterModel> getWatchHistory(int userId) {
	        return userRepo.getWatchHistory(userId);
	    }

	public boolean hasUserRatedMovie(int userId) {
       
        return ratingRepo.checkUserRating(userId);
    }

   
	public boolean addOrUpdateRating(int umId, float rating, String text) {
	    // Check if rating or text is null or empty, and handle it appropriately
	    if (rating < 0 || rating > 5) {
	        System.out.println("Rating must be between 0 and 5.");
	        return false;
	    }

	    // Default text if it's null or empty
	    if (text == null || text.isEmpty()) {
	        text = "No review provided";
	    }

	    // Update or add rating in repository
	    boolean ratingAdded = ratingRepo.addOrUpdateRating(umId, rating, text);

	    if (ratingAdded) {
	        System.out.println("Rating added successfully.");

	        // Optional: Update watch history if needed
	        // Here, you can call a method to update the user's watch history
	        // Example: updateWatchHistory(userId);

	        return true;
	    } else {
	        System.out.println("Failed to add or update rating.");
	        return false;
	    }
	}
}
