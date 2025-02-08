<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>knowHOWnet - Вход</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link href="/SemesterWork/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="custom-bg-color">
<nav class="navbar navbar-shadow navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a href="/SemesterWork/users">
            <img class="logotype" src="${pageContext.request.contextPath}/logo.png" alt="Логотип knowHOWnet">
        </a>
    </div>
</nav>
<div class="container-fluid min-vh-100 d-flex justify-content-center align-items-center">

    <div class="login-container custom-font">
        <h3 class="text-center mb-4 custom-font-bold">Sign up</h3>
        <form action="${pageContext.request.contextPath}/sign_up" method="post">
            <div class="mb-3">
                <input type="text" class="form-control" name="username" placeholder="Username" required>
            </div>
            <div class="mb-3">
                <input type="text" class="form-control" name="email" placeholder="Email" required>
            </div>
            <div class="mb-3">
                <input type="password" class="form-control" name="password" placeholder="Password" required>
            </div>
            <div class="mb-3">
                <input type="password" class="form-control" name="confirm_password" placeholder="Confirm password" required>
            </div>
            <div class="mb-3">
                <textarea maxlength="400" id="aboutMe" class="form-control" name="aboutme" rows="5" placeholder="(Optional) Write something about yourself..."></textarea>
                <small id="charCount" class="form-text text-muted">400 max length</small>
                <div id="Error" class="text-danger mt-2 message" style="${param.exists_error != null ? '' : 'display: none'}">
                    Unique username and email
                </div>
                <div id="Match_Error" class="text-danger mt-2 message" style="${param.match_error != null ? '' : 'display: none'}">
                    Password isn't matched
                </div>
                <div id="Valid_Error" class="text-danger mt-2 message" style="${param.valid_error != null ? '' : 'display: none'}">
                    Password isn't valid
                </div>
            </div>
            <button type="submit" class="custom-font-bold btn btn-success w-100">Sign up</button>
        </form>
    </div>
</div>
</body>
</html>
