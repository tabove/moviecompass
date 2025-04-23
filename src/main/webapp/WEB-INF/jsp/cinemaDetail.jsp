<%@page import="model.data.FavoriteCinema"%>
<%@page import="model.data.Cinema"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    
<%-- 検索画面への検索条件の渡し方 
	 ボタンに画像を使用--%>
<%
Cinema cinema = (Cinema)request.getAttribute("cinema");
FavoriteCinema favoriteCinema = (FavoriteCinema)request.getAttribute("favoriteCinema");
String msg = (String)request.getAttribute("msg");

String favoriteAction = "";
String heart = "";

if (favoriteCinema == null){
	favoriteAction = "?action=register&cinema_id=" + cinema.getId();
	heart = "♡";
} else {
	favoriteAction = "?action=delete&cinema_id=" + cinema.getId();
	heart = "♥";
}

if (msg == null){
	msg = "<br>";
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= cinema.getName() %></title>
</head>

<body>
<div class="container">
    <div class="detail-container">
        <div class="detail-main">
            <h1 class="detail-title"><%= cinema.getName() %></h1>
            <% if (msg != null && !msg.equals("<br>")) { %>
                <div class="error-message"><%= msg %></div>
            <% } %>
            
            <div class="detail-info">
                <p>所在地：<%= cinema.getAddress() %></p>
                <p>電話番号：<%= cinema.getPhone_number() %></p>
            </div>
            
            <div class="detail-image">
                <img src="images/cinema/<%= cinema.getId() %>.jpg" alt="所在地"/>
            </div>
            
            <!-- お気に入りボタン -->
            <form action="CinemaDetail<%= favoriteAction %>" method="post">
                <button class="favorite-button"><%= heart %> お気に入り</button>
            </form>
        </div>
        
        <div class="detail-sidebar">
            <h3>この映画館について</h3>
            <p>この映画館で上映中の作品を検索できます。</p>
            <a href="Main?cinema_id=<%= cinema.getId() %>" class="btn">この映画館で検索</a>
            
            <hr>
            
            <h3>おすすめの映画館</h3>
            <div class="item-list">
                <!-- ここにおすすめの映画館を表示できます（将来的に実装） -->
                <p>準備中...</p>
            </div>
            
            <a href="Main" class="action-link">映画館・上映作品を探す</a>
        </div>
    </div>
</div>
</body>
</html>