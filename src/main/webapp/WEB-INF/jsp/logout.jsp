<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="header.jsp" %>    
    
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ログアウト</title>
</head>
<body>
<div class="container">
    <div class="content-section">
        <div class="main-content">
            <h1 class="page-title">ログアウト</h1>
            
            <div class="form-container">
                <div style="text-align: center; margin: 30px 0;">
                    <h2 style="color: #3498db;">ログアウトしました</h2>
                    <p>ご利用ありがとうございました。</p>
                    
                    <form action="Login" method="get" style="margin-top: 30px;">
                        <button type="submit" class="btn">ログイン</button>
                    </form>
                    
                    <div style="margin-top: 20px;">
                        <a href="Main" class="action-link">映画館・上映作品を探す</a>
                    </div>
                </div>
            </div>
        </div>
        
    </div>
</div>

</body>
</html>