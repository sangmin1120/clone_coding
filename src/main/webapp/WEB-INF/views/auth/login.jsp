<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>로그인 페이지</title>
</head>
<body>
    <form action="login" method="post">
        <label for="accountId">아이디</label>
        <input type="text" id="accountId" name="accountId" placeholder="아이디를 입력하세요">

        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요">

        <button type="submit">로그인</button>
    </form>
    <button type="button" id="signupBtn">회원가입</button>
</body>
</html>

<script>
    document.getElementById("signupBtn").addEventListener("click", function () {
        window.location.href = "signup";
    })
</script>
