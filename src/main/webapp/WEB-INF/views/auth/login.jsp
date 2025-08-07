<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>로그인 페이지</title>
</head>
<body>
    <form id="loginForm">
        <label for="email">아이디</label>
        <input type="text" id="email" name="email" placeholder="아이디를 입력하세요">

        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요">

        <button type="submit">로그인</button>
    </form>
    <button type="button" id="signupBtn">회원가입</button>
    <a href="/oauth2/authorization/google">
        <button type="button">Google 계정으로 로그인</button>
    </a>
</body>
</html>

<script src="/js/auth/auth.js"></script>
<script>
    // 로그인 api 동작
    document.getElementById("loginForm").addEventListener("submit", async (e) => {
        e.preventDefault(); // 기본 form 제출 막기

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        const result = await login(email, password); // (code, data(JwtToken), message), data(json 파싱)
        if (result) {
            const { grantType, refreshToken, accessToken } = result.data.data;

            localStorage.setItem("accessToken", grantType + ' ' + accessToken);
            localStorage.setItem("refreshToken", refreshToken);

            window.location.href="/web/info";
        }
    });
    // 회원가입 페이지 이동
    document.getElementById("signupBtn").addEventListener("click", function () {
        window.location.href = "signup";
    })
</script>
