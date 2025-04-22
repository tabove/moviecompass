<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="model.data.SearchCondition, model.data.MovieSchedule" %>
<%@ page import="java.util.List,java.util.Map, java.util.Set, java.util.HashMap, java.util.HashSet, java.util.ArrayList" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>MovieCompass</title>
        <link rel="stylesheet" href="css/style2.css">
        <link rel="stylesheet" href="css/movie-ads.css">
    </head>
    <body>

        <form id="searchForm" action="Main" method="get">
            <table border="1">
                <tr>
                    <td>
                        映画館:
                        <select name="cinema_id">
                            <option value="">
                                <c:forEach var="theater" items="${theaterList}">
                                    <c:if test="${theater.cinema_id == param.cinema_id}">
                                        ${theater.cinema_name}
                                    </c:if>
                                </c:forEach>
                            </option>
                            <c:forEach var="theater" items="${theaterList}">
                                <option value="${theater.cinema_id}">${theater.cinema_name}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>作品名:
                        <input type="text" name="movie_name" placeholder="作品名を入力してください" value="${param.movie_name}">
                    </td>
                    <td>
                        <label for="genre">ジャンル:</label>
                        <select name="genre">
                            <option value="">${param.genre}</option>
                            <c:forEach var="genre" items="${genreList}">
                                <option value="${genre}">${genre}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td>日付
                        <input type="date" name="date" value="${param.date}">
                    </td>
                    <td>時間
                        <input type="time" name="dateTime" value="${param.dateTime}">
                    </td>
                </tr>
            </table>
            <input type="submit" value="検索">
        </form>

        <h2>検索結果</h2>
        <%
            List<MovieSchedule> searchResults = (List<MovieSchedule>) request.getAttribute("searchResults");

            if (searchResults != null && !searchResults.isEmpty()) {
        %>
        <div class="movie-list-view">
            <%
                Map<String, List<MovieSchedule>> schedulesByDate = new HashMap<>();

                for (MovieSchedule schedule : searchResults) {
                    if (!schedulesByDate.containsKey(schedule.getMovie_date())) {
                        schedulesByDate.put(schedule.getMovie_date(), new ArrayList<>());
                    }
                    schedulesByDate.get(schedule.getMovie_date()).add(schedule);
                }

                for (Map.Entry<String, List<MovieSchedule>> dateEntry : schedulesByDate.entrySet()) {
                    String date = dateEntry.getKey();
                    List<MovieSchedule> schedulesForDate = dateEntry.getValue();
            %>
            <div class="date-section">
                <h3 class="date-heading"><%= date %></h3>
                <%
                    Map<String, List<MovieSchedule>> schedulesByCinema = new HashMap<>();

                    for (MovieSchedule schedule : schedulesForDate) {
                        if (!schedulesByCinema.containsKey(schedule.getCinema_name())) {
                            schedulesByCinema.put(schedule.getCinema_name(), new ArrayList<>());
                        }
                        schedulesByCinema.get(schedule.getCinema_name()).add(schedule);
                    }

                    for (Map.Entry<String, List<MovieSchedule>> cinemaEntry : schedulesByCinema.entrySet()) {
                        String cinemaName = cinemaEntry.getKey();
                        List<MovieSchedule> schedulesForCinema = cinemaEntry.getValue();
                %>
                <div class="cinema-header">
                    <div class="cinema-name">
                        <a href="CinemaDetail?cinema_id=<%= schedulesForCinema.get(0).getCinema_id() %>">
                            <%= cinemaName %>
                        </a>
                    </div>
                </div>

                <%
                    Map<String, List<MovieSchedule>> schedulesByMovie = new HashMap<>();

                    for (MovieSchedule schedule : schedulesForCinema) {
                        if (!schedulesByMovie.containsKey(schedule.getMovie_name())) {
                            schedulesByMovie.put(schedule.getMovie_name(), new ArrayList<>());
                        }
                        schedulesByMovie.get(schedule.getMovie_name()).add(schedule);
                    }

                    for (Map.Entry<String, List<MovieSchedule>> movieEntry : schedulesByMovie.entrySet()) {
                        String movieName = movieEntry.getKey();
                        List<MovieSchedule> schedulesForMovie = movieEntry.getValue();

                        MovieSchedule firstSchedule = schedulesForMovie.get(0);
                %>
                <div class="cinema-section">
                    <div class="schedule-container">
                        <div class="movie-title-section">
                            <div class="movie-title">
                                <a href="MovieDetail?movie_id=<%= firstSchedule.getMovie_id() %>">
                                    <%= movieName %>
                                </a>
                            </div>
                        </div>

                        <% for (MovieSchedule schedule : schedulesForMovie) { %>
                        <div class="movie-row">
                            <div class="movie-time"><%= schedule.getMovie_hour() %></div>
                            <div class="movie-price"><%= schedule.getTicket_price() %>円</div>
                            <div class="movie-action">
                                <form action="Reservation" method="POST">
                                    <input type="hidden" name="cinema_id" value="<%= schedule.getCinema_id() %>">
                                    <input type="hidden" name="cinema_name" value="<%= schedule.getCinema_name() %>">
                                    <input type="hidden" name="movie_id" value="<%= schedule.getMovie_id() %>">
                                    <input type="hidden" name="movie_name" value="<%= schedule.getMovie_name() %>">
                                    <input type="hidden" name="ticket_price" value="<%= schedule.getTicket_price() %>">
                                    <input type="hidden" name="movie_time" value="<%= schedule.getMovie_time() %>">
                                    <input type="hidden" name="movie_date" value="<%= schedule.getMovie_date() %>">
                                    <input type="hidden" name="movie_hour" value="<%= schedule.getMovie_hour() %>">
                                    <button type="submit" class="reservation-btn">予約</button>
                                </form>
                            </div>
                        </div>
                        <% } %>
                    </div>
                    <%
				    // 広告データの配列を作成
				    List<Map<String, String>> promoAds = new ArrayList<>();
				    
				    Map<String, String> ad1 = new HashMap<>();
				    ad1.put("id", "thunderbolts");
				    ad1.put("title", "サンダーボルツ");
				    ad1.put("subtitle", "THUNDERBOLTS");
				    ad1.put("description", "マーベル・シネマティック・ユニバースの新たなチーム「サンダーボルツ」の活躍を描く作品。反英雄たちが集結し、政府の極秘任務に挑む。");
				    ad1.put("release", "2025年公開予定");
				    ad1.put("image", "/api/placeholder/300/450");
				    promoAds.add(ad1);
				    
				    Map<String, String> ad2 = new HashMap<>();
				    ad2.put("id", "underwater-odyssey");
				    ad2.put("title", "アンダーウォーター・オデッセイ");
				    ad2.put("subtitle", "UNDERWATER ODYSSEY");
				    ad2.put("description", "深海の謎に迫る冒険映画。未知の海底世界で繰り広げられる壮大な物語。");
				    ad2.put("release", "2025年7月公開");
				    ad2.put("image", "/api/placeholder/300/450");
				    promoAds.add(ad2);
				    
				    Map<String, String> ad3 = new HashMap<>();
				    ad3.put("id", "cybernetic");
				    ad3.put("title", "サイバネティック");
				    ad3.put("subtitle", "CYBERNETIC");
				    ad3.put("description", "近未来を舞台にしたSFアクション。人間と機械の境界が曖昧になった世界での戦い。");
				    ad3.put("release", "2025年秋公開");
				    ad3.put("image", "/api/placeholder/300/450");
				    promoAds.add(ad3);
				    
				    Map<String, String> ad4 = new HashMap<>();
				    ad4.put("id", "escape-sunrise");
				    ad4.put("title", "エスケープ・サンライズ");
				    ad4.put("subtitle", "ESCAPE SUNRISE");
				    ad4.put("description", "孤島に取り残された主人公たちが脱出を試みるサスペンス。予測不能な展開が待ち受ける。");
				    ad4.put("release", "2025年9月公開");
				    ad4.put("image", "/api/placeholder/300/450");
				    promoAds.add(ad4);
				    
				    request.setAttribute("promoAds", promoAds);
				%>
			<div class="promo-area" data-section-id="<%= firstSchedule.getMovie_id() %>">
    <%-- 広告エリアの内容はJavaScriptで動的に挿入 --%>
			</div>
            </div>
            <% } %>
            <% } %>
        </div>
        <% } %>
    </div>
    <% } else { %>
    <p>検索条件を入力してください</p>
    <% } %>
<script src="js/movie-ads.js"></script>
<script>
// JSから広告データを設定
window.movieApp.setPromoAds([
    <c:forEach var="ad" items="${promoAds}" varStatus="status">
    {
        id: "${ad.id}",
        title: "${ad.title}",
        subtitle: "${ad.subtitle}",
        description: "${ad.description}",
        release: "${ad.release}",
        image: "${ad.image}"
    }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
]);

// ログイン状態をJSに渡す
window.movieApp.setupReservation(<%= session.getAttribute("user_id") != null %>);
</script>
</body>
</html>
