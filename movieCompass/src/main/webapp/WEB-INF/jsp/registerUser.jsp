<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<h1>アカウント作成</h1><br>
<%= message %>
<form action="RegisterUser" method="post">
<p style="text-align: center;">
メールアドレス：<input type="text" name="mail"><br><br>
ユ ー ザ    名  ：<input type="text" name="name"><br><br>
パ ス ワ ー ド ：<input type="password" name="pass"><br>
</p>
<p style="text-align: right;">
<input type="submit" value=" 登     録 "></p>
</form>
<form action="Login" method="get">
<p style="text-align: right;">
<input type="submit" value="ログイン"><br><br>
<a href="Main">映画館・上映作品を探す</a></p><br><br>
</form>
</body>s
</html>