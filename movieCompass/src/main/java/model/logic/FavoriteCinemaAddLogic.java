package model.logic;

import dao.FavoriteCinemaDAO;

public class FavoriteCinemaAddLogic {
	
	public boolean add(String user_id, String cinema_id) {
		FavoriteCinemaDAO dao = new FavoriteCinemaDAO();
		int count = dao.addFavoriteCinema(user_id, cinema_id);
		
		if (count == 1) {
			return true;
		} else {
			return false;
		}
	}

}
