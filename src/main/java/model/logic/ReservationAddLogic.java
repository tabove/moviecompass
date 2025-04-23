package model.logic;

import dao.ReservationDAO;
import model.data.Reservation;

public class ReservationAddLogic {
	public boolean add(Reservation reservation) {
		ReservationDAO dao = new ReservationDAO();
		return dao.create(reservation);
		
	}
}
