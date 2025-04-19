<%@page import="model.data.User"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<% User user = (User)session.getAttribute("userR"); %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログイン結果</title>
</head>
<body>
<br><br>
<p style="text-align:center">
<% if(user != null) { %>
ログインしました<br>
<%= user.getName() %>さん、ようこそ</p><br>

<% } else { %>
<p style="text-align:center">
ログインできませんでした<br>
ユーザーID・パスワードを確認してください</p><br>

<form action="Login" method="get">
<p style="text-align:center"><input type="submit" value="ログイン画面へ移動する"></p>
</form>
<% } %>


<p style="text-align:center">
<a href ="Main">映画館・上映作品を探す</a></p>
<form action="Mypage" method="get">
<p style="text-align:center">
<a href ="Mypage">マイページへ</a></p>
</form>

</body>
</html>