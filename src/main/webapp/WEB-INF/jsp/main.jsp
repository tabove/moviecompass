<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.data.SearchCondition, model.data.MovieSchedule" %>
<%@ page import="java.util.List,java.util.Map, java.util.Set, java.util.HashMap, java.util.HashSet" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>MovieCompass</title>
	    <link rel="stylesheet" href="css/header.css">
	    <style>
	        form { margin: 20px; }
	        table { border-collapse: collapse; margin: 20px; width: 95%; }
	        th, td { border: 1px solid #ccc; padding: 10px; text-align: center; }
	        th { background-color: #eee; }
	    </style>
	</head>
	<body>
		<form id="searchForm" action="Main" method="get">
    		<table border="1">
        		<tr>
	            	<td>
		                映画館:
						<select name="cinema_name">
						<option value=""></option>
						<c:forEach var="theater" items="${theaterList}">
						<option value="${theater.cinema_name}">${theater.cinema_name}</option>
						</c:forEach>
						</select>
		            </td>
		            <td>作品名:<input type="text" name="movie_name" placeholder="作品名を入力するのDA!!"></td>
		            <td><label for="genre">ジャンル:</label>
		            	<select name="genre">
		            		<option></option>
		                    <c:forEach var="genre" items="${genreList}">
		                    	<option value="${genre}">${genre}</option>
		                    </c:forEach>
		                </select>
		            </td>
		            <td>日付<input type="date" name="date"></td>
		            <td>時間<input type="time" name="dateTime"></td>
		        </tr>
		    </table>
	    <input type="submit" value="検索">
		</form>
		
		<h2>検索結果</h2>
		<%
   			List<MovieSchedule> searchResults = (List<MovieSchedule>) request.getAttribute("searchResults");
			Set<String> displayedDates = new HashSet<>(); // 表示済みの日付
    		Map<String, Set<String>> displayedMoviesByCinema = new HashMap<>(); // 映画館ごとの作品名管理

    		if (searchResults != null && !searchResults.isEmpty()) {
		%>
		<table>
		    <tbody>
		    <%for (MovieSchedule rs : searchResults) { %>
			<tr>
				<% // 日付が未表示なら表示する
                	if (!displayedDates.contains(rs.getMovie_date())) { 
                    displayedDates.add(rs.getMovie_date()); 
           		 %>
                	<th colspan="5"><%= rs.getMovie_date() %></th>
           		 <% } %>
			</tr>
			<tr>
				<%	// 映画館が未表示なら表示
					if (!displayedMoviesByCinema.containsKey(rs.getCinema_name())) { 
					displayedMoviesByCinema.put(rs.getCinema_name(), new HashSet<>());
            	%>
                <td colspan="5"><%= rs.getCinema_name() %></td>
            <% } %>
            </tr>
            <tr>
            	<% // 作品名がその映画館でまだ表示されていない場合
					Set<String> displayedMovies = displayedMoviesByCinema.get(rs.getCinema_name());
					if (!displayedMovies.contains(rs.getMovie_name())) {
					displayedMovies.add(rs.getMovie_name());
            	%>
                	<td colspan="5"><%= rs.getMovie_name() %></td>
            	<% } %>
        	</tr>
        	<tr>
				<td><%= rs.getMovie_hour() %></td>
				<td><%= rs.getTicket_price() %>円</td>
				<td>
					<form action="Reservation" method="POST">
						<input type="hidden" name="cinema_id" value="<%= rs.getCinema_id() %>">
						<input type="hidden" name="cinema_name" value="<%= rs.getCinema_name() %>">
						<input type="hidden" name="movie_id" value="<%= rs.getMovie_id() %>">
                        <input type="hidden" name="movie_name" value="<%= rs.getMovie_name() %>">
                        <input type="hidden" name="ticket_price" value="<%= rs.getTicket_price() %>">
                        <input type="hidden" name="movie_hour" value="<%= rs.getMovie_time() %>">
						<button type="submit">予約ページへ</button>
					</form>
				</td>
			</tr>
			<% } %>
		    </tbody>
		</table>
		<% } else {%>
		<p>検索条件を入力してください</p>
		<% } %>
<!--      ※機能確認用の仮フォーム
	<form action="Reservation" method="get">
		<input value="キャナルシティ博多" name="cinemaName">
		<input value="名探偵コナン" name="movieName">
		<input value="11:30" name="movieTime">
		<input value="2000" name="ticketPrice">
		<input type="submit" value="予約ページへ">	
	</form>
-->
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
