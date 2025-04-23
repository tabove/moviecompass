package model.logic;

import java.util.List;

import dao.ReservationDAO;
import model.data.Reservation;

public class ReservationSearchLogic {

	/*
	 * ReservationテーブルよりString user_idと一致するもの全件返す。
	 * なにも見つけられなかったら、NULLを返す。
	 */
	public List<Reservation> searchAll(String user_id){
		// user_idがNULLの場合は検索を行わない
		if (user_id == null) {
			return null;
		}
		
		ReservationDAO dao = new ReservationDAO();
		return dao.selectAll(user_id);
	}
	
}
