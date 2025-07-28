<%@ page contentType="text/html; charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    index.jsp 파일
    <form action="login" method="post">
        <label for="accountId">
            <input type="text" name="accountId" placeholder="아이디 입력해주세요">
        </label>
        <label for="password">
            <input type="text" name="password" placeholder="비밀번호 입력해주세요">
        </label>
        <button type="submit">제출</button>
    </form>
</body>
</html>