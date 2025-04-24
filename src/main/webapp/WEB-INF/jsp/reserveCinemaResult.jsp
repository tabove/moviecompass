<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.data.Reservation" %>
<%@ include file="header.jsp" %>
<% String errorMsg = (String)request.getAttribute("errorMsg"); 
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>予約完了画面</title>
</head>
<body>
	<div class="container">
	    <h1 class="page-title">予約結果</h1>
           <div class="result-message"><%= errorMsg %></div>       

           <div class="action-buttons">
               <a href="Mypage" class="btn">マイページへ</a>
               <a href="Main" class="btn">トップへ戻る</a>
           </div>
</div>
</body>
</html>