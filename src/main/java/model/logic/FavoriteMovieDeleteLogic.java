package model.logic;

import dao.FavoriteMovieDAO;

public class FavoriteMovieDeleteLogic {

	public boolean delete(String user_id, String movie_id) {
		FavoriteMovieDAO dao = new FavoriteMovieDAO();
		int count = dao.deleteFavoriteMovie(user_id, movie_id);
		
		if (count == 1) {
			return true;
		} else {
			return false;
		}
	}
}
