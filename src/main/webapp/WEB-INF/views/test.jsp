<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<body>

<div class="container">
    <table class="table table-hover table table-striped">
        <tr>
            <th>작성자</th>
            <th>제목</th>
        </tr>

        <tr>
            <th>${member.getName()}</th>
            <th>${member.getAccountId()}</th>
            <th>${member.getPassword()}</th>
        </tr>
    </table>
</div>

</body>
</html>