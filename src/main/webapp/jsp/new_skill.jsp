<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>knowHOWnet - Вход</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../css/profile.css">
    <link href="/SemesterWork/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="custom-bg-color">
<nav class="navbar navbar-shadow navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a href="/SemesterWork/users">
            <img class="logotype" src="/SemesterWork/logo.png" alt="Логотип knowHOWnet">
        </a>
    </div>
    <div class="ms-auto" style="padding: 15px">
        <a href="/SemesterWork/logout">
            <svg width="20px" height="20px" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" fill="none">
                <path fill="#05187F" fill-rule="evenodd" d="M6 2a3 3 0 0 0-3 3v14a3 3 0 0 0 3 3h6a3 3 0 0 0 3-3V5a3 3 0 0 0-3-3H6zm10.293 5.293a1 1 0 0 1 1.414 0l4 4a1 1 0 0 1 0 1.414l-4 4a1 1 0 0 1-1.414-1.414L18.586 13H10a1 1 0 1 1 0-2h8.586l-2.293-2.293a1 1 0 0 1 0-1.414z" clip-rule="evenodd"/>
            </svg>
        </a>
    </div>
</nav>
<nav class="navbar navbar-expand-lg navbar-shadow bg-light">
    <div class="ms-auto me-auto" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="/SemesterWork/home">Main</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="/SemesterWork/home/skills"><u>Skills</u></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/SemesterWork/home/lessons">Lessons</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/SemesterWork/home/notifications">Notifications</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container-fluid min-vh-100 d-flex justify-content-center align-items-center">
    <!-- Контейнер для логина -->
    <div class="login-container">
        <h3 class="text-center mb-4 custom-font-bold">Add Skill</h3>
        <form action="/SemesterWork/home/new_skill" method="post">
            <div class="mb-3">
                <input type="text" class="form-control" name="name" placeholder="Name" required>
            </div>
            <div class="mb-3">
                <input type="text" class="form-control" name="category" placeholder="Category" required>
            </div>
            <div class="mb-3">
                <textarea maxlength="400" id="aboutMe" class="form-control" name="description" rows="5" placeholder="Description of skill" required></textarea>
                <small id="charCount" class="form-text text-muted">400 max length</small>
                <div id="Error" class="text-danger mt-2 message" style="<c:if test='${not empty param.exists_error}'>display: block</c:if>; display: none;">
                    You already have skill with such name
                </div>
            </div>
            <button type="submit" class="btn btn-success w-100 custom-font-bold">Add</button>
        </form>
    </div>
</div>
</body>
</html>
