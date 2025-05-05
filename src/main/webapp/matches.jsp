<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tennis Scoreboard | Finished Matches</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/style.css">

    <script src="js/app.js"></script>
</head>

<body>
<header class="header">
    <section class="nav-header">
        <div class="brand">
            <div class="nav-toggle">
                <img src="images/menu.png" alt="Logo" class="logo">
            </div>
            <span class="logo-text">TennisScoreboard</span>
        </div>
        <div>
            <nav class="nav-links">
                <a class="nav-link" href="index.jsp">Home</a>
                <a class="nav-link" href="matches?page=1">Matches</a>
            </nav>
        </div>
    </section>
</header>
<main>
    <div class="container">
        <h1>Matches</h1>
        <div class="input-container">
            <form method="get" action="${pageContext.request.contextPath}/matches" class="input-container">

                <input
                        class="input-filter"
                        name="filter_by_player_name"
                        placeholder="Filter by name"
                        type="text"
                        value="${param.filter_by_player_name}" />

                <input type="hidden" name="page" value="1" />

                <button class="btn-filter" type="submit">Apply Filter</button>
                <a href="${pageContext.request.contextPath}/matches?page=1"
                   class="btn-filter"
                   style="display: inline-block; text-decoration: none; padding: 10px 20px; background-color: #42aaff;
          color: white; border-radius: 20px; font-size: 17px; text-align: center; line-height: 1.5;">
                    Reset Filter
                </a>
            </form>
        </div>

        <table class="table-matches">
            <tr>
                <th>Player One</th>
                <th>Player Two</th>
                <th>Winner</th>
            </tr>
            <c:forEach var="match" items="${pagedMatches}">
                <tr>
                    <td>${match.player1.name}</td>
                    <td>${match.player2.name}</td>
                    <td><span class="winner-name-td">${match.winner.name}</span></td>
                </tr>
            </c:forEach>
        </table>

        <div class="pagination">
            <a class="prev"
               href="?page=${pageNumber - 1}&filter_by_player_name=${param.filter_by_player_name}"
               <c:if test="${pageNumber == 1}">style="pointer-events:none;opacity:0.4;"</c:if>> &lt; </a>

            <c:if test="${startPage > 1}">
                <a class="num-page"
                   href="?page=1&filter_by_player_name=${param.filter_by_player_name}">1</a>
                <span>...</span>
            </c:if>

            <c:forEach var="p" begin="${startPage}" end="${endPage}">
                <a class="num-page ${p == pageNumber ? 'current' : ''}"
                   href="?page=${p}&filter_by_player_name=${param.filter_by_player_name}">${p}</a>
            </c:forEach>

            <c:if test="${endPage < totalPagesCount}">
                <span>...</span>
                <a class="num-page"
                   href="?page=${totalPagesCount}&filter_by_player_name=${param.filter_by_player_name}">
                        ${totalPagesCount}
                </a>
            </c:if>

            <a class="next"
               href="?page=${pageNumber + 1}&filter_by_player_name=${param.filter_by_player_name}"
               <c:if test="${pageNumber == totalPagesCount}">style="pointer-events:none;opacity:0.4;"</c:if>> &gt; </a>
        </div>
    </div>
</main>
<footer>
    <div class="footer">
        <p>&copy; Tennis Scoreboard, project from <a href="https://zhukovsd.github.io/java-backend-learning-course/">zhukovsd/java-backend-learning-course</a>
            roadmap.</p>
    </div>
</footer>
</body>
</html>