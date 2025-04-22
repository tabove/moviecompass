<%@page import="model.data.FavoriteMovie"%>
<%@page import="model.data.Movie"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    
<%-- 検索画面への検索条件の渡し方 --%>
<%
Movie movie = (Movie)request.getAttribute("movie");
FavoriteMovie favoriteMovie = (FavoriteMovie)request.getAttribute("favoriteMovie");
String msg = (String)request.getAttribute("msg");

String favoriteAction = "";
String heart = "";

if (favoriteMovie == null){
	favoriteAction = "?action=register&movie_id=" + movie.getId();
	heart = "♡";
} else {
	favoriteAction = "?action=delete&movie_id=" + movie.getId();
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
<title><%= movie.getName() %></title>
</head>

<body>
<div class="container">
    <div class="detail-container">
        <div class="detail-main">
            <div style="display: flex;">
                <div class="detail-image">
                    <img src="images/movie/<%= movie.getId() %>.jpg" width="200" alt="ポスター"/>
                </div>
                <div class="detail-content">
                    <h1 class="detail-title"><%= movie.getName() %></h1>
                    <div class="detail-info">
                        <p>上映開始日：<%= movie.getRelease_date() %></p>
                        <p>上映時間：<%= movie.getDuration() %>分</p>
                        <p>ジャンル：<%= movie.getGenre() %></p>
                    </div>
                    
                    <% if (msg != null && !msg.equals("<br>")) { %>
                        <div class="error-message"><%= msg %></div>
                    <% } %>
                    
                    <form action="MovieDetail<%= favoriteAction %>" method="post">
                        <button class="favorite-button"><%= heart %> お気に入り</button>
                    </form>
                </div>
            </div>
            
            <div class="detail-description">
                <h3>概要</h3>
                <p><%= movie.getDescription() %></p>
            </div>
        </div>
        
        <div class="detail-sidebar">
            <h3>この作品について</h3>
            <p>この作品を上映している映画館を検索できます。</p>
            <a href="Main?movie_id=<%= movie.getId() %>" class="btn">この作品で検索</a>
            
            <hr>
            
            <h3>おすすめの作品</h3>
            <div class="item-list">
                <!-- ここにおすすめの作品を表示できます（将来的に実装） -->
                <p>準備中...</p>
            </div>
            
            <a href="Main" class="action-link">映画館・上映作品を探す</a>
        </div>
    </div>
</div>
</body>
</html>