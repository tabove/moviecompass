package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.data.User;
import model.logic.UserSearchLogic;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		// セッションスコープのログインユーザを取得する
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("loginUser");
		
		// ログイン状態でない場合に、ログイン画面へフォワード
		if (user == null) {
			RequestDispatcher dispartchar = 
					request.getRequestDispatcher("WEB-INF/jsp/login.jsp");	
			dispartchar.forward(request, response);
		
		// ログイン状態の場合はマイページへリダイレクト
		} else {
			response.sendRedirect("Mypage");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//入力情報の入手
		request.setCharacterEncoding("UTF-8");
		
		String mail = request.getParameter("mail");
		String pass = request.getParameter("pass");
				
		
		if (mail != null && mail.length() != 0 && pass != null && pass.length() != 0) {
			
			UserSearchLogic usl = new UserSearchLogic();
			User userR = usl.search(mail, pass);
			
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", userR);
		
			//フォワード
			RequestDispatcher dispartchar = 
					request.getRequestDispatcher("WEB-INF/jsp/loginResult.jsp");	
			dispartchar.forward(request, response);
			
		} else {
		
			request.setAttribute("errorSign", "※ログインID・パスワードは入力必須です");
		
			//フォワード
			RequestDispatcher dispartchar = 
					request.getRequestDispatcher("WEB-INF/jsp/login.jsp");	
			dispartchar.forward(request, response);
		
		}	
	}
}
