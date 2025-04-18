<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="javax.servlet.http.*, javax.servlet.*" %>
<%--
    HttpSession session = request.getSession(false);
    String username = null;
    if (session != null) {
        username = (String) session.getAttribute("username");
    }
--%>
<link rel="stylesheet" href="css/header.css">

<header>
    <h1><a href="index.jsp">Movie Compass</a></h1>
    <nav>
        <%-- if (username == null) { --%>
            <a href="register.jsp">ユーザー登録</a>
            <a href="login.jsp">ログイン</a>
        <%-- } else { --%>
            <a href="mypage.jsp"><%--= username --%>さん</a>
            <a href="logout.jsp">ログアウト</a>
        <%-- } --%>
    </nav>
</header>
