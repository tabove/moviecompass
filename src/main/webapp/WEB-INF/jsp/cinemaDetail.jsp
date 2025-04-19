<%@page import="model.data.FavoriteCinema"%>
<%@page import="model.data.Cinema"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
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

<style>
.right {
width: 95%;
display: inline-block;
text-align: right; 
}
</style>

<body>
<div style="display:flex; width:95%">
<div><h1 style="width:500px"><%= cinema.getName() %></h1></div>
<div class="right" style="width: 100%;">
<div style="color:red"><%= msg %></div>
<form action ="CinemaDetail<%= favoriteAction %>" method="post">
<button><%= heart %>お気に入り</button></form>
<a href="Main">この映画館で検索</a></div>
</div>
<div>所在地：<%= cinema.getAddress() %></div>
<div>電話番号：<%= cinema.getPhone_number() %></div>
<div><img src="images/cinema/<%= cinema.getId() %>.jpg" width="300" alt="所在地"/></div>
<div class="right"><a href="Main">映画館・上映作品を探す</a></div>
</body>
</html>