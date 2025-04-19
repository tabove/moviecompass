package model.data;

import java.util.List;

import DAO.MovieScheduleDAO;

public class GenreSearch {
	
	private MovieScheduleDAO dao = new MovieScheduleDAO();
	
	public List<String> getGenreList(){
		return dao.genreList();
	}
}
