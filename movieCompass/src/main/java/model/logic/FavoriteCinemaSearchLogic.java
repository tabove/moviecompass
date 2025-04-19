package model.logic;

import java.util.List;

import dao.FavoriteCinemaDAO;
import model.data.FavoriteCinema;

public class FavoriteCinemaSearchLogic {
	
	/*
	 * FavoriteCinemaテーブルよりint user_idと一致するもの全件返す。
	 * なにも見つけられなかったら、NULLを返す。
	 */
	public List<FavoriteCinema> searchAll(String user_id){
		FavoriteCinemaDAO dao = new FavoriteCinemaDAO();
		return dao.selectFavoriteCinemaByUserId(user_id);
	}
	
	/*
	 * FavoriteCinemaテーブルよりint user_idとint cinema_idと一致するものを１件返す。
	 * なにも見つけられなかったら、NULLを返す。
	 */
	public FavoriteCinema search(String user_id, String cinema_id) {
		FavoriteCinemaDAO dao = new FavoriteCinemaDAO();
		return dao.selectFavoriteCinemaByUserIdAndCinemaId(user_id, cinema_id);
	}

}
