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
		    <!-- 作品名検索 -->
		    <div class="search-movie-name">
		        <span>作品名:</span>
		        <input type="text" name="movie_name" placeholder="作品名を入力してください" value="${param.movie_name}">
		    </div>
		    
		    <!-- その他の検索条件 -->
		    <div class="search-filters">
		        <div class="filter-item">
		            <span>映画館:</span>
		            <select name="cinema_id">
		                <option value=""></option>
		                <c:forEach var="theater" items="${theaterList}">
		                    <option value="${theater.cinema_id}" ${param.cinema_id == theater.cinema_id ? 'selected' : ''}>${theater.cinema_name}</option>
		                </c:forEach>
		            </select>
		        </div>
		        <div class="filter-item">
		            <label for="genre">ジャンル:</label>
		            <select name="genre">
		                <option value=""></option>
		                <c:forEach var="genre" items="${genreList}">
		                    <option value="${genre}" ${param.genre == genre ? 'selected' : ''}>${genre}</option>
		                </c:forEach>
		            </select>
		        </div>
		        <div class="filter-item">
		            <span>日付:</span>
		            <input type="date" name="date" value="${param.date}">
		        </div>
		        <div class="filter-item">
		            <span>時間:</span>
		            <input type="time" name="dateTime" value="${param.dateTime}">
		        </div>
		    </div>
		    
		    <!-- ボタン -->
		    <div class="search-buttons">
		        <input type="submit" value="検索">
		        <button type="button" class="reset-btn" onclick="resetForm()">リセット</button>
		    </div>
		</form>
        <h2>検索結果</h2>
        <c:choose>
        <c:when test="${empty param.cinema_id && empty param.movie_name && empty param.genre && empty param.date && empty param.dateTime}">        	
        	<p>検索したい項目を入力してください</p>
        </c:when>
        <c:otherwise>
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
                                <form action="ReserveCinema" method="POST">
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
				    ad1.put("pr","オーナーKイチオシ！");
				    ad1.put("id", "conan");
				    ad1.put("title", "名探偵コナン・隻眼の残像");
				    ad1.put("subtitle", "DETECTIVE CONAN");
				    ad1.put("description", "大人気アニメ「名探偵コナン」劇場版第28作！！氷雪吹き荒れる山岳で、白き闇の因縁(ホワイトアウトミステリー)の幕が切って落とされる。");
				    ad1.put("release", "大ヒット上映中！！");
				    ad1.put("image", "images/ads/conan.jpg");
				    ad1.put("link", "https://www.conan-movie.jp/2025/");
				    promoAds.add(ad1);
				    
				    Map<String, String> ad2 = new HashMap<>();
				    ad2.put("pr", "ビッグボスDのこれを見ろ！！");
				    ad2.put("title", "劇場版TOKYO MER 走る緊急救命室　南海ミッション");
				    ad2.put("subtitle", "TOKYO MER");
				    ad2.put("description", "大ヒットドラマ「TOKYO MER」の劇場版第二弾。火山の大噴火で島に取り残された島民79名の救助に挑む");
				    ad2.put("release", "2025年8月公開");
				    ad2.put("image", "images/ads/tokyo_mer.jpg");
				    ad2.put("link", "https://tokyomer-movie.jp/");
				    promoAds.add(ad2);
				    
				    
				    Map<String, String> ad3 = new HashMap<>();
				    ad3.put("pr","ファンタジスタN 待望の最新作");
				    ad3.put("id", "kimetsu");
				    ad3.put("title", "鬼滅の刃・無限城編 第一章");
				    ad3.put("subtitle", "KIMETSU NO YAIBA");
				    ad3.put("description", "週間少年ジャンプでメガヒットした「鬼滅の刃」。最終章となる無限城編、第一章が公開予定。");
				    ad3.put("release", "2025年7月公開");
				    ad3.put("image", "images/ads/kimetsu.jpg");
				    ad3.put("link", "https://kimetsu.com/anime/mugenjyohen_movie/");
				    promoAds.add(ad3);
				    
				    Map<String, String> ad4 = new HashMap<>();
				    ad4.put("pr", "fanboy Hおすすめ!!");
				    ad4.put("id", "thunderbolts");
				    ad4.put("title", "サンダーボルツ");
				    ad4.put("subtitle", "THUNDERBOLTS");
				    ad4.put("description", "マーベル・シネマティック・ユニバースの新たなチーム「サンダーボルツ」の活躍を描く作品。反英雄たちが集結し、政府の極秘任務に挑む。");
				    ad4.put("release", "2025年公開予定");
				    ad4.put("image", "images/ads/thunderbolts.jpg");
				    ad4.put("link", "https://marvel.disney.co.jp/movie/thunderbolts");
				    promoAds.add(ad4);
				    
				    Map<String, String> ad5 = new HashMap<>();
				    ad5.put("pr", "スペシャリストU推薦！!普及の名作をもう一度");
				    ad5.put("title", " 英国ロイヤル・バレエ＆オペラ in シネマ 2024/25 ロイヤル・バレエ「ロミオとジュリエット」");
				    ad5.put("subtitle", "ROMEO AND JULLET");
				    ad5.put("description", "今、なお色褪せることのない名作を世界最高峰クラスのバレエとオペラで表現。映画館の大スクリーンと迫力ある音響で観劇とは違う臨場感を味わうことができます。");
				    ad5.put("release", "2025年6月公開予定");
				    ad5.put("image", "images/ads/romiojullet.jpg");
				    ad5.put("link", "https://tohotowa.co.jp/roh/");
				    promoAds.add(ad5);

				    
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
    <% } else{ %>
    <p>表示出来る結果がありませんでした。</p>
    <% } %>
	</c:otherwise>
</c:choose>
    
<script src="js/movie-ads.js"></script>
<script >

function resetForm() {
    // セレクトボックスの値をリセット
    document.querySelectorAll('#searchForm select').forEach(select => {
        select.selectedIndex = 0;
    });
    
    // テキスト入力、日付、時間入力をリセット
    document.querySelectorAll('#searchForm input[type="text"], #searchForm input[type="date"], #searchForm input[type="time"]').forEach(input => {
        input.value = '';
    });
}

//JSから広告データを設定
window.movieApp.setPromoAds([
    <c:forEach var="ad" items="${promoAds}" varStatus="status">
    {
        pr: "${ad.pr}",
        id: "${ad.id}",
        title: "${ad.title}",
        subtitle: "${ad.subtitle}",
        description: "${ad.description}",
        release: "${ad.release}",
        image: "${ad.image}",
        link: "${ad.link}"
    }<c:if test="${!status.last}">,</c:if>
    </c:forEach>
]);

//ログイン状態をJSに渡す
window.movieApp.setupReservation(${not empty loginUser});
</script>
</body>
</html>