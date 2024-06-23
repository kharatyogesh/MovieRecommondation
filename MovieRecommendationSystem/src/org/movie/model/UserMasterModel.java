package org.movie.model;

public class UserMasterModel {

	private String userid;
	private String password;
	private String name;
	private String city;
	
	public UserMasterModel() 
	{
		
	}
	public UserMasterModel(String userid,String password,String name,String city) 
	{
		this.userid=userid;
		this.password=password;
		this.name=name;
		this.city=city;
	}
	

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
