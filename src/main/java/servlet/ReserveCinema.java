package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *予約確定を押下⇒予約完了画面へ遷移、DBへ追加とか？
 */
@WebServlet("/ReserveCinema")
public class ReserveCinema extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ログイン確認　⇒　余裕があれば追加する機能でした
		// セッションスコープからユーザ情報を取得
//		HttpSession session = request.getSession();
//		User loginUser = (User)session.getAttribute("loginUser");
//		
//		if(loginUser == null) {
//			response.sendRedirect("WEB-INF/jsp/");
//		}
		// リクエストパラメータの取得
//		request.setCharacterEncoding("UTF-8");
//		String reservationId = request.getParameter("reservationId");
//		String userId = request.getParameter("userId");		
//		String cinemaId = request.getParameter("cinemaId");
//		String movieid = request.getParameter("movieId");
//		String movieTime = request.getParameter("movieTime");
//		String ticketPrice = request.getParameter("ticketTanka");
//		String torokubi = request.getParameter("torokubi");
		
		// 
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/reserveCinema.jsp");
		dispatcher.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// テーブル追加
		
		// 予約完了画面にフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/reserveCinemaResult.jsp");
		dispatcher.forward(request, response);
		
	}

}
