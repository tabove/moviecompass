package servlet;
// 予約内容を表示する処理
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.data.Reservation;
import model.data.User;

@WebServlet("/ReserveCinema")
public class ReserveCinema extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// リクエストパラメータの取得
		String cinema_id = request.getParameter("cinema_id");
		String movie_id = request.getParameter("movie_id");
		String cinema_name =request.getParameter("cinema_name");
		String movie_name = request.getParameter("movie_name");
		String movie_time = request.getParameter("movie_time");
		String ticket_price = request.getParameter("ticket_price");
		
		// チケット単価を数値に変換する。
		int repTicket_price = Integer.parseInt(ticket_price);
		// セッションスコープに保存されたユーザIDを取得
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		String user_id = user.getId();
		
		// reservationインスタンスを準備
		Reservation reservation = new Reservation(user_id, cinema_id, movie_id,
				cinema_name, movie_name, movie_time, repTicket_price);
		request.setAttribute("reservation", reservation);
		
		// 予約確認画面へフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/reserveCinema.jsp");
		dispatcher.forward(request, response);
	}
}