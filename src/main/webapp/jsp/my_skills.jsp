<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <a href="/SemesterWork/users">
            <img class="logotype" src="/SemesterWork/logo.png" alt="Логотип knowHOWnet">
        </a>
    </div>
    <div class="ms-auto" style="padding: 15px">
        <a href="/SemesterWork/logout">
            <svg width="20px" height="20px" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg" fill="none"><path fill="#05187F" fill-rule="evenodd" d="M6 2a3 3 0 0 0-3 3v14a3 3 0 0 0 3 3h6a3 3 0 0 0 3-3V5a3 3 0 0 0-3-3H6zm10.293 5.293a1 1 0 0 1 1.414 0l4 4a1 1 0 0 1 0 1.414l-4 4a1 1 0 0 1-1.414-1.414L18.586 13H10a1 1 0 1 1 0-2h8.586l-2.293-2.293a1 1 0 0 1 0-1.414z" clip-rule="evenodd"/></svg>
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

<div class="container mt-4">
    <div class="skill-container card p-5">
        <div class="d-flex justify-content-between align-items-center">
            <h1 class="custom-font-bold">Skills</h1>
            <form class="ms-auto" action="/SemesterWork/home/new_skill" method="get">
                <button type="submit" class="btn btn-success custom-font-bold">Add skill</button>
            </form>
        </div>
        <c:choose>
            <c:when test="${not empty skills}">
                <c:forEach var="skill" items="${skills}">
                    <c:set var="curColor" value="color-${skill.id}" />
                    <c:set var="curDescription" value="description-${skill.id}" />
                    <div class="skill-container">
                        <a href="../home/skills?skill=${skill.id}">
                            <div class="skill-header" style="background-color: ${requestScope[curColor]}">
                                    ${skill.name}
                                <div class="ms-auto">${skill.rating}★</div>
                            </div>
                            <div class="skill-body skill-mini-text">
                                <p>Category: ${skill.category}</p>
                                <p>${requestScope[curDescription]}</p>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="null message">
                    No skills so far...
                </div>
            </c:otherwise>
        </c:choose>
        <div class="pagination-container text-center" style="font-family: Clab_Bold, sans-serif">
            <nav aria-label="Page navigation example">
                <ul class="pagination justify-content-center mb-0">
                    <li class="page-item ${pageNum == 1 ? 'disabled' : ''}">
                        <a class="black-color page-link" href="/SemesterWork/home/skills?page=${pageNum - 1}">&laquo;</a>
                    </li>
                    <c:forEach var="i" begin="1" end="${countOfPages}">
                        <li class="page-item ${i == pageNum ? 'active' : ''}">
                            <a class="page-link custom-active" href="/SemesterWork/home/skills?page=${i}">${i}</a>
                        </li>
                    </c:forEach>
                    <li class="page-item ${pageNum == countOfPages ? 'disabled' : ''}">
                        <a class="black-color page-link" href="/SemesterWork/home/skills?page=${pageNum + 1}">&raquo;</a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</div>
</body>
</html>
