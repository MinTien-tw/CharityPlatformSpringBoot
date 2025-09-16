<%@ page session="true" contentType="text/html; charset=UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-Hant">

    <head>
        <meta charset="UTF-8" />
        <title>歡迎頁</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>

    <body class="container mt-5">
        <div class="text-center">
            <h2>歡迎回來，<strong>${sessionScope.account}</strong>！</h2>
            <p>您是
                <c:choose>
                    <c:when test="${sessionScope.accountType == 'user'}">一般會員</c:when>
                    <c:when test="${sessionScope.accountType == 'charity'}">慈善團體</c:when>
                    <c:otherwise>訪客</c:otherwise>
                </c:choose>
            </p>

            <div class="d-flex justify-content-center gap-3 mt-4">
                <a href="logout.jsp" class="btn btn-danger">登出</a>
                <a href="${pageContext.request.contextPath}/index" class="btn btn-secondary">回到首頁</a>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>