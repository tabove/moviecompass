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
		
		RequestDispatcher dispartchar = 
				request.getRequestDispatcher("WEB-INF/jsp/login.jsp");	
		dispartchar.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		//入力情報の入手
		request.setCharacterEncoding("UTF-8");
		
		String mail = request.getParameter("mail");
		String pass = request.getParameter("pass");
				
		
		if (mail != null && mail.length() != 0 && pass != null && pass.length() != 0) {
			
			UserSearchLogic usl = new UserSearchLogic();
			User userR = usl.search(mail);
			
			HttpSession session = request.getSession();
			
			session.setAttribute("userR", userR);
			
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
