<%@ page import="model.data.Reservation" %>
<%@ page import="model.data.User" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>

<% Reservation reservation = (Reservation) request.getAttribute("reservation");
   User user = (User) session.getAttribute("loginUser");  
   
// 日付と時間を分割
   String[] splitTime = reservation.getMovie_time().split(" ");
   String movie_date = splitTime[0];  // 日付
   String movie_hour = splitTime[1];  // 日時
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ムビコン_予約確認</title>
</head>
<body>
	<div class="container">
		<h1 class="page-title">予約確認</h1>
		
		<div class="form-container">
			<h2 style="color: #3498db;">以下の内容で予約します。</h2>
			<div class="bordered-box">
				<h3>劇場名</h3>
				<span><%= reservation.getCinema_name() %></span>
				<h3>作品名</h3>
				<span><%= reservation.getMovie_name() %></span>
				<h3>上映日</h3>
				<span><%= movie_date %></span>
				<h3>上映時間</h3>
				<span><%= movie_hour %></span>
				<% // 金額の表示を整える
				String price = String.format("%,d円", reservation.getTicket_price());
				%>
				<h3>金額</h3>
				<span><%= price %></span>
				<h3>予約者氏名</h3>
				<span><%= user.getName() %></span>
			</div>
			
			<div class="button-container">
				<!-- 検索画面に戻るボタンにパラメータを追加 -->
				<a class="btns" href="Main?movie_name=<%= reservation.getMovie_name() %>&cinema_id=<%= reservation.getCinema_id() %>">検索画面に戻る</a>	
				<form action="ReserveCinemaResult" method="post" style="flex: 1; margin: 0;">
					<input type="hidden" name="user_id" value=<%= reservation.getUser_id() %>>
					<input type="hidden" name="cinema_id" value=<%= reservation.getCinema_id() %>>
					<input type="hidden" name="movie_id" value=<%= reservation.getMovie_id() %>>
					<input type="hidden" name="movie_time" value="<%= reservation.getMovie_time() %>">
					<input type="hidden" name="ticket_price" value=<%= reservation.getTicket_price() %>>
					<button type="submit" class="btn" style="width: 100%;">予約確定</button>
				</form>
			</div>
		</div>
	</div>
</body>
</html>