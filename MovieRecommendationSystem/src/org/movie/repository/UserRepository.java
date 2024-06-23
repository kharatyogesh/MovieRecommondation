package org.movie.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.movie.config.DBHelper;
import org.movie.model.GenresMasterModel;
import org.movie.model.MovieMasterModel;
import org.movie.model.UserMasterModel;
import org.movie.model.UserRating;


public class UserRepository<Movie> extends DBHelper{


	public boolean isAddUser(UserMasterModel userModel) {
		try {
			stmt = conn.prepareStatement("insert into usermaster values('0',?,?,?,?)");
			stmt.setString(1, userModel.getUserid());
			stmt.setString(2, userModel.getName());
			stmt.setString(3, userModel.getCity());
			stmt.setString(4, userModel.getPassword());
		
		

			int value = stmt.executeUpdate();

			return value > 0 ? true : false;
		} catch (Exception ex) {
			System.out.println("Error is" + ex);
			return false;
		}

	}
	public boolean isUserValid(String con, String userpass) 
	{
		try
		{
			stmt=conn.prepareStatement("select * from usermaster where contact = ? AND password = ?");
			stmt.setString(1, con);
			stmt.setString(2, userpass);
			
			try(ResultSet rs=stmt.executeQuery()) 
			{
				if(rs.next()) 
				{
					return true;
				}
				else 
				{
					return false;
				}
			}
		}
		catch(Exception ex) 
		{
			System.out.println("Error is "+ex);
		}
		return false;
	}
	 public int getUserIdByContact(String contact) 
	 {
	        int userId = -1;
	        try 
	        {
	            String query = "select u_id from usermaster where contact = ?";
	            PreparedStatement stmt = conn.prepareStatement(query);
	            stmt.setString(1, contact);
	            ResultSet rs = stmt.executeQuery();

	            if (rs.next())
	            {
	                userId = rs.getInt("u_id");
	            }

	            rs.close();
	            stmt.close();
	        }
	        catch (SQLException e) 
	        {
	            e.printStackTrace();
	        }
	        return userId;
	    }
	 
	 
	 public boolean assignMovieToUser(int userId, int movieId) {
		    boolean result = false;
		    try {
		        stmt = conn.prepareStatement("insert  into movieuserjoin values('0',?,?)");
		        stmt.setInt(1, movieId);
		        stmt.setInt(2, userId);
		        
		        int rowsAffected = stmt.executeUpdate();
		        result = rowsAffected > 0;

		       
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    return result;
		}
	 
	 public int getMovieUserJoinId(int userId, int movieId) {
	        int umId = -1;
	        try {
	       
	            stmt = conn.prepareStatement("select um_id from movieuserjoin where u_id = ? and mov_id = ?");
	            stmt.setInt(1, userId);
	            stmt.setInt(2, movieId);
	            rs = stmt.executeQuery();

	            if (rs.next()) {
	                umId = rs.getInt("um_id");
	            }

	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return umId;
	    }
	 
	 public boolean addRating(int umId, float rating,String text) {
	        boolean result = false;
	        try {
	    
	            stmt = conn.prepareStatement("insert into rating values ('0',?,?,?)");
	            stmt.setInt(1, umId);
	            stmt.setFloat(2, rating);
	            stmt.setString(3, text);
	            
	            int rowsAffected = stmt.executeUpdate();
	            result = rowsAffected > 0;

	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return result;
	    }

	 
	 public List<MovieMasterModel> getMoviesByGenreId(int genreId) {
	        List<MovieMasterModel> movies = new ArrayList<>();
	        try {
	            stmt = conn.prepareStatement("select m.mov_id,m.mov_title,m.mov_rel_date ,g.gen_name from moviemaster m inner join moviegenrejoin mg on mg.mov_id=m.mov_id inner join genres g on g.gen_id=mg.gen_id where mg.gen_id = ?");
	            stmt.setInt(1, genreId);
	            ResultSet rs = stmt.executeQuery();

	            while (rs.next()) {
	                MovieMasterModel movie = new MovieMasterModel();
	                movie.setMovieId(rs.getInt("mov_id"));
	                movie.setMovTitle(rs.getString("mov_title"));
	                movie.setMovDate(rs.getString("mov_rel_date"));
	                movie.setGenName(rs.getString("gen_name"));
	               
	                movies.add(movie);
	            }

	         
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return movies;
	 }
	 
	 public List<MovieMasterModel> findMoviesByRatingRange(float minRating, float maxRating) 
	 {
	        List<MovieMasterModel> movies = new ArrayList<>();
	       
	        try 
	        {

	        	stmt = conn.prepareStatement("select m.mov_id, m.mov_title, m.mov_rel_date, g.gen_name,avg(r.rating) as avg_rating " +
                        "from moviemaster m " +
                        "join rating r on m.mov_id = r.um_id " +
                        "join moviegenrejoin mg on m.mov_id = mg.mov_id " +
                        "join genres g on mg.gen_id = g.gen_id " +
                        "group by  m.mov_id, m.mov_title, m.mov_rel_date, g.gen_name " +
                        "having avg(r.rating) between ? and ?");
	
	           	
	            stmt.setFloat(1, minRating);
	            stmt.setFloat(2, maxRating);
	            try ( ResultSet rs = stmt.executeQuery()) {
	                while (rs.next()) {
	                    MovieMasterModel movie = new MovieMasterModel();
	                    movie.setMovieId(rs.getInt("mov_id"));
	                    movie.setMovTitle(rs.getString("mov_title"));
	                    movie.setMovDate(rs.getString("mov_rel_date"));
	                    movie.setGenName(rs.getString("gen_name"));
	                    movie.setRating(rs.getFloat("avg_rating"));
	                    movies.add(movie);
	                }
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return movies;
	    }
	 
	 

		public boolean checkTheUsername(String user) 
		{
			try 
			{
				stmt = conn.prepareStatement("select contact from usermaster where contact = ?");
				stmt.setString(1, user);
				rs = stmt.executeQuery();
				return rs.next();
			}
			catch(Exception ex) 
			{
				System.out.println("Error is "+ex);
			}
			return false;
		}
		public boolean changePass(String user,String newPass) {

			try {
				stmt = conn.prepareStatement("update usermaster set password=? where contact=?");

				stmt.setString(1, newPass);
				stmt.setString(2, user);
				

				int value = stmt.executeUpdate();

				return value > 0 ? true : false;
			} catch (Exception ex) {
				System.out.println("Error is " + ex);
				return false;
			}
		}
		
		public List<MovieMasterModel> getWatchHistory(int userId) {
		    List<MovieMasterModel> watchHistory = new ArrayList<>();

		    try {
		        PreparedStatement stmt = conn.prepareStatement(
		            "SELECT m.mov_id, m.mov_title, m.mov_rel_date, r.rating, r.text " +
		            "FROM movieuserjoin muj " +
		            "JOIN moviemaster m ON muj.mov_id = m.mov_id " +
		            "LEFT JOIN rating r ON muj.um_id = r.um_id " +
		            "WHERE muj.u_id = ? " +
		            "AND r.rev_id = (SELECT MAX(r2.rev_id) FROM rating r2 WHERE r2.um_id = muj.um_id) " +
		            "ORDER BY m.mov_id"
		        );
		        stmt.setInt(1, userId);
		        ResultSet rs = stmt.executeQuery();

		        while (rs.next()) {
		            MovieMasterModel movie = new MovieMasterModel();
		            movie.setMovieId(rs.getInt("mov_id"));
		            movie.setMovTitle(rs.getString("mov_title"));
		            movie.setMovDate(rs.getString("mov_rel_date"));
		            movie.setRating(rs.getFloat("rating"));
		            movie.setText(rs.getString("text"));
		            watchHistory.add(movie);
		        }
		    } catch (SQLException e) {
		        System.out.println("Error fetching watch history: " + e.getMessage());
		    }

		    return watchHistory;
		}


		public List<UserRating> getAllUserRatings() {
	        List<UserRating> ratings = new ArrayList<>();
	        

	        try 
	        {
	              stmt = conn.prepareStatement("SELECT u_id, mov_id, rating FROM rating");
	             ResultSet rs = stmt.executeQuery();
	            while (rs.next()) {
	                int userId = rs.getInt("u_id");
	                int movieId = rs.getInt("mov_id");
	                double rating = rs.getDouble("rating");
	                ratings.add(new UserRating(userId, movieId, rating));
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	        return ratings;
	    }

		   public List<UserRating> getAllUserRating() {
		        List<UserRating> ratings = new ArrayList<>();

		        try {
		            PreparedStatement stmt = conn.prepareStatement("SELECT u_id, mov_id, rating FROM rating");
		            ResultSet rs = stmt.executeQuery();
		            while (rs.next()) {
		                int userId = rs.getInt("u_id");
		                int movieId = rs.getInt("mov_id");
		                double rating = rs.getDouble("rating");
		                ratings.add(new UserRating(userId, movieId, rating));
		            }
		        } catch (SQLException e) {
		            System.out.println("Error fetching user ratings: " + e.getMessage());
		        }
		        return ratings;
		    }
		   public boolean checkUserRating(int umId) {
		        try 
		        {
		            String query = "SELECT COUNT(*) FROM rating WHERE um_id = ?";
		            PreparedStatement stmt = conn.prepareStatement(query);
		            stmt.setInt(1, umId);
		            ResultSet rs = stmt.executeQuery();
		            if (rs.next()) {
		                return rs.getInt(1) > 0;
		            }
		        } catch (SQLException e) {
		            e.printStackTrace();
		        }
		        return false;
		    }

		   public boolean addOrUpdateRating(int umId, float rating, String text) {
			    try {
			        // Delete existing rating
			        String deleteQuery = "DELETE FROM rating WHERE um_id = ?";
			        PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
			        deleteStmt.setInt(1, umId);
			        deleteStmt.executeUpdate();

			        // Insert new rating
			        String insertQuery = "INSERT INTO rating (um_id, rating, text) VALUES (?, ?, ?)";
			        PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
			        insertStmt.setInt(1, umId);
			        insertStmt.setFloat(2, rating);
			        insertStmt.setString(3, text);
			        insertStmt.executeUpdate();

			        return true;
			    } catch (SQLException e) {
			        e.printStackTrace();
			        return false;
			    }
			}

}
