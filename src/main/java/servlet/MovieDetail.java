package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.data.FavoriteMovie;
import model.data.Movie;
import model.data.User;
import model.logic.FavoriteMovieAddLogic;
import model.logic.FavoriteMovieDeleteLogic;
import model.logic.FavoriteMovieSearchLogic;
import model.logic.MovieSearchLogic;

@WebServlet("/MovieDetail")
public class MovieDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/*
	 * movie_idをリクエストパラメータでもらってその情報をDBより表示する
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String movie_id = request.getParameter("movie_id");
		
		// セッションスコープからログイン状況の確認
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		
		if (user != null) { // ログイン状態である
			// お気に入り登録されているかの確認
			FavoriteMovieSearchLogic favoriteMovieSearchLogic = new FavoriteMovieSearchLogic();
			FavoriteMovie favoriteMovie = favoriteMovieSearchLogic.search(user.getId(), movie_id);
			
			// リクエストスコープへ保存
			request.setAttribute("favoriteMovie", favoriteMovie);
		}
		
		// 作品データを作品テーブルより取得
		MovieSearchLogic movieSearchLogic = new MovieSearchLogic();
		Movie movie = movieSearchLogic.search(movie_id);
		
		// リクエストスコープへ保存
		request.setAttribute("movie", movie);
		
		// フォワード処理
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/movieDetail.jsp");
		dispatcher.forward(request, response);
	}

	/*
	 * actionパラメータをもらって"register"の場合はお気に入り登録処理、
	 * "delete"の場合はお気に入り登録削除処理を行う
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String msg = ""; 	// 表示するメッセージ
		
		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		String movie_id = request.getParameter("movie_id");
		
		// セッションスコープのログインユーザを取得
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		
		if (user == null) { // ログイン状態でない場合
			msg = "お気に入り登録を行うにはログインしてください";
			
		} else if ("register".equals(action)) {	// 登録処理
			// すでにお気に入り登録されているかのチェック
			FavoriteMovieSearchLogic favoriteMovieSearchLogic = new FavoriteMovieSearchLogic();
			FavoriteMovie favoriteMovie = favoriteMovieSearchLogic.search(user.getId(), movie_id);
			
			if (favoriteMovie != null) {	// お気に入り登録がすでにされている
				msg = "すでにお気に入り登録されています";
				
			} else { // まだ未登録の場合
				// 登録処理を行う
				FavoriteMovieAddLogic favoriteMovieAddLogic = new FavoriteMovieAddLogic();
				boolean isAdd = favoriteMovieAddLogic.add(user.getId(), movie_id);
				
				if (!isAdd) { // 登録に失敗
					msg = "お気に入り登録に失敗しました";
				} else {
					msg = "お気に入り登録されました";
				}
			}
			
		} else if ("delete".equals(action)) { // 削除処理
			FavoriteMovieDeleteLogic favoriteMovieDeleteLogic = new FavoriteMovieDeleteLogic();
			boolean isDelete = favoriteMovieDeleteLogic.delete(user.getId(), movie_id);
			
			if (!isDelete) { // 削除に失敗
				msg = "お気に入り登録から削除に失敗しました";
			} else {
				msg = "お気に入り登録から削除されました";
			}
		}
		
		// メッセージをリクエストスコープへ保存
		request.setAttribute("msg", msg);
		
		// 残りの処理は通常のゲットメソッド
		doGet(request, response);
	}

}
