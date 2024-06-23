package org.movie.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.movie.config.DBHelper;
import org.movie.model.MovieMasterModel;

import com.mysql.cj.jdbc.CallableStatement;

public class MovieRepository extends DBHelper {

    private int movid = 0;

    public boolean addNewMovie(MovieMasterModel model) {
        try {
            CallableStatement cstmt = (CallableStatement) conn.prepareCall("{call savemovie(?,?,?,?)}");
            cstmt.setInt(1, model.getMovieId());
            cstmt.setString(2, model.getMovTitle());
            cstmt.setString(3, model.getMovDate());
            cstmt.setInt(4, model.getGenId());
            boolean b = cstmt.execute();
            return !b;
        } catch (Exception ex) {
            System.out.println("Error is " + ex);
            return false;
        }
    }

    public List<MovieMasterModel> getAllMovie() {
        List<MovieMasterModel> movies = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(
                    "select m.mov_id,m.mov_title,m.mov_rel_date ,g.gen_name from moviemaster m inner join moviegenrejoin mg on mg.mov_id=m.mov_id inner join genres g on g.gen_id=mg.gen_id ORDER BY m.mov_id ASC");
            rs = stmt.executeQuery();
            while (rs.next()) {
                MovieMasterModel movie = new MovieMasterModel();
                movie.setMovieId(rs.getInt(1));
                movie.setMovTitle(rs.getString(2));
                movie.setMovDate(rs.getString(3));
                movie.setGenName(rs.getString(4));
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

    public int getMovieIdAutomatic() {
        try {
            stmt = conn.prepareStatement("select max(mov_id) from moviemaster");
            rs = stmt.executeQuery();
            if (rs.next()) {
                this.movid = rs.getInt(1);
            }
            ++movid;
            return movid;
        } catch (Exception ex) {
            System.out.println("Error is " + ex);
        }
        return -1;
    }

    public List<MovieMasterModel> searchMovieById(int mid) {
        List<MovieMasterModel> list = new ArrayList<MovieMasterModel>();
        try {
            stmt = conn.prepareStatement(
                    "select m.mov_id,m.mov_title,m.mov_rel_date ,g.gen_name from moviemaster m inner join moviegenrejoin mg on mg.mov_id=m.mov_id inner join genres g on g.gen_id=mg.gen_id where m.mov_id=?");
            stmt.setInt(1, mid);
            rs = stmt.executeQuery();
            while (rs.next()) {
                MovieMasterModel movie = new MovieMasterModel();
                movie.setMovieId(rs.getInt("mov_id"));
                movie.setMovTitle(rs.getString("mov_title"));
                movie.setMovDate(rs.getString("mov_rel_date"));
                movie.setGenName(rs.getString("gen_name"));
                list.add(movie);
            }
            return list.size() > 0 ? list : null;
        } catch (Exception ex) {
            System.out.println("Error is " + ex);
            return null;
        }
    }

    public List<MovieMasterModel> searchMovieByName(String movieName) {
        List<MovieMasterModel> list = new ArrayList<MovieMasterModel>();
        try {
            stmt = conn.prepareStatement(
                    "select m.mov_id,m.mov_title,m.mov_rel_date ,g.gen_name from moviemaster m inner join moviegenrejoin mg on mg.mov_id=m.mov_id inner join genres g on g.gen_id=mg.gen_id where m.mov_title like ?");
            stmt.setString(1, "%" + movieName + "%");
            rs = stmt.executeQuery();
            while (rs.next()) {
                MovieMasterModel model = new MovieMasterModel();
                model.setMovieId(rs.getInt("mov_id"));
                model.setMovTitle(rs.getString("mov_title"));
                model.setMovDate(rs.getString("mov_rel_date"));
                model.setGenName(rs.getString("gen_name"));
                list.add(model);
            }
            return list.size() > 0 ? list : null;
        } catch (Exception ex) {
            System.out.println("Error is " + ex);
            return null;
        }
    }

    public boolean updateMovieById(int upid, String newTitle, String newDate, int newGenreID) {
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("update moviemaster set mov_title=?,mov_rel_date=? where mov_id=?");
            stmt.setString(1, newTitle);
            stmt.setString(2, newDate);
            stmt.setInt(3, upid);
            int movieCount = stmt.executeUpdate();
            stmt.close();
            stmt = conn.prepareStatement("UPDATE moviegenrejoin SET gen_id = ? WHERE mov_id = ?");
            stmt.setInt(1, newGenreID);
            stmt.setInt(2, upid);
            int genreCount = stmt.executeUpdate();
            if (movieCount > 0 && genreCount > 0) {
                conn.commit();
                result = true;
            } else {
                conn.rollback();
            }
        } catch (Exception ex) {
            System.out.println("Error is " + ex);
            return false;
        }
        return result;
    }

    public boolean updateMovieByName(String upName, String newTitle, String newDate) {
        try {
            stmt = conn.prepareStatement("update moviemaster set mov_title=?,mov_rel_date=? where mov_title=?");
            stmt.setString(1, newTitle);
            stmt.setString(2, newDate);
            stmt.setString(3, upName);
            int value = stmt.executeUpdate();
            return value > 0;
        } catch (Exception ex) {
            System.out.println("Error is " + ex);
            return false;
        }
    }

    public boolean movieExistsById(int mid) {
        try {
            stmt = conn.prepareStatement("select mov_id from moviemaster where mov_id = ?");
            stmt.setInt(1, mid);
            rs = stmt.executeQuery();
            return rs.next(); // Returns true if there is at least one result, false otherwise
        } catch (Exception ex) {
            System.out.println("Error is " + ex);
            return false;
        }
    }

    public boolean movieExistsByName(String movieName) {
        try {
            stmt = conn.prepareStatement("select mov_id from moviemaster where mov_title = ?");
            stmt.setString(1, movieName);
            rs = stmt.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            System.out.println("Error is " + ex);
            return false;
        }
    }

    public boolean deleteMovieById(int upid) {
        try {
            stmt = conn.prepareStatement("delete from  moviemaster where mov_id=?");
            stmt.setInt(1, upid);
            int value = stmt.executeUpdate();
            return value > 0;
        } catch (Exception ex) {
            System.out.println("Error is " + ex);
            return false;
        }
    }

    public boolean deleteMovieByName(String upName) {
        try {
            stmt = conn.prepareStatement("delete from  moviemaster where mov_title=?");
            stmt.setString(1, upName);
            int value = stmt.executeUpdate();
            return value > 0;
        } catch (Exception ex) {
            System.out.println("Error is " + ex);
            return false;
        }
    }

    public int getMovieIdByUmId(int umId) {
        int movieId = -1;
        try {
            stmt = conn.prepareStatement("SELECT mov_id FROM movieuserjoin WHERE um_id = ?");
            stmt.setInt(1, umId);
            rs = stmt.executeQuery();
            if (rs.next()) {
                movieId = rs.getInt("mov_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieId;
    }

    public List<MovieMasterModel> getLatestThreeMovies() {
        List<MovieMasterModel> movies = new ArrayList<>();
        try {
            stmt = conn.prepareStatement(
                    "select m.mov_id,m.mov_title,m.mov_rel_date ,g.gen_name from moviemaster m inner join moviegenrejoin mg on mg.mov_id=m.mov_id inner join genres g on g.gen_id=mg.gen_id order by m.mov_rel_date desc limit 3");
            rs = stmt.executeQuery();
            while (rs.next()) {
                MovieMasterModel movie = new MovieMasterModel();
                movie.setMovieId(rs.getInt(1));
                movie.setMovTitle(rs.getString(2));
                movie.setMovDate(rs.getString(3));
                movie.setGenName(rs.getString(4)); // Make sure this is set correctly
                movies.add(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return movies;
    }

}

