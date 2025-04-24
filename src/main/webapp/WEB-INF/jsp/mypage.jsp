<%@page import="model.data.Reservation"%>
<%@page import="java.util.List"%>
<%@page import="model.data.FavoriteMovie"%>
<%@page import="model.data.FavoriteCinema"%>
<%@page import="model.data.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    
<%
User user = (User)session.getAttribute("loginUser");
List<FavoriteCinema> favoriteCinemaList = (List<FavoriteCinema>)request.getAttribute("favoriteCinemaList");
List<FavoriteMovie> favoriteMovieList = (List<FavoriteMovie>)request.getAttribute("favoriteMovieList");
List<Reservation> reservationList = (List<Reservation>)request.getAttribute("reservationList");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= user.getName() %>さんのマイページ</title>
</head>

<body>
<div class="container">
    <h1 class="page-title">マイページ</h1>
    
    <p>ようこそ、<%= user.getName() %>さん</p>
    
    <div class="detail-container">
        <div class="detail-main">
            <h2>お気に入り映画館</h2>
            <div class="bordered-box">
                <div class="item-list">
                <% if (favoriteCinemaList != null && !favoriteCinemaList.isEmpty()) { %>
                    <% for (FavoriteCinema fc : favoriteCinemaList) { %>
                        <a href="CinemaDetail?cinema_id=<%= fc.getCinema_id() %>"><%= fc.getCinema_name() %></a>
                    <% } %>
                <% } else { %>
                    <p>お気に入りの映画館はありません。</p>
                <% } %>
                </div>
            </div>
            
            <h2>お気に入り作品</h2>
            <div class="bordered-box">
                <div class="item-list">
                <% if (favoriteMovieList != null && !favoriteMovieList.isEmpty()) { %>
                    <% for (FavoriteMovie fm : favoriteMovieList) { %>
                        <a href="MovieDetail?movie_id=<%= fm.getMovie_id() %>"><%= fm.getMovie_name() %></a>
                    <% } %>
                <% } else { %>
                    <p>お気に入りの作品はありません。</p>
                <% } %>
                </div>
            </div>
            
            <h2>予約中の作品</h2>
            <div class="bordered-box">
                <% if (reservationList != null && !reservationList.isEmpty()) { %>
                    <% for (Reservation r : reservationList) { %>
                        <div class="reservation-item">
                            <div class="reservation-image">
                                <img src="images/movie/<%= r.getMovie_id() %>.jpg" alt="ポスター"/>
                            </div>
                            <div class="reservation-content">
                                映画館: <a href="CinemaDetail?cinema_id=<%= r.getCinema_id() %>"><%= r.getCinema_name() %></a><br>
                                作品: <a href="MovieDetail?movie_id=<%= r.getMovie_id() %>"><%= r.getMovie_name() %></a><br>
                                <% // 上映日時の表示を整える
                                String[] str = r.getMovie_time().split("[-/ :]");
                                String time = String.format("%s年%2s月%2s日 %2s時%2s分", str[0], str[1], str[2], str[3], str[4]);
                                %>
                                上映日時: <%= time %><br>
                                <% // チケット単価の表示を整える
                                String price = String.format("%,d円", r.getTicket_price());
                                %>
                                価格: <%= price %><br>
                            </div>
                        </div>
                    <% } %>
                <% } else { %>
                    <p>予約中の作品はありません。</p>
                <% } %>
            </div>
        </div>
        
        <div class="detail-sidebar">
            <h3>アカウント情報</h3>
            <div class="bordered-box">
                <p><strong>名前:</strong> <%= user.getName() %></p>
                <p><strong>メールアドレス:</strong> <%= user.getMail() %></p>
            </div>
            
            <h3>クイックリンク</h3>
            <div class="bordered-box">
                <p><a href="Main" class="action-link">映画館・上映作品を探す</a></p>
                <!-- 将来実装予定のリンク -->
                <!-- <p><a href="#" class="action-link">プロフィール編集</a></p> -->
                <!-- <p><a href="#" class="action-link">パスワード変更</a></p> -->
            </div>
        </div>
      </div>
	</div>
</body>
</html>