<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<% String errorMsg = (String)request.getAttribute("errorMsg"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約完了画面</title>
</head>
<body>
<% if(errorMsg != null){%>
<p><%= errorMsg %></p>
<% } %>
<a href="WEB-INF/jsp/mypage.jsp">マイページ</a>
<br>
<a href="index.jsp">トップへ</a>
</body>
</html>