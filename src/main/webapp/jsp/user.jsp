<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <a href="/SemesterWork/users">
            <img class="logotype" src="http://localhost:8080/SemesterWork/logo.png" alt="Логотип knowHOWnet">
        </a>
    </div>
    <div class="ms-auto custom-font-bold" style="padding: 15px">
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <a href="http://localhost:8080/SemesterWork/home">
                    <svg fill="#000000" width="20px" height="20px" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M6.03531778,18.739764..."/>
                    </svg>
                </a>
            </c:when>
            <c:otherwise>
                <a href="http://localhost:8080/SemesterWork/sign_in" class="black-color">Sign_in</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>

<nav class="navbar navbar-expand-lg navbar-shadow bg-light">
    <div class="ms-auto me-auto" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link ${page == 'main' ? 'active' : ''}" href="http://localhost:8080/SemesterWork/users?user=${requestScope.user.username}"><u>Main</u></a>
            </li>
            <li class="nav-item">
                <a class="nav-link ${page == 'skills' ? 'active' : ''}" href="http://localhost:8080/SemesterWork/users/skills?user=${requestScope.user.username}">Skills</a>
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
                    <h5 class="card-title" style="font-family: Clab_Bold, sans-serif">${requestScope.user.username}</h5>
                    <div class="point-values">
                        <span>${requestScope.user.skillpoints} SkP</span>
                        <span style="background-color: ${color}">${requestScope.user.avg_rating}★</span>
                    </div>
                    <div class="divider"></div>
                    <p>
                        <span class="custom-font-bold">Email:</span>
                        <a href="mailto:${requestScope.user.email}">${requestScope.user.email}</a>
                    </p>
                </div>
            </div>

            <div class="card">
                <div class="card-body">
                    <h5 class="card-title custom-font-bold">About Me</h5>
                    <p>${requestScope.user.description}</p>
                </div>
            </div>
        </div>


        <div class="col-md-8 card mb-4">
            <h4 class="mt-3 mb-3" style="font-family: Clab_Bold, sans-serif">Skills</h4>

            <c:choose>
            <c:when test="${not empty skills}">
            <c:forEach var="skill" items="${skills}" varStatus="status">
            <div class="skill-container">
                <c:set var="curColor" value="color-${status.index + 1}" />
                <c:set var="curDescription" value="description-${status.index + 1}" />
                <div class="skill-container">
                    <a href="users/skills?user=${requestScope.user.username}&skill=${skill.id}">
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
            </div>
            </c:forEach>
                <div class="mb-3" style="text-align: center">
                    <a class="message" href="http://localhost:8080/SemesterWork/users/skills?user=${requestScope.user.username}">see more</a>
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

