<%@page import="model.data.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
    
<% User user = (User)session.getAttribute("userR"); %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン結果</title>
</head>
<body>
<div class="container">
    <div class="content-section">
        <div class="main-content">
            <h1 class="page-title">ログイン結果</h1>
            
            <div class="form-container">
                <% if(user != null) { %>
                    <div style="text-align: center; margin: 30px 0;">
                        <h2 style="color: #2ecc71;">ログインしました</h2>
                        <p><%= user.getName() %>さん、ようこそ</p>
                        
                        <div style="margin-top: 30px;">
                            <a href="Mypage" class="btn">マイページへ</a>
                        </div>
                    </div>
                <% } else { %>
                    <div style="text-align: center; margin: 30px 0;">
                        <h2 style="color: #e74c3c;">ログインできませんでした</h2>
                        <p>ユーザーID・パスワードを確認してください</p>
                        
                        <form action="Login" method="get" style="margin-top: 20px;">
                            <button type="submit" class="btn">ログイン画面へ移動する</button>
                        </form>
                    </div>
                <% } %>
                
                <div style="text-align: center; margin-top: 20px;">
                    <a href="Main" class="action-link">映画館・上映作品を探す</a>
                </div>
            </div>
        </div>
        
    </div>
</div>
</body>
</html>