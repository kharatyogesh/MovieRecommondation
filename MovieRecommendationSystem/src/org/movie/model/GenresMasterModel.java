package org.movie.model;

public class GenresMasterModel
{
	private int genId;
	private String genName;

	public GenresMasterModel() 
	{
		
	}
	
	public GenresMasterModel(int genId,String genName) 
	{
		this.genId=genId;
		this.genName=genName;
	}
	public int getGenId() {
		return genId;
	}

	public void setGenId(int genId) {
		this.genId = genId;
	}

	public String getGenName() {
		return genName;
	}

	public void setGenName(String genName) {
		this.genName = genName;
	}
		
}
