package model.logic;

import dao.FavoriteMovieDAO;

public class FavoriteMovieDeleteLogic {

	/*
	 * FavoriteMovieテーブルより１件お気に入り登録を削除する
	 */
	public boolean delete(String user_id, String movie_id) {
		// 引数の値チェック
		if (user_id == null || movie_id == null) {
			return false;
		}
		
		FavoriteMovieDAO dao = new FavoriteMovieDAO();
		int count = dao.deleteFavoriteMovie(user_id, movie_id);
		
		if (count == 1) {
			return true;
		} else {
			return false;
		}
	}
}
