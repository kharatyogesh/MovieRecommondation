package org.movie.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.movie.config.DBHelper;
import org.movie.model.RatingModel;

public class RatingRepository extends DBHelper {

    public List<RatingModel> getAllRatings() {
        List<RatingModel> ratings = new ArrayList<>();
        try {
            stmt = conn.prepareStatement("SELECT rev_id, um_id, rating, text FROM rating");
            rs = stmt.executeQuery();
            while (rs.next()) {
                RatingModel rating = new RatingModel();
                rating.setRevId(rs.getInt("rev_id"));
                rating.setUmId(rs.getInt("um_id"));
                rating.setRating(rs.getFloat("rating"));
                rating.setText(rs.getString("text"));
                ratings.add(rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
            // Check if the rating exists
            String checkQuery = "SELECT COUNT(*) FROM rating WHERE um_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, umId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                // Update the existing rating
                String updateQuery = "UPDATE rating SET rating = ?, text = ? WHERE um_id = ?";
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setFloat(1, rating);
                updateStmt.setString(2, text);
                updateStmt.setInt(3, umId);
                updateStmt.executeUpdate();
            } else {
                // Insert a new rating
                String insertQuery = "INSERT INTO rating (um_id, rating, text) VALUES (?, ?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setInt(1, umId);
                insertStmt.setFloat(2, rating);
                insertStmt.setString(3, text);
                insertStmt.executeUpdate();
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}