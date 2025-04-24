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
	
	/*
	 * ReservationテーブルよりString user_id, String cinema_id,
	 * String movie_id, String movie_timeと一致するものを１件検索する。
	 * 1件レコードが見つけられたら、trueを返す。
	 * なにも見つけられなかったら、falseを返す。
	 */
	public boolean searchDuplicate(String user_id, String cinema_id, 
							String movie_id, String movie_time) {
		// 引数の値チェック
		if (user_id == null || cinema_id == null || movie_id == null || movie_time == null) {
			return false;
		}
		
		ReservationDAO dao = new ReservationDAO();
		Reservation r = dao.selectDuplicate(user_id, cinema_id, movie_id, movie_time);
		
		if (r != null) {
			return true;
		} else {
			return false;
		}
	}
	
}
