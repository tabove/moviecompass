<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List, model.SearchCondition" %>
<%@ include file="header.jsp" %>
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
                <select name="theater">
                    <option value="">--選択--</option>
                    <option value="キャナル">ユナイテッド・シネマ キャナルシティ13</option>
                    <!-- 省略 -->
                </select>
            </td>
            <td>作品名:<input type="text" name="title"></td>
            <td>ジャンル:<select name="genre">
                    <option value="">--選択--</option>
                    <option value="恋愛">恋愛</option>
                    <option value="デスゲーム">デスゲーム</option>
                </select></td>
            <td><input type="date" name="date">日付</td>
            <td><input type="time" name="dateTime">時間</td>
        </tr>
    </table>
    <input type="submit" value="検索">
</form>

<h2>検索結果</h2>
<%
    List<SearchCondition> searchResults = (List<SearchCondition>) request.getAttribute("searchResults");
    if (searchResults != null && !searchResults.isEmpty()) {
%>
<form method="post">
    <table>
        <thead>
            <tr>
                <th>選択</th>
                <th>映画館</th>
                <th>作品名</th>
                <th>上映時間</th>
                <th>料金</th>
                <th>予約</th>
            </tr>
        </thead>
        <tbody>
        <% for (SearchCondition sc : searchResults) { %>
            <tr>
                <td><input type="radio" name="selectedMovie" value="<%= sc.getTitleName() %>"></td>
                <td><%= sc.getCinemaName() %></td>
                <td><%= sc.getTitleName() %></td>
                <td><%= sc.getMovieTime() %> 分</td>
                <td><%= sc.getTicketPrice() %> 円</td>
                <td><a href="<%= sc.getUrl() != null ? sc.getUrl() : "#" %>">予約ページへ</a></td>
            </tr>
        <% } %>
        </tbody>
    </table>
</form>
<% } else if (request.getParameter("title") != null) { %>
    <p>該当する作品は見つかりませんでした。</p>
<% } %>

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
