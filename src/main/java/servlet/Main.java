package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.data.MovieSchedule;
import model.data.TheaterSearch;
import model.logic.CinemaSearchLogic;
import model.logic.MovieScheduleSearchLogic;
import model.logic.MovieSearchLogic;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
				
		MovieScheduleSearchLogic mssl = new MovieScheduleSearchLogic();
		MovieSearchLogic movieSearch = new MovieSearchLogic();
		CinemaSearchLogic cinemaSearch = new CinemaSearchLogic();
		
		// パラメータ取得
		String cinema_id = request.getParameter("cinema_id");
		String cinema_name = request.getParameter("cinema_name");
		String movie_name = request.getParameter("movie_name");
		String movie_id = request.getParameter("movie_id");
	    String genre = request.getParameter("genre");
	    String date = request.getParameter("date");
	    String dateTime = request.getParameter("dateTime");
	    
//	 // デバッグ出力を追加
//	    System.out.println("パラメータ確認: movie_name=" + movie_name + 
//	                      ", cinema_id=" + cinema_id + 
//	                      ", genre=" + genre + 
//	                      ", date=" + date + 
//	                      ", dateTime=" + dateTime);

		
	    
		// Nullチェック（リファクタリング）
		cinema_id = (cinema_id != null) ? cinema_id : "";
		movie_name = (movie_name != null) ? movie_name : "";
		genre = (genre != null) ? genre : "" ;
		
		// 検索に必要なドロップダウン項目を作成する中身
		List<TheaterSearch> theaterList = (List<TheaterSearch>)session.getAttribute("theaterList");
		List<String> genreList = (List<String>)session.getAttribute("genreList");
		// 検索に必要なリストがセッションスコープにない場合に取得する
		if (theaterList == null) {
			theaterList = cinemaSearch.getTheaterList();
		}
		if (genreList == null) {
			genreList = movieSearch.getGenreList();
		}
		
		// 検索処理
		List<MovieSchedule> results = mssl.searchMovie(cinema_id, movie_name, cinema_name, movie_id, genre, date, dateTime);
		
		// セッションに検索条件保存
		session.setAttribute("genreList", genreList);
		session.setAttribute("theaterList", theaterList);

		// 検索結果をリクエストスコープへ保存
		request.setAttribute("searchResults", results);
		
		// JSPへフォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
