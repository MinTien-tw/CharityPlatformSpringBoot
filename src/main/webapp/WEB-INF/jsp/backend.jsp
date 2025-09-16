<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

            <!DOCTYPE html>
            <html lang="zh-Hant">

            <head>
                <meta charset="UTF-8" />
                <title>後台管理頁面</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
                <style>
                    body {
                        font-family: "Microsoft JhengHei", sans-serif;
                        background: #f4f7f8;
                        margin: 0;
                        padding: 0;
                        color: #333;
                    }

                    header {
                        background-color: #4CAF50;
                        color: white;
                        padding: 20px 40px;
                        text-align: center;
                        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                    }

                    main {
                        max-width: 900px;
                        margin: 40px auto;
                        background: white;
                        padding: 30px 40px;
                        border-radius: 8px;
                        box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                        position: relative;
                    }

                    h1 {
                        margin-top: 0;
                        font-weight: 700;
                    }

                    p.welcome {
                        font-size: 1.2em;
                        margin-bottom: 30px;
                        color: #555;
                    }

                    nav a {
                        display: inline-block;
                        margin-right: 20px;
                        text-decoration: none;
                        color: #4CAF50;
                        font-weight: 600;
                        border: 2px solid #4CAF50;
                        padding: 8px 16px;
                        border-radius: 5px;
                        transition: background-color 0.3s ease, color 0.3s ease;
                    }

                    nav a:hover {
                        background-color: #4CAF50;
                        color: white;
                    }

                    footer {
                        text-align: center;
                        color: #888;
                        margin: 50px 0 20px 0;
                        font-size: 0.9em;
                    }

                    .btn-group-bottom {
                        position: absolute;
                        bottom: 20px;
                        right: 40px;
                    }
                </style>
            </head>

            <body>

                <c:if test="${not empty sessionScope.message}">
                    <script>
                        alert('${fn:escapeXml(sessionScope.message)}');
                    </script>
                    <c:remove var="message" scope="session" />
                </c:if>

                <header>
                    <h1>慈善團體後台管理系統</h1>
                </header>

                <main>
                    <p class="welcome">歡迎進入慈善團體後台管理系統！</p>
                    <nav>
                        <a href="${pageContext.request.contextPath}/events.jsp">活動管理</a>
                        <a href="${pageContext.request.contextPath}/participants.jsp">參加者管理</a>
                        <a href="${pageContext.request.contextPath}/profile.jsp">個人資料</a>
                        <a href="${pageContext.request.contextPath}/logout.jsp">登出</a>
                    </nav>

                    <div class="btn-group-bottom d-flex gap-2">
                        <a href="${pageContext.request.contextPath}/index.jsp" class="btn btn-outline-primary">回首頁</a>
                        <a href="${pageContext.request.contextPath}/charity_events.jsp"
                            class="btn btn-outline-success">我的活動列表</a>
                    </div>
                </main>

                <footer>
                    &copy; 2025 慈善平台版權所有
                </footer>

            </body>

            </html>