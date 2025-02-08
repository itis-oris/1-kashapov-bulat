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
    <style>
        #popup-container {
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            width: 50%;
            padding: 20px;
            background: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
            display: none;
            z-index: 1050;
        }
    </style>
</head>
<body class="custom-bg-color custom-font">
<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a href="/SemesterWork/users">
            <img class="logotype" src="${pageContext.request.contextPath}/logo.png" alt="Логотип knowHOWnet">
        </a>
    </div>
    <div class="ms-auto" style="padding: 15px">
        <a href="${pageContext.request.contextPath}/logout">
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
                <a class="nav-link" href="${pageContext.request.contextPath}/home">Main</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/home/skills">Skills</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/home/lessons">Lessons</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="${pageContext.request.contextPath}/home/notifications"><u>Notifications</u></a>
            </li>
        </ul>
    </div>
</nav>

<div class="container mt-4">
    <div class="skill-container card p-4">
        <div class="d-flex justify-content-between align-items-center">
            <h1 class="custom-font-bold">Notifications</h1>
        </div>
        <c:choose>
            <c:when test="${not empty notifications}">
                <c:forEach var="notification" items="${notifications}">
                    <div class="skill-container">
                        <c:choose>
                            <c:when test="${notification.type == 'lesson_request'}">
                                <p>
                                    User
                                    <a href="${pageContext.request.contextPath}/users?user=${notification.from.username}" style="color: darkgray">${notification.from.username}</a>
                                    wants to sign up to your skill:
                                    <a href="${pageContext.request.contextPath}/home/skills?skill=${notification.skill.id}" style="color: darkgray">${notification.skill.name}</a>
                                </p>
                                <c:choose>
                                    <c:when test="${notification.status == 'unchecked'}">
                                        <div class="row">
                                            <div class="col text-center">
                                                <div id="popup-container">
                                                    <h5 class="text-center custom-font-bold">Your links</h5>
                                                    <form action="${pageContext.request.contextPath}/lesson_approve" method="post">
                                                        <input type="hidden" name="notification" value="${notification.id}">
                                                        <input type="hidden" name="accept" value="1">
                                                        <div class="mb-3">
                                                            <textarea class="form-control" rows="3" maxlength="100" name="type" placeholder="Отправьте пользователю ссылки на ресурсы, через которые с вами можно будет связаться" required></textarea>
                                                        </div>
                                                        <div class="text-center">
                                                            <button type="submit" class="btn btn-success custom-font-bold">Отправить</button>
                                                        </div>
                                                    </form>
                                                </div>
                                                <button type="button" class="btn btn-success" id="show-popup">Accept</button>
                                            </div>
                                            <div class="col text-center">
                                                <form action="${pageContext.request.contextPath}/lesson_approve" method="post">
                                                    <input type="hidden" name="notification" value="${notification.id}">
                                                    <input type="hidden" name="accept" value="0">
                                                    <button type="submit" class="btn btn-danger">Reject</button>
                                                </form>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:when test="${notification.status == 'approved'}">
                                        <button type="button" class="btn btn-success disabled">Accepted</button>
                                    </c:when>
                                    <c:when test="${notification.status == 'rejected'}">
                                        <button type="button" class="btn btn-danger disabled">Rejected</button>
                                    </c:when>
                                </c:choose>
                            </c:when>
                            <c:when test="${notification.type == 'accepted'}">
                                <p style="color: darkgreen">
                                    User
                                    <a href="${pageContext.request.contextPath}/users?user=${notification.from.username}" style="color: darkgray">${notification.from.username}</a>
                                    accepted your request, skill:
                                    <a href="${pageContext.request.contextPath}/users/skills?user=${notification.from.username}&skill=${notification.skill.id}" style="color: darkgray">${notification.skill.name}</a>
                                </p>
                                <p>His links: ${notification.message}</p>
                            </c:when>
                            <c:when test="${notification.type == 'rejected'}">
                                <p style="color: darkred">
                                    User
                                    <a href="${pageContext.request.contextPath}/users?user=${notification.from.username}" style="color: darkgray">${notification.from.username}</a>
                                    rejected your request, skill:
                                    <a href="${pageContext.request.contextPath}/users/skills?user=${notification.from.username}&skill=${notification.skill.id}" style="color: darkgray">${notification.skill.name}</a>
                                </p>
                            </c:when>
                        </c:choose>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <div class="null message">
                    No notifications so far...
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="pagination-container text-center">
        <nav aria-label="Page navigation example">
            <ul class="pagination justify-content-center mb-0">
                <li class="page-item ${pageNum == 1 ? 'disabled' : ''}">
                    <a class="black-color page-link" href="${pageContext.request.contextPath}/home/notifications?page=${pageNum - 1}">&laquo;</a>
                </li>
                <c:forEach var="i" begin="1" end="${numOfPages}">
                    <li class="page-item ${i == pageNum ? 'active' : ''}">
                        <a class="page-link custom-active" href="${pageContext.request.contextPath}/home/notifications?page=${i}">${i}</a>
                    </li>
                </c:forEach>
                <li class="page-item ${pageNum == numOfPages ? 'disabled' : ''}">
                    <a class="black-color page-link" href="${pageContext.request.contextPath}/home/notifications?page=${pageNum + 1}">&raquo;</a>
                </li>
            </ul>
        </nav>
    </div>
</div>
<script>
    document.getElementById('show-popup').addEventListener('click', function() {
        document.getElementById('popup-container').style.display = 'block';
    });
</script>
</body>
</html>
