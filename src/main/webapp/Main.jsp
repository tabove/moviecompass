<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="model.SearchCondition"  %>
<jsp:useBean id="searchCondition" class="java.util.ArrayList" scope="request"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" href="css/header.css">
<title>MovieCompass</title>
</head>
<body>
	<header>
		<div>
			<h1><a href="index.jsp">Movie Compass</a></h1>
		</div>
		<nav>
			<a>ログイン</a>
			<a>ユーザ登録</a>
		</nav>
	</header>
	
	<div class="thunder">
		
	</div>
	<div class="avengers">
		
	</div>
	<form id="searchForm" action="Result" method="get">
	<table border="1">
		<tr>
			<td>
			映画館:<select name="theater">
					<option value="" ></option>
					<option value="キャナル">ユナイテッド・シネマ キャナルシティ13</option>
					<option value="ソラリア">TOHOシネマズ 天神・ソラリア館</option>
					<option value="キノシネマ">kino cinema 天神</option>
					<option value="KBCシネマ">KBCシネマ</option>
					<option value="博多">T・ジョイ 博多</option>
					<option value="ももち">ユナイテッド・シネマ 福岡ももち</option>
					<option value="ららぽ">TOHOシネマズ ららぽーと福岡</option>
				</select>
			</td>
			<td>作品名:<input type="text" name="title"></td>
			<td>
				ジャンル:<select name="genre">
					<option value=""></option>
					<option value="">恋愛</option>
					<option value="">デスゲーム</option>
				</select>
			</td>
			<td><input type="date" name="date">日付</td>
			<td><input type="time" name="dateTime">時間</td>
		</tr>
	</table>	
	<input type="submit" value="検索">
	</form>
	<h2>検索結果</h2>
	<form method="post">
	<table border="1">
		<tbody>
			<tr>
				<th>日付</th>
				<th>映画館</th>
				<th>作品名</th>
				<th>上映時間</th>
				<th>料金</th>
				<th><a href="dammy.html">予約</a></th>
			</tr>
 			<% for (SearchCondition sc : (List<SearchCondition>) searchCondition) { %>
			<tr>
                <td><input type="radio" name="selectedMovie" value="<%= sc.getCinemaName() %>"></td>
                <td><%= sc.getCinemaName() %></td>
                <td><%= sc.getTitleName() %></td>
                <td><%= sc.getMovieTime() %></td>
                <td><%= sc.getTicketPrice() %></td>
            </tr>
			<% }%> 
	</table>
	</form>
	<script>
		const adjustDate = d =>
		  new Date(d.setMinutes(d.getMinutes() - d.getTimezoneOffset()));
		const setDateElement = (e, p, d) =>
		  e[p] = adjustDate(d).toJSON().match(/\d+-\d+-\d+/);
		
		const dateElement = document.querySelector('input[name=date]');
		
		// valueを本日に設定
		const d = new Date();
		setDateElement(dateElement, 'value', d);
		
		// minを当日に設定
		d.setTime(Date.now());
		d.setDate(d.getDate());
		setDateElement(dateElement, 'min', d);
		
		// maxを一か月後(30日)に設定
		d.setTime(Date.now());
		d.setDate(d.getDate() + 30);
		setDateElement(dateElement, 'max', d);
	</script>
</body>
</html>	