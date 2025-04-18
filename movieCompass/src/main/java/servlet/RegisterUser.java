package servlet;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.data.UserAddLogic;


@WebServlet("/RegisterUser")
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// getでJSPを呼ぶ
	//　入力値チェック　
	// insert文を書く
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// jspを呼ぶ...
		
		// フォワード
		RequestDispatcher dispatcher =
		request.getRequestDispatcher("WEB-INF/jsp/registerUser.jsp");
		dispatcher.forward(request, response);
	}
	
	
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// リクエストパラメタを取得
		request.setCharacterEncoding("UTF-8");
		String user_mail = request.getParameter("mail"); 
		String user_pass = request.getParameter("pass"); 
		String user_name = request.getParameter("name"); 

		// 入力値のチェック

		String message = "" ;	// レスポンス後に画面に表示する結果メッセージ

		String errMessage = "";
		
		// 必須項目チェック
		if (user_mail != null && "" .equals(user_mail)){
			errMessage += "メールアドレスは必須項目です。<br>";
		}
		
		if (user_pass != null && "".equals(user_pass)) {
			errMessage += "パスワードは必須項目です。<br>";
		}
		
		if (user_name != null && "" .equals(user_name)) {
			errMessage += "ユーザ名は必須項目です。<br>";
		}
		
	
		// 文字数チェック
		if (user_mail.length() > 50) {
			errMessage += "アドレスは50文字以下で入力してください。<br>";
		}
		
		// 文字数チェック
		if (user_pass.length() > 20) {
			errMessage += "パスワードは20文字以下で入力してください。<br>";
		}
		
		// 文字数チェック
		if (user_name.length() > 20) {
			errMessage += "ユーザ名は20文字以下で入力してください。<br>";
		}
//		
	

		// 入力値チェックに問題がなければ商品追加処理を行う
		if ("".equals(errMessage)) {
			// 商品テーブルへ追加処理を行う
			UserAddLogic logic = new UserAddLogic();
			boolean result = logic.add( user_mail, user_pass, user_name);
			
			// 結果メッセージを設定
			if (result) {
				message = "登録が完了しました";
			} else {
				message = "登録処理時に問題が発生しました";
			}
		} else {
			// 入力値チェックに問題がある場合は、メッセージにエラーメッセージを設定
			message = errMessage;
		}

		// 結果messageをリクエストスコープに保存
		request.setAttribute("message", message);

		// 商品追加画面（自画面）へフォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/registerUser.jsp");
		dispatcher.forward(request, response);
	  

	
	
	}	
}

