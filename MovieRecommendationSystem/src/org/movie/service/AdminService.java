package org.movie.service;

import org.movie.repository.AdminRepository;

public class AdminService {

	AdminRepository adminRepo=new AdminRepository();
	
	public boolean isAdminValid(String id,String pass) 
	{
		return adminRepo.isAdminValid(id,pass);
	}
	public boolean checkTheUsername(String user) 
	{
		return adminRepo.checkTheUsername(user);
	}
	public boolean changePass(String user,String newPass) 
	{
		return adminRepo.changePass(user,newPass);
	}
}
