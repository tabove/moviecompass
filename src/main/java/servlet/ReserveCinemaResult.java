package servlet;
// 予約内容をDBに追加する処理
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.logic.ReservationAddLogic;

@WebServlet("/ReserveCinemaResult")
public class ReserveCinemaResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
//		String reservation_id = request.getParameter("reservation_id");
		String user_id = request.getParameter("user_id");
		String cinema_id = request.getParameter("cinema_id");
		String movie_id = request.getParameter("movie_id");
//		String cinema_name =request.getParameter("cinema_name");
//		String movie_name = request.getParameter("movie_name");
		String movie_time = request.getParameter("movie_time");
//		String movie_date = request.getParameter("movie_date");
//		String movie_hour = request.getParameter("movie_hour");
		String ticket_price = request.getParameter("ticket_price");
//		String torokubi = request.getParameter("torokubi");
		
		// チケット単価を数値に変換する。
		int repTicket_price = Integer.parseInt(ticket_price); 
		
		ReservationAddLogic reservationAddLogic = new ReservationAddLogic();
		boolean isAdd = reservationAddLogic.add(user_id, cinema_id, movie_id, movie_time, repTicket_price);
		
		String errorMsg = "";
		
		if(isAdd == true) {
			errorMsg = "予約が完了しました！<br>"
					+ "ご来場を心よりお待ちしております。";
		} else {
			errorMsg = "エラーが発生しました。<br>"
					+ "再度予約をやり直してください。";
		}
		// エラーメッセージをリクエストスコープに保存
		request.setAttribute("errorMsg", errorMsg);
		// 予約完了画面へフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/reserveCinemaResult.jsp");
		dispatcher.forward(request, response);
	}
}
