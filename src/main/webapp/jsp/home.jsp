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
    <link rel="stylesheet" href="css/styles.css">
    <link rel="stylesheet" href="css/profile.css">
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
                <a class="nav-link active" href="http://localhost:8080/SemesterWork/home"><u>Main</u></a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://localhost:8080/SemesterWork/home/skills">Skills</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://localhost:8080/SemesterWork/home/lessons">Lessons</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="http://localhost:8080/SemesterWork/home/notifications">Notifications</a>
            </li>
        </ul>
    </div>
</nav>

<div class="container mt-4">
    <div class="row">
        <div class="col-md-4 mb-4">
            <div class="card mb-4">
                <div class="card-body text-center">
                    <img src="http://localhost:8080/SemesterWork/default.png" style="height:100px" alt="User Avatar" class="rounded-circle mb-3">
                    <h5 class="card-title" style="font-family: Clab_Bold, sans-serif">${user.username}</h5>
                    <div class="point-values">
                        <span>${user.skillpoints} SkP</span>
                        <span style="background-color: ${color}">${user.avg_rating}★</span>
                    </div>
                    <div class="divider"></div>
                    <p>
                        <span class="custom-font-bold"> Email:</span>
                        <a href="mailto:${user.email}">${user.email}</a>
                    </p>
                </div>
            </div>
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title custom-font-bold">About Me</h5>
                    <p>${user.description}</p>
                </div>
            </div>
        </div>
        <div class="col-md-8 card mb-4">
            <h4 class="mt-3 mb-3" style="font-family: Clab_Bold, sans-serif">Skills</h4>
            <!-- Skill Block -->
            <c:choose>
                <c:when test="${not empty skills}">
                    <c:forEach var="skill" items="${skills}" varStatus="status">
                        <c:set var="curColor" value="color-${status.index + 1}" />
                        <c:set var="curDescription" value="description-${status.index + 1}" />
                        <div class="skill-container">
                            <a href="home/skills?skill=${skill.id}">
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
                    <div class="mb-3" style="text-align: center">
                        <a class="message" href="http://localhost:8080/SemesterWork/home/skills">see more</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="null message">
                        No skills so far...
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>
