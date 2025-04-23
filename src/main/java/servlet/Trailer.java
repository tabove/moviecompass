package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Trailer
 */
@WebServlet("/Trailer")
public class Trailer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// クッキーの有効期限（7日間）
//    private static final int COOKIE_MAX_AGE = 60 * 60 * 24 * 7;
    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 初回訪問かどうかをクッキーで判断
        boolean hasVisited = false;
        Cookie[] cookies = request.getCookies();
        
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("hasVisitedSite".equals(cookie.getName())) {
                    hasVisited = true;
                    break;
                }
            }
        }
        
        if (!hasVisited) {
            // 初回訪問の場合、クッキーをセット
            Cookie visitedCookie = new Cookie("hasVisitedSite", "true");
//            visitedCookie.setMaxAge(COOKIE_MAX_AGE);
            visitedCookie.setPath("/");
            response.addCookie(visitedCookie);
            
            // ランダムに予告編を選択（0か1）
            int trailerChoice = (int)(Math.random() * 2);
            request.setAttribute("showTrailer", true);
            request.setAttribute("trailerChoice", trailerChoice);
        } else {
            request.setAttribute("showTrailer", false);
        }
        
        // indexページにフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/index.jsp");
        dispatcher.forward(request, response);
    }
}
