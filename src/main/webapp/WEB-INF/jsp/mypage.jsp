<%@page import="model.data.Reservation"%>
<%@page import="java.util.List"%>
<%@page import="model.data.FavoriteMovie"%>
<%@page import="model.data.FavoriteCinema"%>
<%@page import="model.data.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%-- user モデルの確認 --%>
<%-- 検索画面への検索条件の渡し方 --%>
<%
User user = (User)session.getAttribute("loginUser");
List<FavoriteCinema> favoriteCinemaList = (List<FavoriteCinema>)request.getAttribute("favoriteCinemaList");
List<FavoriteMovie> favoriteMovieList = (List<FavoriteMovie>)request.getAttribute("favoriteMovieList");
List<Reservation> reservationList = (List<Reservation>)request.getAttribute("reservationList");

String borderWidth = "500px";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%= user.getName() %>さんのマイページ</title>
</head>

<style>
.right {
width: 95%;
display: inline-block;
text-align: right; 
}
</style>

<body>
<h1>マイページ</h1>
<div style="display:inline">
ようこそ、<%= user.getName() %>さん
<div class="right"><a href="Main">映画館・上映作品を探す</a></div>
</div>
<br>
<div><table>
<tr>
<td valign="top">お気に入り映画館</td>
<td><div style = "border: solid 3px #000; width:<%= borderWidth %>; display: inline-block; _display: inline;">
<% if (favoriteCinemaList != null) { %>
	<% for (FavoriteCinema fc : favoriteCinemaList) { %>
		<a href="CinemaDetail?cinema_id=<%= fc.getCinema_id() %>"><%= fc.getCinema_name() %></a><br>
	<% } %>
<% } %>
</div></td>
</tr>
<tr>
<td valign="top">お気に入り作品</td>
<td><div style = "border: solid 3px #000; width:<%= borderWidth %>; display: inline-block; _display: inline;">
<% if (favoriteMovieList != null) { %>
	<% for (FavoriteMovie fm : favoriteMovieList) { %>
		<a href="MovieDetail?movie_id=<%= fm.getMovie_id() %>"><%= fm.getMovie_name() %></a><br>
	<% } %>
<% } %>
</div></td>
</tr>
<tr>
<td valign="top">予約中の作品</td>
<td><div style = "border: solid 3px #000; width:<%= borderWidth %>; display: inline-block; _display: inline;">
<% if (reservationList != null) { %>
	<% for (Reservation r : reservationList) { %>
		<a href="CinemaDetail?cinema_id=<%= r.getCinema_id() %>"><%= r.getCinema_name() %></a>、
		<a href="MovieDetail?movie_id=<%= r.getMovie_id() %>"><%= r.getMovie_name() %></a>、
			<% // 上映日時の表示を整える
			String[] str = r.getMovie_time().split("[-/ :]");
			String time = String.format("%s年%2s月%2s日 %2s時%2s日", str[0], str[1], str[2], str[3], str[4]);
			%>
		<%= time %>、
			<% // チケット単価の表示を整える
			String price = String.format("%,d円", r.getTicket_price());
			%>
		<%= price %>、
			<% // 予約日の表示を整える
			String reservationDate = r.getTorokubi().replaceFirst("[-/]", "年").replaceFirst("[-/]", "月") + "日";
			%>
		予約日：<%= reservationDate %><br>
	<% } %>
<% } %>
</div></td>
</tr>
</table></div>

</body>
</html>