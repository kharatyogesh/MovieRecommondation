package org.movie.repository;

import java.sql.ResultSet;

import org.movie.config.DBHelper;

public class AdminRepository extends DBHelper{


	public boolean isAdminValid(String id,String pass)
	{
		try
		{
			stmt=conn.prepareStatement("select * from admin where username = ? AND password = ?");
			stmt.setString(1, id);
			stmt.setString(2, pass);
			
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
	
	public boolean checkTheUsername(String user) 
	{
		try 
		{
			stmt = conn.prepareStatement("select username from admin where username = ?");
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
			stmt = conn.prepareStatement("update admin set password=? where username=?");

			stmt.setString(1, newPass);
			stmt.setString(2, user);
			

			int value = stmt.executeUpdate();

			return value > 0 ? true : false;
		} catch (Exception ex) {
			System.out.println("Error is " + ex);
			return false;
		}
	}
	
	
	
	
	
}
