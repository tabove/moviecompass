package model.logic;

import dao.ReservationDAO;

public class ReservationAddLogic {
	
	public boolean add(String user_id, String cinema_id, String movie_id, 
						String movie_time, int repTicket_price) {
		ReservationDAO dao = new ReservationDAO();
		return dao.create(user_id, cinema_id, movie_id, movie_time, repTicket_price);
	}
}
