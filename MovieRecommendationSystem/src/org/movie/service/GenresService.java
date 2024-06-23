package org.movie.service;

import java.util.List;

import org.movie.model.GenresMasterModel;

import org.movie.repository.GenresRepository;

public class GenresService {
	GenresRepository genRepo = new GenresRepository();

	public List<GenresMasterModel> getAllGenres() {

		return genRepo.getAllGenres();
	}
	
	public boolean isAddGenre(GenresMasterModel gmodel) 
	{
	return genRepo.isAddGenre(gmodel);
	}
//	public boolean addBulkGenres() {
//		return genRepo.addBulkGenres();
//	}

	public int getGenreId(String genName) 
	{
		return genRepo.getGenreId(genName);
	}
	


	public List<GenresMasterModel> searchGenreById(int gid) {
		return genRepo.searchGenreById(gid);
	}

	public List<GenresMasterModel> searchGenreByName(String genreName) {
		return genRepo.searchGenreByName(genreName);
	}

	public boolean genreExistsById(int gid) {
		return genRepo.genreExistsById(gid);
	}

	public boolean updateGenreById(int upid, String genName) {
		return genRepo.updateGenreById(upid, genName);
	}

	public boolean genreExistsByName(String genreName) {
		return genRepo.genreExistsByName(genreName);
	}

	public boolean updateGenreByName(String upName, String genName) {
		return genRepo.updateGenreByName(upName, genName);
	}
	
	public boolean deleteGenreById(int genid) {
		return genRepo.deleteGenreById(genid);
	}

	public boolean deleteGenreByName(String upName) {
		return genRepo.deleteGenreById(upName);
	}
	
	


}
