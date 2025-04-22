<%@page import="model.data.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<% 
String errorSign = (String)request.getAttribute("errorSign");
User user = (User)session.getAttribute("userR");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン画面</title>
</head>
<body>
<div class="container">
    <h1 class="page-title">ログイン</h1>
    
    <div class="form-container">
        <form action="Login" method="post">
            <div class="form-group">
                <label for="mail">ログインID（メールアドレス）</label>
                <input type="text" id="mail" class="form-control" placeholder="メールアドレス" name="mail">
            </div>
            
            <div class="form-group">
                <label for="pass">パスワード</label>
                <input type="password" id="pass" class="form-control" name="pass">
            </div>
            
            <% if (errorSign != null) { %>
                <div class="error-message"><%= errorSign %></div>
            <% } %>
            
            <div style="text-align: center; margin-top: 20px;">
                <button type="submit" class="btn">ログイン</button>
            </div>
        </form>
        
        <hr style="margin: 30px 0;">
        
        <div style="text-align: center;">
            <p>アカウントをお持ちでない方は</p>
            <form action="RegisterUser" method="get">
                <button type="submit" class="btn">アカウント作成</button>
            </form>
            
            <p style="margin-top: 20px;">
                <a href="Main" class="action-link">映画館・上映作品を探す</a>
            </p>
        </div>
    </div>
</div>
</body>
</html>