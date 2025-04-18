package model.logic;

import dao.MovieDAO;
import model.data.Movie;

public class MovieSearchLogic {
	
	public Movie search(String movie_id) {
		MovieDAO dao = new MovieDAO();
		return dao.selectMovieById(movie_id);
	}
}
