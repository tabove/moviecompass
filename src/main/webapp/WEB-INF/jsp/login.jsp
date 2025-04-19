<%@page import="model.data.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<br><br>
<div>
<form action="Login" method="post">
<p style="text-align:center">ログインIDとパスワードを入力してください</p>
<p style="text-align:center">ログインID：<input type="text" value="" placeholder="メールアドレス" name="mail"></p>
<p style="text-align:center">パスワード：<input type="password" name="pass"></p>

<% if (errorSign != null) { %>
<p style="text-align:center"><font color="red"><%= errorSign %></font></p>
<% } %>

<p style="text-align:center">　　　　　　　　　　　　　
<input type="submit" value="ログイン"></p>
</form>




<form action="RegisterUser" method="get">
<p style="text-align:center">　　　　　　　　　　　
<input type="submit" value="アカウント作成"></p>
</form>

<p style="text-align:center">　　　　　　
<a href ="Main">映画館・上映作品を探す</a></p>

</div>
</body>
</html>