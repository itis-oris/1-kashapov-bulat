<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"
           prefix="fn" %>
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
        <a href="http://localhost:8080/SemesterWork/users">
            <img class="logotype" src="http://localhost:8080/SemesterWork/logo.png" alt="Логотип knowHOWnet">
        </a>
    </div>
    <div class="ms-auto" style="padding: 15px">
        <a href="http://localhost:8080/SemesterWork/logout">
            <svg width="20px" height="20px" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" fill="none"><path fill="#05187F" fill-rule="evenodd" d="M6 2a3 3 0 0 0-3 3v14a3 3 0 0 0 3 3h6a3 3 0 0 0 3-3V5a3 3 0 0 0-3-3H6zm10.293 5.293a1 1 0 0 1 1.414 0l4 4a1 1 0 0 1 0 1.414l-4 4a1 1 0 0 1-1.414-1.414L18.586 13H10a1 1 0 1 1 0-2h8.586l-2.293-2.293a1 1 0 0 1 0-1.414z" clip-rule="evenodd"/></svg>
        </a>
    </div>
</nav>
<nav class="navbar navbar-expand-lg navbar-shadow bg-light">
    <div class="ms-auto me-auto" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="http://localhost:8080/SemesterWork/home">Main</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://localhost:8080/SemesterWork/home/skills">Skills</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="http://localhost:8080/SemesterWork/home/lessons"><u>Lessons</u></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://localhost:8080/SemesterWork/home/notifications">Notifications</a>
            </li>
        </ul>
    </div>
</nav>
<div class="container mt-4">
    <div class="skill-container card p-5">
        <h1 class="text-center custom-font-bold">Lessons</h1>
        <c:choose>
            <c:when test="${not empty lessons}">
                <c:forEach var="lesson" items="${lessons}">
                    <div class="skill-container">
                        <a href="/SemesterWork/home/lessons?lesson=${lesson.id}">
                            <div class="skill-header" style="background-color: ${lesson.teacher.id == user.id ? '#659dbd' : 'green'}">
                                Lesson №${lesson.id}
                            </div>
                            <div class="skill-body skill-mini-text">
                                <p>Teacher: ${lesson.teacher.username}</p>
                                <p>Student: ${lesson.student.username}</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="null message">
                    No lessons so far...
                </div>
            </c:otherwise>
        </c:choose>
        <div class="pagination-container text-center" style="font-family: Clab_Bold, sans-serif">
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center mb-0">
                    <li class="page-item ${page == 1 ? 'disabled' : ''}">
                        <a class="black-color page-link" href="http://localhost:8080/SemesterWork/home/lessons?page=${page - 1}">&laquo;</a>
                    </li>
                    <c:forEach var="i" begin="1" end="${numOfPages}">
                        <li class="page-item">
                            <a class="black-color page-link ${i == page ? 'custom-active active' : ''}" href="http://localhost:8080/SemesterWork/home/lessons?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${page == numOfPages ? 'disabled' : ''}">
                        <a class="black-color page-link" href="http://localhost:8080/SemesterWork/home/lessons?page=${page + 1}">&raquo;</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>
</body>
</html>
