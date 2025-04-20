<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="model.data.SearchCondition, model.data.MovieSchedule" %>
<%@ page import="java.util.List,java.util.Map, java.util.Set, java.util.HashMap, java.util.HashSet, java.util.ArrayList" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="UTF-8">
	    <title>MovieCompass</title>
	    <link rel="stylesheet" href="css/header.css">
	    <link rel="stylesheet" href="css/style2.css">
	</head>
	<body>
		<form id="searchForm" action="Main" method="get">
    		<table border="1">
        		<tr>
	            	<td>
		                映画館:
						<select name="cinema_id">
						<option value=""></option>
						<c:forEach var="theater" items="${theaterList}">
						<option value="${theater.cinema_id}">${theater.cinema_name}</option>
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
    
    if (searchResults != null && !searchResults.isEmpty()) {
%>
<div class="movie-list-view">
<%
    // 日付ごとにグループ化
    Map<String, List<MovieSchedule>> schedulesByDate = new HashMap<>();
    
    for (MovieSchedule schedule : searchResults) {
        if (!schedulesByDate.containsKey(schedule.getMovie_date())) {
            schedulesByDate.put(schedule.getMovie_date(), new ArrayList<>());
        }
        schedulesByDate.get(schedule.getMovie_date()).add(schedule);
    }
    
    // 日付ごとに処理
    for (Map.Entry<String, List<MovieSchedule>> dateEntry : schedulesByDate.entrySet()) {
        String date = dateEntry.getKey();
        List<MovieSchedule> schedulesForDate = dateEntry.getValue();
%>
    <div class="date-section">
        <h3 class="date-heading"><%= date %></h3>
<%
        // 映画館ごとにグループ化
        Map<String, List<MovieSchedule>> schedulesByCinema = new HashMap<>();
        
        for (MovieSchedule schedule : schedulesForDate) {
            if (!schedulesByCinema.containsKey(schedule.getCinema_name())) {
                schedulesByCinema.put(schedule.getCinema_name(), new ArrayList<>());
            }
            schedulesByCinema.get(schedule.getCinema_name()).add(schedule);
        }
        
        // 映画館ごとに処理
        for (Map.Entry<String, List<MovieSchedule>> cinemaEntry : schedulesByCinema.entrySet()) {
            String cinemaName = cinemaEntry.getKey();
            List<MovieSchedule> schedulesForCinema = cinemaEntry.getValue();
%>
        <div class="cinema-section">
            <div class="cinema-header">
                <div class="cinema-name"><%= cinemaName %></div>
            </div>
            
<%
            // 映画ごとにグループ化
            Map<String, List<MovieSchedule>> schedulesByMovie = new HashMap<>();
            
            for (MovieSchedule schedule : schedulesForCinema) {
                if (!schedulesByMovie.containsKey(schedule.getMovie_name())) {
                    schedulesByMovie.put(schedule.getMovie_name(), new ArrayList<>());
                }
                schedulesByMovie.get(schedule.getMovie_name()).add(schedule);
            }
            
            // 映画ごとに処理
            for (Map.Entry<String, List<MovieSchedule>> movieEntry : schedulesByMovie.entrySet()) {
                String movieName = movieEntry.getKey();
                List<MovieSchedule> schedulesForMovie = movieEntry.getValue();
                
                // 各時間ごとに行を生成
                for (MovieSchedule schedule : schedulesForMovie) {
%>
                <div class="movie-row">
                    <div class="movie-name"><%= movieName %></div>
                    <div class="movie-time"><%= schedule.getMovie_hour() %></div>
                    <div class="movie-price"><%= schedule.getTicket_price() %>円</div>
                    <div class="movie-action">
                        <form action="Reservation" method="POST">
                            <input type="hidden" name="cinema_id" value="<%= schedule.getCinema_id() %>">
                            <input type="hidden" name="cinema_name" value="<%= schedule.getCinema_name() %>">
                            <input type="hidden" name="movie_id" value="<%= schedule.getMovie_id() %>">
                            <input type="hidden" name="movie_name" value="<%= schedule.getMovie_name() %>">
                            <input type="hidden" name="ticket_price" value="<%= schedule.getTicket_price() %>">
                            <input type="hidden" name="movie_hour" value="<%= schedule.getMovie_time() %>">
                            <button type="submit" class="reservation-btn">予約する</button>
                        </form>
                    </div>
                </div>
<%
                }
            }
%>
        </div>
<%
        }
%>
    </div>
<%
    }
%>
</div>
<% } else { %>
<p>検索条件を入力してください</p>
<% } %>
<script>
    // 既存のスクリプトをそのまま残す
    const adjustDate = d =>
      new Date(d.setMinutes(d.getMinutes() - d.getTimezoneOffset()));
    const setDateElement = (e, p, d) =>
      e[p] = adjustDate(d).toJSON().match(/\d+-\d+-\d+/);
    
    const dateElement = document.querySelector('input[name=date]');

    const d = new Date();
                
    // minを当日に設定
    d.setTime(Date.now());
    d.setDate(d.getDate());
    setDateElement(dateElement, 'min', d);
    
    // maxを一か月後(30日)に設定
    d.setTime(Date.now());
    d.setDate(d.getDate() + 30);
    setDateElement(dateElement, 'max', d);
    
    // テーブル表示をカード表示に変換する機能
    document.addEventListener('DOMContentLoaded', function() {
        // 既にカード表示がある場合は処理しない
        if (document.querySelector('.search-results')) {
            return;
        }
        
        const resultTable = document.querySelector('h2 + table');
        if (!resultTable) return;
        
        // データ構造を作成
        const movieData = {};
        let currentDate = '';
        let currentCinema = '';
        let currentMovie = '';
        
        // テーブルの行を処理
        const rows = resultTable.querySelectorAll('tbody tr');
        for (let i = 0; i < rows.length; i++) {
            const row = rows[i];
            const cells = row.querySelectorAll('td, th');
            
            if (cells.length === 1) {
                const content = cells[0].textContent.trim();
                // 日付行の場合
                if (cells[0].tagName.toLowerCase() === 'th') {
                    currentDate = content;
                    if (!movieData[currentDate]) {
                        movieData[currentDate] = {};
                    }
                } 
                // 映画館名の場合
                else if (cells[0].colSpan === 5) {
                    currentCinema = content;
                    if (!movieData[currentDate][currentCinema]) {
                        movieData[currentDate][currentCinema] = {};
                    }
                } 
                // 映画名の場合
                else {
                    currentMovie = content;
                    if (!movieData[currentDate][currentCinema][currentMovie]) {
                        movieData[currentDate][currentCinema][currentMovie] = {
                            times: [],
                            prices: {},
                            forms: {}
                        };
                    }
                }
            } 
            // 上映時間、料金、予約ボタンの行
            else if (cells.length >= 3 && currentMovie && currentCinema && currentDate) {
                const time = cells[0].textContent.trim();
                const price = cells[1].textContent.trim();
                const form = cells[2].querySelector('form');
                
                movieData[currentDate][currentCinema][currentMovie].times.push(time);
                movieData[currentDate][currentCinema][currentMovie].prices[time] = price;
                if (form) {
                    movieData[currentDate][currentCinema][currentMovie].forms[time] = form.cloneNode(true);
                }
            }
        }
        
        // カード表示を作成
        const searchResults = document.createElement('div');
        searchResults.className = 'search-results';
        
        for (const date in movieData) {
            const dateSection = document.createElement('div');
            dateSection.className = 'date-section';
            
            const dateHeading = document.createElement('h3');
            dateHeading.className = 'date-heading';
            dateHeading.textContent = date;
            dateSection.appendChild(dateHeading);
            
            for (const cinema in movieData[date]) {
                const cinemaSection = document.createElement('div');
                cinemaSection.className = 'cinema-section';
                
                const cinemaName = document.createElement('h4');
                cinemaName.className = 'cinema-name';
                cinemaName.textContent = cinema;
                cinemaSection.appendChild(cinemaName);
                
                const movieCards = document.createElement('div');
                movieCards.className = 'movie-cards';
                
                for (const movie in movieData[date][cinema]) {
                    const movieCard = document.createElement('div');
                    movieCard.className = 'movie-card';
                    
                    const movieTitle = document.createElement('div');
                    movieTitle.className = 'movie-title';
                    movieTitle.textContent = movie;
                    movieCard.appendChild(movieTitle);
                    
                    const movieDetails = document.createElement('div');
                    movieDetails.className = 'movie-details';
                    
                    const timeSlots = document.createElement('div');
                    timeSlots.className = 'time-slots';
                    
                    // 時間スロットを追加
                    for (const time of movieData[date][cinema][movie].times) {
                        const timeSlot = document.createElement('div');
                        timeSlot.className = 'time-slot';
                        timeSlot.textContent = time;
                        timeSlots.appendChild(timeSlot);
                    }
                    
                    movieDetails.appendChild(timeSlots);
                    
                    // 料金情報
                    const priceInfo = document.createElement('div');
                    priceInfo.className = 'price-info';
                    // 最初の時間の料金を使用
                    const firstTime = movieData[date][cinema][movie].times[0];
                    priceInfo.textContent = movieData[date][cinema][movie].prices[firstTime];
                    movieDetails.appendChild(priceInfo);
                    
                    // 予約フォーム
                    if (movieData[date][cinema][movie].forms[firstTime]) {
                        const form = movieData[date][cinema][movie].forms[firstTime];
                        const button = form.querySelector('button');
                        if (button) {
                            button.className = 'reservation-btn';
                            button.textContent = '予約する';
                        }
                        movieDetails.appendChild(form);
                    }
                    
                    movieCard.appendChild(movieDetails);
                    movieCards.appendChild(movieCard);
                }
                
                cinemaSection.appendChild(movieCards);
                dateSection.appendChild(cinemaSection);
            }
            
            searchResults.appendChild(dateSection);
        }
        
        // テーブルをカード表示に置き換え
        resultTable.parentNode.replaceChild(searchResults, resultTable);
    });
</script>
	</body>
</html>
