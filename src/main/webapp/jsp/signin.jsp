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
            <img class="logotype" src="/SemesterWork/logo.png" alt="Логотип knowHOWnet">
        </a>
    </div>
</nav>
<div class="container-fluid min-vh-100 d-flex justify-content-center align-items-center">

    <div class="login-container custom-font">
        <h3 class="text-center mb-4 custom-font-bold">Sign in</h3>
        <form action="${pageContext.request.contextPath}/sign_in" method="post">
            <div class="mb-3">
                <input type="text" class="form-control" name="username" placeholder="Username" required>
            </div>
            <div class="mb-3">
                <input type="password" class="form-control" name="password" placeholder="Password" required>
                <div id="Error" class="text-danger mt-2 message" style="${param.not_found_error != null ? '' : 'display: none'}">
                    Wrong username or password
                </div>
                <div id="Success" class="text-success mt-2 message" style="${param.create_success != null ? '' : 'display: none'}">
                    User successfully created
                </div>
                <div id="Create-error" class="text-danger mt-2 message" style="${param.create_error != null ? '' : 'display: none'}">
                    Unknown error: user creation failed
                </div>
            </div>
            <button type="submit" style="font-family: Clab_Bold, sans-serif" class="btn btn-success w-100">Sign in</button>
        </form>

        <div class="mt-2 message">
            Don’t have an account? <a href="${pageContext.request.contextPath}/sign_up">Sign up</a>
        </div>
    </div>
</div>
</body>
</html>
