package org.movie.repository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


import org.movie.config.DBHelper;
import org.movie.model.GenresMasterModel;
import org.movie.model.MovieMasterModel;

import com.mysql.cj.jdbc.CallableStatement;


public class GenresRepository extends DBHelper 
{
	

	public List<GenresMasterModel> getAllGenres() 
	{
		List<GenresMasterModel> list=new ArrayList<GenresMasterModel>();
		try {
			stmt = conn.prepareStatement("select * from genres");
			rs = stmt.executeQuery();

			while (rs.next()) {
				GenresMasterModel model = new GenresMasterModel();
				model.setGenId(rs.getInt(1));
				model.setGenName(rs.getString(2));
				list.add(model);
			}

			return list.size()>0?list:null;
		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return null;
		}

	}
	public boolean isAddGenre(GenresMasterModel gmodel) {

		try {
			stmt = conn.prepareStatement("insert into genres values('0',?)");
			stmt.setString(1, gmodel.getGenName());

			int value = stmt.executeUpdate();

			return value > 0 ? true : false;
		} catch (Exception ex) {
			System.out.println("Error is" + ex);
			return false;
		}

	}

//	public boolean addBulkGenres() {
//		try {
//			FileReader fr = new FileReader(org.movie.config.PathHelper.path + "genres.csv");
//			@SuppressWarnings("resource")
//			BufferedReader br = new BufferedReader(fr);
//			String line = null;
//			int value = 0;
//
//			while ((line = br.readLine()) != null) {
//				String data[] = line.split(",");
//				stmt = conn.prepareStatement("insert into genres values('0',?)");
//				stmt.setString(1, data[1]);
//				value = stmt.executeUpdate();
//			}
//
//			return value > 0 ? true : false;
//		} catch (Exception ex) {
//			System.out.println("Error is " + ex);
//			return false;
//		}
//	}
	
	public int getGenreId(String genName) 
	{
		try 
		{
			stmt=conn.prepareStatement("select gen_id from genres where gen_name=?");
			stmt.setString(1,genName);
			rs=stmt.executeQuery();
			if(rs.next()) 
			{
				return rs.getInt(1); //id indicate if gen_id is present consider genre name present in database table... 
			}
			else 
			{
				return -1;  //-1 indicate genre not present in database table.....
			}
		}
		catch(Exception ex) 
		{
			System.out.println("Error is "+ex);
		}
		return -1;
	}
	
	
	public List<GenresMasterModel> searchGenreById(int gid) 
	{
		
		List<GenresMasterModel> list = new ArrayList<GenresMasterModel>();
		try {
			stmt = conn.prepareStatement("select * from genres where gen_id= ?");
			stmt.setInt(1, gid);
			rs = stmt.executeQuery();

			while (rs.next()) {
				GenresMasterModel genre = new GenresMasterModel();
				genre.setGenId(rs.getInt(1));
				genre.setGenName(rs.getString(2));
				list.add(genre);
			}

			return list.size() > 0 ? list : null;
		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return null;
		}

	}
	
	
	public List<GenresMasterModel> searchGenreByName(String genreName) {
		List<GenresMasterModel> list = new ArrayList<GenresMasterModel>();
		try {
			stmt = conn.prepareStatement("select * from genres where gen_name like ?");

			stmt.setString(1, "%" + genreName + "%");
			rs = stmt.executeQuery();

			while(rs.next()) {
				GenresMasterModel genre = new GenresMasterModel();
				genre.setGenId(rs.getInt(1));
				genre.setGenName(rs.getString(2));
				

				list.add(genre);
			}

			return list.size() > 0 ? list : null;
		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return null;
		}

	}
	
	
	
	public boolean genreExistsById(int gid) {
		try {
			stmt = conn.prepareStatement("select gen_id from genres where gen_id = ?");
			stmt.setInt(1, gid);
			rs = stmt.executeQuery();

			return rs.next(); // Returns true if there is at least one result, false otherwise
		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return false;
		}
	}
	
	
	public boolean updateGenreById(int genid, String genName) {

		try {
			stmt = conn.prepareStatement("update genres set gen_name=? where gen_id=?");

			stmt.setString(1, genName);
			stmt.setInt(2, genid);

			int value = stmt.executeUpdate();

			return value > 0 ? true : false;
		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return false;
		}

	}
	
	public boolean genreExistsByName(String genName) {
		try {
			stmt = conn.prepareStatement("select gen_id from genres where gen_name = ?");
			stmt.setString(1, genName);
			rs = stmt.executeQuery();
			return rs.next();
		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return false;
		}
	}
	
	public boolean updateGenreByName(String upName, String genName) {

		try {
			stmt = conn.prepareStatement("update genres set gen_name=? where gen_name =?");

			stmt.setString(1, genName);
			stmt.setString(2, upName);

			int value = stmt.executeUpdate();

			return value > 0 ? true : false;
		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return false;
		}
	}
	
	
	public boolean deleteGenreById(int genid) {

		try {
			stmt = conn.prepareStatement("delete from  genres where gen_id=?");
			stmt.setInt(1, genid);
			int value = stmt.executeUpdate();

			return value > 0 ? true : false;
		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return false;
		}
	}

	public boolean deleteGenreById(String upName) {

		try {
			stmt = conn.prepareStatement("delete from  genres where gen_name=?");
			stmt.setString(1, upName);
			int value = stmt.executeUpdate();

			return value > 0 ? true : false;
		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return false;
		}
	}
	
	

}
