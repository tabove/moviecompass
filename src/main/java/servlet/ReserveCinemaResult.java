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
import model.logic.ReservationSearchLogic;

@WebServlet("/ReserveCinemaResult")
public class ReserveCinemaResult extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String errorMsg = ""; // 表示するエラーメッセージ
		
		// リクエストパラメータの取得
		request.setCharacterEncoding("UTF-8");
		String user_id = request.getParameter("user_id");
		String cinema_id = request.getParameter("cinema_id");
		String movie_id = request.getParameter("movie_id");
		String movie_time = request.getParameter("movie_time");
		String ticket_price = request.getParameter("ticket_price");
		
		// チケット単価を数値に変換する。
		int repTicket_price = Integer.parseInt(ticket_price); 
		
		
		// すでに登録されている時間か確認
		ReservationSearchLogic reservationSearchLogic = new ReservationSearchLogic();
		boolean isFound = reservationSearchLogic.searchDuplicate(user_id, cinema_id, movie_id, movie_time);
		
		if (isFound) { // すでに登録されている場合
			errorMsg = "エラーが発生しました。<br>"
					+ "既に予約済みです。";
		
		} else {	// まだ未登録の場合
			ReservationAddLogic reservationAddLogic = new ReservationAddLogic();
			boolean isAdd = reservationAddLogic.add(user_id, cinema_id, movie_id, movie_time, repTicket_price);
			
			
			if(isAdd == true) {
				errorMsg = "予約が完了しました！<br>"
						+ "ご来場を心よりお待ちしております。";
			} else {
				errorMsg = "エラーが発生しました。<br>"
						+ "再度予約をやり直してください。";
			}
		}
		
		// エラーメッセージをリクエストスコープに保存
		request.setAttribute("errorMsg", errorMsg);
		// 予約完了画面へフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/reserveCinemaResult.jsp");
		dispatcher.forward(request, response);
	}
}
