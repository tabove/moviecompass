<%@page import="model.data.FavoriteMovie"%>
<%@page import="model.data.Movie"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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

<style>
.right {
width: 95%;
display: inline-block;
text-align: right; 
}
</style>

<body>
<div style="display:flex">
<div style="margin-right:10px"><img src="images/movie/<%= movie.getId() %>.jpg" width="200" alt="ポスター" align="left"/></div>
<div style="display:inline"><h1><%= movie.getName() %></h1>
<p style="vertical-align: bottom">上映開始日：<%= movie.getRelease_date() %><br>
上映時間：<%= movie.getDuration() %>分<br>
ジャンル：<%= movie.getGenre() %></p></div>

<div class="right">
<div style="color:red"><%= msg %></div>
<form action ="MovieDetail<%= favoriteAction %>" method="post">
<button style="height:2em; width:8em"><%= heart %>お気に入り</button></form>
<a href="Main">この作品で検索</a></div>
</div>




<br clear="left">
<div>概要：<%= movie.getDescription() %></div>

<div class="right"><a href="Main">映画館・上映作品を探す</a></div>
</body>
</html>