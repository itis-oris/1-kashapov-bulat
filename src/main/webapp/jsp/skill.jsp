<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Home - knowHOWnet</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css">

    <link href="/SemesterWork/bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .rating-container {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
            width: 400px;
            z-index: 1050;
        }

        .stars {
            display: flex;
            justify-content: space-between;
            cursor: pointer;
        }

        .star {
            font-size: 30px;
            color: #ccc;
        }

        .star.selected {
            color: gold;
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
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <a href="/SemesterWork/home">
                    <svg fill="#000000" width="20px" height="20px" viewBox="0 0 24 24" xmlns="http://www.w3.org/2000/svg">
                        <path fill-rule="evenodd" d="M6.03531778,18.739764 C7.62329979,20.146176 9.71193925,21 12,21 C14.2880608,21 16.3767002,20.146176 17.9646822,18.739764 C17.6719994,17.687349 15.5693823,17 12,17 C8.43061774,17 6.32800065,17.687349 6.03531778,18.739764 Z M4.60050358,17.1246475 C5.72595131,15.638064 8.37060189,15 12,15 C15.6293981,15 18.2740487,15.638064 19.3994964,17.1246475 C20.4086179,15.6703183 21,13.9042215 21,12 C21,7.02943725 16.9705627,3 12,3 C7.02943725,3 3,7.02943725 3,12 C3,13.9042215 3.59138213,15.6703183 4.60050358,17.1246475 Z M12,23 C5.92486775,23 1,18.0751322 1,12 C1,5.92486775 5.92486775,1 12,1 C18.0751322,1 23,5.92486775 23,12 C23,18.0751322 18.0751322,23 12,23 Z M8,10 C8,7.75575936 9.57909957,6 12,6 C14.4141948,6 16,7.92157821 16,10.2 C16,13.479614 14.2180861,15 12,15 C9.76086382,15 8,13.4273743 8,10 Z M10,10 C10,12.2692568 10.8182108,13 12,13 C13.1777063,13 14,12.2983927 14,10.2 C14,8.95041736 13.2156568,8 12,8 C10.7337387,8 10,8.81582479 10,10 Z"/>
                    </svg>
                </a>
            </c:when>
            <c:otherwise>
                <a href="/SemesterWork/sign_in" class="black-color custom-font-bold">Sign_in</a>
            </c:otherwise>
        </c:choose>
    </div>
</nav>
<nav class="navbar navbar-expand-lg navbar-shadow bg-light">
    <div class="ms-auto me-auto" id="navbarNav">
        <ul class="navbar-nav">
            <li class="nav-item">
                <a class="nav-link" href="${pageContext.request.contextPath}/users?user=${user.username}">Main</a>
            </li>
            <li class="nav-item">
                <a class="nav-link active" href="${pageContext.request.contextPath}/users/skills?user=${user.username}">
                    <u>Skills</u>
                </a>
            </li>
        </ul>
    </div>
</nav>

<div class="container mt-4">
    <div class="skill-container card p-5">
        <div class="d-flex justify-content-between align-items-center">
            <h3 style="font-family: Clab_Bold, sans-serif">${skill.name}</h3>
            <form action="${pageContext.request.contextPath}/skill_sign_up" method="post">
                <input type="hidden" name="skill" value="${skill.id}">
                <input type="hidden" name="teacher" value="${user.id}">
                <input type="hidden" name="type" value="lesson_request">
                <button class="btn btn-success custom-font-bold ${sessionScope.user != null && sessionScope.user.skillpoints != 0 ? "" : "disabled"}">
                    Sign up
                </button>
            </form>
        </div>
        <div class="point-values">
            <span style="background-color: ${color}">${skill.rating}★</span>
        </div>
        <p>
            <span style="font-family: Clab_Bold, sans-serif">Category:</span>
            <span>${skill.category}</span>
        </p>
        <p>${skill.description}</p>
        <a class="custom-font-bold" style="color: #0b5ed7" href="/SemesterWork/users/skills/review?user=${requestScope.user.username}&skill=${requestScope.skill.id}"><u>See reviews<u></u></a>
    </div>
    <button id="openRatingBtn" class="btn btn-success mt-5 ml-3 ${requestScope.rateable == true ? "" : "disabled"}">Leave a review</button>
</div>
<div id="ratingForm" class="rating-container">
    <h3 class="text-center mb-4">Оцените наш сервис</h3>
    <form action="/SemesterWork/review" method="post">
        <div class="stars" id="stars">
            <span class="star" data-value="1">★</span>
            <span class="star" data-value="2">★</span>
            <span class="star" data-value="3">★</span>
            <span class="star" data-value="4">★</span>
            <span class="star" data-value="5">★</span>
        </div>

        <div class="form-group mt-3">
            <textarea maxlength="400" id="message" name="message" class="form-control" rows="4" placeholder="Ваш отзыв..." required></textarea>
            <small class="form-text text-muted">400 max length</small>
        </div>

        <input type="hidden" id="rating" name="rating" value="0">
        <input type="hidden" name="skill" value="${skill.id}">
        <input type="hidden" name="user" value="${requestScope.user.username}">
        <button type="submit" class="btn btn-success btn-block">Отправить</button>
    </form>
</div>
<script>

    document.getElementById('openRatingBtn').addEventListener('click', function() {
        document.getElementById('ratingForm').style.display = 'block';
    });


    const stars = document.querySelectorAll('.star');
    let selectedStars = 0;

    stars.forEach(star => {
        star.addEventListener('click', function() {
            selectedStars = parseInt(this.getAttribute('data-value'));
            stars.forEach(star => {
                if (parseInt(star.getAttribute('data-value')) <= selectedStars) {
                    star.classList.add('selected');
                } else {
                    star.classList.remove('selected');
                }
            });
            document.getElementById('rating').value = selectedStars;
        });
    });
</script>
</body>
</html>
