<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>회원가입 페이지</title>
</head>
<body>
    <form action="signup" method="post">
        <label for="accountId">이름</label>
        <input type="text" id="name" name="name" placeholder="이름을 입력하세요">

        <label for="accountId">아이디</label>
        <input type="text" id="accountId" name="accountId" placeholder="아이디를 입력하세요">

        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요">

        <button type="submit">회원가입</button>
    </form>
</body>
</html>