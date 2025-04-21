package model.logic;

import java.util.List;

import dao.MovieScheduleDAO;
import model.data.MovieSchedule;

public class MovieScheduleSearchLogic {
	
	private MovieScheduleDAO dao = new MovieScheduleDAO();
	
	/* 映画タイトル検索*/
	public List<MovieSchedule> searchMovie(String cinema_id, String movie_name, String cinema_name, String movie_id, String genre, String date, String dateTime) {
	    if(date == null) date = "";
	    if(dateTime == null) dateTime = "";
	    
	    return dao.searchMovie(cinema_id, movie_name, cinema_name, movie_id, genre, date, dateTime);
	}
}
