# moviecompass

予約ページへの遷移

1．クエリパラメータの利用
→URLで直接アクセス可能。
拡張for文で全て抽出する際に、aタグのリンクの中に全部入れてしまい、そこを押すとその情報を予約ページに渡す。
例：
jspのaタグ
<a href="/ReservationPage?cinemaName=<%= sc.getCinemaName() %>&titleName=<%= sc.getTitleName() %>&movieTime=<%= sc.getMovieTime() %>&ticketPrice=<%= sc.getTicketPrice() %>">予約ページへ</a>

servlet
@WebServlet("/ReservationPage")
public class ReservationServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cinemaName = request.getParameter("cinemaName");
        String titleName = request.getParameter("titleName");
        String movieTime = request.getParameter("movieTime");
        String ticketPrice = request.getParameter("ticketPrice");

        // データをリクエストスコープに保存
        request.setAttribute("cinemaName", cinemaName);
        request.setAttribute("titleName", titleName);
        request.setAttribute("movieTime", movieTime);
        request.setAttribute("ticketPrice", ticketPrice);

        // JSPへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/reservation.jsp");
        dispatcher.forward(request, response);
    }
}

デメリット：URLがとんでもないことになる。気にならない？

2．POSTリクエスト
→セキュリティ面はすごい良い。

ただ、それをどのように渡すのか？
予約ページへのアクセスをinputで渡すにしても、どこまで保持するのか。
可視化しにくい気がする

今の形に近い
例：jsp
<form action="/reservation" method="POST">
    <input type="hidden" name="cinemaName" value="<%= sc.getCinemaName() %>">
    <input type="hidden" name="titleName" value="<%= sc.getTitleName() %>">
    <input type="hidden" name="movieTime" value="<%= sc.getMovieTime() %>">
    <input type="hidden" name="ticketPrice" value="<%= sc.getTicketPrice() %>">
    <button type="submit">予約ページへ</button>
</form>

servlet
@WebServlet("/reservation")
public class ReservationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cinemaName = request.getParameter("cinemaName");
        String titleName = request.getParameter("titleName");
        String movieTime = request.getParameter("movieTime");
        String ticketPrice = request.getParameter("ticketPrice");

        // データをリクエストスコープに保存
        request.setAttribute("cinemaName", cinemaName);
        request.setAttribute("titleName", titleName);
        request.setAttribute("movieTime", movieTime);
        request.setAttribute("ticketPrice", ticketPrice);

        // JSPへフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/reservation.jsp");
        dispatcher.forward(request, response);
    }
}

3．セッションリクエスト
複数ページの保持をするなら、これかなーと。
ぱらめーたを一々とって渡してが無くなるからコードの簡略化できそう？要らない？

サーバ重くなる。セッションタイムアウトの可能性増える

Session.setAttribute...
