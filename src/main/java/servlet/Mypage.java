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

import model.data.FavoriteCinema;
import model.data.FavoriteMovie;
import model.data.User;
import model.logic.FavoriteCinemaSearchLogic;
import model.logic.FavoriteMovieSearchLogic;

@WebServlet("/Mypage")
public class Mypage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/*
	 * ログイン状態のユーザのお気に入り登録されている映画館・作品、そして
	 * 予約中の作品のリストを表示する。
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forwardPath = "";	// フォワード先のパス
		
		// セッションスコープのログインユーザの取得
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		
		if (user == null) { // ログイン状態でない場合
			// ログイン画面へフォワード
			forwardPath = "Login";
			
		} else {
			// お気に入り登録されている映画館の取得
			FavoriteCinemaSearchLogic favoriteCinemaSearchLogic = new FavoriteCinemaSearchLogic();
			List<FavoriteCinema> favoriteCinemaList = favoriteCinemaSearchLogic.searchAll(user.getId());
			
			// お気に入り登録されている作品の取得
			FavoriteMovieSearchLogic favoriteMovieSearchLogic = new FavoriteMovieSearchLogic();
			List<FavoriteMovie> favoriteMovieList = favoriteMovieSearchLogic.searchAll(user.getId());
			
			/*
			 * 予約リストの取得
			 * 長迫さんのreservationクラスの確認　フィールド
			 */
			
			// リクエストスコープへ保存
			request.setAttribute("favoriteCinemaList", favoriteCinemaList);
			request.setAttribute("favoriteMovieList", favoriteMovieList);
			
			// マイページ画面へフォワード
			forwardPath = "WEB-INF/jsp/mypage.jsp";
		}
		
		
		// フォワード処理
		RequestDispatcher dispatcher = request.getRequestDispatcher(forwardPath);
		dispatcher.forward(request, response);
	}

}
