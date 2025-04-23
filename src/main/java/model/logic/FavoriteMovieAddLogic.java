package model.logic;

import dao.FavoriteMovieDAO;

public class FavoriteMovieAddLogic {
	
	/*
	 * FavoriteMovieテーブルへ１件お気に入り登録を追加する
	 */
	public boolean add(String user_id, String movie_id) {
		// 引数の値チェック
		if (user_id == null || movie_id == null) {
			return false;
		}
		
		FavoriteMovieDAO dao = new FavoriteMovieDAO();
		int count = dao.addFavoriteMovie(user_id, movie_id);
		
		if (count == 1) {
			return true;
		} else {
			return false;
		}
	}

}
