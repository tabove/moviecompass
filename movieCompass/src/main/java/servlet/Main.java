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

import DAO.MovieScheduleDAO;
import model.data.GenreSearch;
import model.data.MovieSchedule;
import model.data.TheaterSearch;
import model.logic.MovieScheduleSearchLogic;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		MovieScheduleSearchLogic mssl = new MovieScheduleSearchLogic();
		GenreSearch gs = new GenreSearch();
		MovieScheduleDAO dao = new MovieScheduleDAO();
		
		// パラメータ取得
		String cinema_name = request.getParameter("cinema_name");
		String movie_name = request.getParameter("movieName");
	    String genre = request.getParameter("genre");
	    String date = request.getParameter("date");
	    String dateTime = request.getParameter("dateTime");
		
	    // 検索に必要なリスト取得	
	    List<TheaterSearch> theaterList = dao.theaterList();
	    List<String> genreList = gs.getGenreList();
	    
		// Nullチェック（リファクタリング）
		cinema_name = (cinema_name != null) ? cinema_name : "";
		movie_name = (movie_name != null) ? movie_name : "";
		genre = (genre != null) ? genre : "" ;
		
		// 検索処理
		List<MovieSchedule> results = mssl.searchMovie(movie_name, cinema_name, genre, date, dateTime);
		
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
