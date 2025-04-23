package model.logic;

import java.util.List;

import dao.FavoriteCinemaDAO;
import model.data.FavoriteCinema;

public class FavoriteCinemaSearchLogic {
	
	/*
	 * FavoriteCinemaテーブルよりString user_idと一致するもの全件返す。
	 * なにも見つけられなかったら、NULLを返す。
	 */
	public List<FavoriteCinema> searchAll(String user_id){
		// user_idがNULLの場合は検索を行わない
		if (user_id == null) {
			return null;
		}
		
		FavoriteCinemaDAO dao = new FavoriteCinemaDAO();
		return dao.selectFavoriteCinemaByUserId(user_id);
	}
	
	/*
	 * FavoriteCinemaテーブルよりString user_idとString cinema_idと一致するものを１件返す。
	 * なにも見つけられなかったら、NULLを返す。
	 */
	public FavoriteCinema search(String user_id, String cinema_id) {
		// 引数の値チェック
		if (user_id == null || cinema_id == null) {
			return null;
		}
		
		FavoriteCinemaDAO dao = new FavoriteCinemaDAO();
		return dao.selectFavoriteCinemaByUserIdAndCinemaId(user_id, cinema_id);
	}

}
