package model.logic;

import java.util.List;

import dao.MovieDAO;
import model.data.Movie;

public class MovieSearchLogic {
	
	/*
	 * Movieテーブルより１件作品情報を取得する
	 */
	public Movie search(String movie_id) {
		// movie_idがNULLの場合は検索を行わない
		if (movie_id == null) {
			return null;
		}
		MovieDAO dao = new MovieDAO();
		
		return dao.selectMovieById(movie_id);
	}
	
	/*
	 * Movieテーブルより全ての作品のジャンルを
	 * 重複無しでリストで返す
	 */
	public List<String> getGenreList(){
		MovieDAO dao = new MovieDAO();
		return dao.genreList();
	}
}
