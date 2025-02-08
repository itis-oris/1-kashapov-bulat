<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - knowHOWnet</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../css/profile.css">
    <link href="/SemesterWork/bootstrap/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="custom-bg-color custom-font">

<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a href="/users">
            <img class="logotype" src="http://localhost:8080/SemesterWork/logo.png" alt="Логотип knowHOWnet">
        </a>
    </div>
    <div class="ms-auto" style="padding: 15px">
        <a href="/logout">
            <svg width="20px" height="20px" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" fill="none">
                <path fill="#05187F" fill-rule="evenodd"
                      d="M6 2a3 3 0 0 0-3 3v14a3 3 0 0 0 3 3h6a3 3 0 0 0 3-3V5a3 3 0 0 0-3-3H6zm10.293 5.293a1 1 0 0 1 1.414 0l4 4a1 1 0 0 1 0 1.414l-4 4a1 1 0 0 1-1.414-1.414L18.586 13H10a1 1 0 1 1 0-2h8.586l-2.293-2.293a1 1 0 0 1 0-1.414z"
                      clip-rule="evenodd"/>
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
                <a class="nav-link" href="/SemesterWork/home/skills">Skills</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="/SemesterWork/home/lessons"><u>Lessons</u></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/SemesterWork/home/notifications">Notifications</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container mt-4">
    <div class="skill-container card p-5">
        <div class="d-flex justify-content-between align-items-center">
            <h3 style="font-family: Clab_Bold, sans-serif">Lesson №${lesson.id}</h3>
            <c:if test="${lesson.teacher.id == user.id}">
                <form action="/SemesterWork/home/lesson_done" method="post">
                    <input type="hidden" name="lesson" value="${lesson.id}">
                    <button type="submit" class="btn btn-success ${lesson.status == "done" ? "disabled" : ""}">Done</button>
                </form>
            </c:if>
        </div>
        <p>
            <span style="font-family: Clab_Bold, sans-serif">Student:</span>
            <span>${lesson.student.username}</span>
        </p>
        <p>
            <span style="font-family: Clab_Bold, sans-serif">Teacher:</span>
            <span>${lesson.teacher.username}</span>
        </p>
    </div>
</div>
</body>
</html>
