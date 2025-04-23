package servlet;


import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.logic.UserAddLogic;
import model.logic.UserSearchLogic;


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
		
		// 必須項目チェックと文字数チェック
		if ("".equals(user_mail)){
			errMessage += "メールアドレスは必須項目です。<br>";
		} else if (user_mail.length() > 50) {
			errMessage += "メールアドレスは50文字以下で入力してください。<br>";
		}
		
		if ("".equals(user_pass)) {
			errMessage += "パスワードは必須項目です。<br>";
		} else if (user_pass.length() < 8 || user_pass.length() > 20) {
			errMessage += "パスワードは8文字以上、20文字以下で入力してください。<br>";
		}
		
		if ("".equals(user_name)) {
			errMessage += "ユーザ名は必須項目です。<br>";
		} else if (user_name.length() > 20) {
			errMessage += "ユーザ名は20文字以下で入力してください。<br>";
		}
		
		// 必須項目チェックと文字数チェックで問題がない場合
		if ("".equals(errMessage)) {
			// パスワードに使用可能な半角英数字、記号かのチェック
			if (!user_pass.matches("[\\w\\p{Punct}]*")) {
				errMessage += "パスワードは半角英数字、記号のみ使用可能です。";
				
			} else {
				// 全ての入力値に問題がない場合に、メールアドレスの重複チェック
				UserSearchLogic searchLogic = new UserSearchLogic();
				boolean isFound = searchLogic.search(user_mail);
				if (isFound) {
					errMessage += "既に登録されているメールアドレスです。";
				}
			}
		}
	

		// 入力値チェックに問題がなければユーザ追加処理を行う
		if ("".equals(errMessage)) {
			// ユーザテーブルへ追加処理を行う
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
			message = "登録が完了しませんでした。<br>" + errMessage;
		}

		// 結果messageをリクエストスコープに保存
		request.setAttribute("message", message);

		// フォワード
		RequestDispatcher dispatcher =
				request.getRequestDispatcher("WEB-INF/jsp/registerUser.jsp");
		dispatcher.forward(request, response);
	}	
}

