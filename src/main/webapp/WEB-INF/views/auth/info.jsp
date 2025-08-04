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
info.jsp 파일 - My Info
<table class="table table-hover table table-striped">
    <tr>
        <th>이름</th>
        <th>아이디</th>
    </tr>
    <tr>
        <th id="memberName">${member.getName()}</th>
        <th id="memberEmail">${member.getEmail()}</th>
    </tr>
</table>
</body>
</html>
<script src="/js/auth/info.js"></script>
<script>
    document.addEventListener("DOMContentLoaded", async () => {
        const result = await info(); // (code, data, message) json 파싱 데이터
        const { data } = result.data; // name, email

        if (data) {
            document.querySelector("#memberName").textContent = data.name;
            document.querySelector("#memberEmail").textContent = data.email;
        }
    })
</script>