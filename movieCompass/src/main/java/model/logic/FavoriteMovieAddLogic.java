package model.logic;

import dao.FavoriteMovieDAO;

public class FavoriteMovieAddLogic {
	
	public boolean add(String user_id, String movie_id) {
		FavoriteMovieDAO dao = new FavoriteMovieDAO();
		int count = dao.addFavoriteMovie(user_id, movie_id);
		
		if (count == 1) {
			return true;
		} else {
			return false;
		}
	}

}
