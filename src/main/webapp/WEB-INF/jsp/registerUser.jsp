<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>
<% // 結果メッセージをリクエストスコープから取得
String message = (String)request.getAttribute("message");
if (message == null) {
	message = "";
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ユーザ登録の画面</title>
</head>
<body>
<div class="container">
    <h1 class="page-title">アカウント作成</h1>
    
    <div class="form-container">
        <% if (!message.isEmpty()) { %>
            <div class="error-message"><%= message %></div>
        <% } %>
        
        <form action="RegisterUser" method="post">
            <div class="form-group">
                <label for="mail">メールアドレス</label>
                <input type="email" id="mail" class="form-control" name="mail" required>
            </div>
            
            <div class="form-group">
                <label for="name">ユーザー名</label>
                <input type="text" id="name" class="form-control" name="name" required>
            </div>
            
            <div class="form-group">
                <label for="pass">パスワード</label>
                <input type="password" id="pass" class="form-control" name="pass" required>
            </div>
            
            <div style="text-align: center; margin-top: 20px;">
                <button type="submit" class="btn">登録</button>
            </div>
        </form>
        
        <hr style="margin: 30px 0;">
        
        <div style="text-align: center;">
            <p>すでにアカウントをお持ちの方は</p>
            <form action="Login" method="get">
                <button type="submit" class="btn">ログイン</button>
            </form>
            
            <p style="margin-top: 20px;">
                <a href="Main" class="action-link">映画館・上映作品を探す</a>
            </p>
        </div>
    </div>
</div>
</body>
</html>