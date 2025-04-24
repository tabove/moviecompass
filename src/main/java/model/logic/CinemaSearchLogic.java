package model.logic;

import java.util.List;

import dao.CinemaDAO;
import model.data.Cinema;
import model.data.TheaterSearch;

public class CinemaSearchLogic {
	
	/*
	 * Cinemaテーブルより1件映画館情報を取得する
	 */
	public Cinema search(String cinema_id) {
		// cinema_idがNULLの場合は検索を行わない
		if (cinema_id == null) {
			return null;
		}
		CinemaDAO dao = new CinemaDAO();
		
		return dao.selectCinemaById(cinema_id);
	}
	
	/*
	 * Cinemaテーブルより全ての映画館のIDと名前をリストで返す
	 */
	public List<TheaterSearch> getTheaterList(){
		CinemaDAO dao = new CinemaDAO();
		return dao.theaterList();
	}

}
