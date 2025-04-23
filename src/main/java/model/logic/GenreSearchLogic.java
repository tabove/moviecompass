package model.logic;

import java.util.List;

import dao.MovieScheduleDAO;

public class GenreSearchLogic {
	
	public List<String> getGenreList(){
		MovieScheduleDAO dao = new MovieScheduleDAO();
		return dao.genreList();
	}
}
