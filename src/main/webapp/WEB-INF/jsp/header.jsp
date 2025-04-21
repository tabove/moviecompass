<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>

<!-- ヘッダーナビゲーション -->
<header class="site-header">
  <div class="header-container">
    <div class="logo-container">
      <a href="Main" class="logo">MovieCompass</a>
    </div>
    
     <!-- ログイン状態によって表示を切り替えるナビゲーション -->
    <nav class="header-nav">
      <!-- 未ログイン時の表示 -->
      <div id="guest-menu" class="nav-menu">
        <a href="RegisterUser" class="nav-button register-btn">ユーザー登録</a>
        <a href="Login" class="nav-button login-btn">ログイン</a>
      </div>
      
      <!-- ログイン済みの表示 -->
      <div id="user-menu" class="nav-menu" style="display: none;">
        <span class="user-greeting">
          <span id="username-display"></span>さん
        </span>
        <a href="MyPage" class="nav-button mypage-btn">マイページ</a>
        <a href="Logout" class="nav-button logout-btn">ログアウト</a>
      </div>
    </nav>
  </div>
</header>

<!-- ヘッダー用CSS -->
<link rel="stylesheet" href="css/reset.css">
<link rel="stylesheet" href="css/header.css">

<!-- ログイン状態を判定するスクリプト -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
$(document).ready(function() {
  // セッションからログイン情報を取得
  var isLoggedIn = <%= session.getAttribute("user_id") != null %>;
  var username = "<%= session.getAttribute("user_name") != null ? session.getAttribute("user_name") : "" %>";
  
  // ログイン状態に応じてメニューを切り替え
  if (isLoggedIn) {
    $("#guest-menu").hide();
    $("#user-menu").show();
    $("#username-display").text(username);
  } else {
    $("#guest-menu").show();
    $("#user-menu").hide();
  }
});
</script>