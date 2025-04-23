package model.logic;

import dao.FavoriteCinemaDAO;

public class FavoriteCinemaDeleteLogic {
	
	/*
	 * FavoriteCinemaテーブルより１件お気に入り登録を削除する
	 */
	public boolean delete(String user_id, String cinema_id) {
		// 引数の値チェック
		if (user_id == null || cinema_id == null) {
			return false;
		}
		
		FavoriteCinemaDAO dao = new FavoriteCinemaDAO();
		int count = dao.deleteFavoriteCinema(user_id, cinema_id);
		
		if (count == 1) {
			return true;
		} else {
			return false;
		}
	}

}
