package model.logic;

import dao.CinemaDAO;
import model.data.Cinema;

public class CinemaSearchLogic {
	
	public Cinema search(String cinema_id) {
		CinemaDAO dao = new CinemaDAO();
		return dao.selectCinemaById(cinema_id);
	}

}
