package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import DAO.MovieScheduleDAO;
import model.SearchCondition;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//パラメータの取得
		String theater = request.getParameter("theater");
		String title = request.getParameter("title");
		String genre = request.getParameter("genre");
		String date = request.getParameter("date");
		String dateTime = request.getParameter("dateTime");
		 List<SearchCondition> results = new ArrayList<>();
		
		//パラメータが空の場合に、セッションから取得
		HttpSession session = request.getSession();
		 if (title != null && !title.isEmpty()) {
			 //DAOに検索依頼
            MovieScheduleDAO dao = new MovieScheduleDAO();
            results = dao.searchTitle(title);
    	}
        
        //nullチェック
        if(theater == null)theater = "";
        if(title == null)title = "";
        
        //検索条件をセッションに保存
        session.setAttribute("selectedTheater", theater);
        session.setAttribute("searchTitle", title);
        
        
        //検索結果をリクエストスコープへ保存
        request.setAttribute("searchResults", results);
        
     // 結果ページへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
        dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
