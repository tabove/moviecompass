package model.logic;

import java.util.List;

import dao.FavoriteMovieDAO;
import model.data.FavoriteMovie;

public class FavoriteMovieSearchLogic {

	/*
	 * FavoriteMovieテーブルよりString user_idと一致するもの全件返す。
	 * なにも見つけられなかったら、NULLを返す。
	 */
	public List<FavoriteMovie> searchAll(String user_id){
		// user_idがNULLの場合は検索を行わない
		if (user_id == null) {
			return null;
		}
		
		FavoriteMovieDAO dao = new FavoriteMovieDAO();
		return dao.selectFavoriteMovieByUserId(user_id);
	}
	
	/*
	 * FavoriteMovieテーブルよりString user_idとString movie_idと一致するものを１件返す。
	 * なにも見つけられなかったら、NULLを返す。
	 */
	public FavoriteMovie search(String user_id, String movie_id) {
		// 引数の値チェック
		if (user_id == null || movie_id == null) {
			return null;
		}
		
		FavoriteMovieDAO dao = new FavoriteMovieDAO();
		return dao.selectFavoriteMovieByUserIdAndMovieId(user_id, movie_id);
	}
	
}
