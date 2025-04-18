package model.logic;

import java.util.List;

import dao.FavoriteMovieDAO;
import model.data.FavoriteMovie;

public class FavoriteMovieSearchLogic {

	/*
	 * FavoriteMovieテーブルよりint user_idと一致するもの全件返す。
	 * なにも見つけられなかったら、NULLを返す。
	 */
	public List<FavoriteMovie> searchAll(String user_id){
		FavoriteMovieDAO dao = new FavoriteMovieDAO();
		return dao.selectFavoriteMovieByUserId(user_id);
	}
	
	/*
	 * FavoriteMovieテーブルよりint user_idとint movie_idと一致するものを１件返す。
	 * なにも見つけられなかったら、NULLを返す。
	 */
	public FavoriteMovie search(String user_id, String movie_id) {
		FavoriteMovieDAO dao = new FavoriteMovieDAO();
		return dao.selectFavoriteMovieByUserIdAndMovieId(user_id, movie_id);
	}
}
