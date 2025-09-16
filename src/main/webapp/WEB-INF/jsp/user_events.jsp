<%@ page contentType="text/html;charset=UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

        <!DOCTYPE html>
        <html lang="zh-Hant">

        <head>
            <meta charset="UTF-8" />
            <title>參加公益活動</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
            <style>
                .joined-label {
                    color: #28a745;
                    font-weight: bold;
                }
            </style>
            <script>
                function disableButton(btn) {
                    btn.disabled = true;
                    btn.form.submit();
                }
            </script>
        </head>

        <body class="container mt-4">

            <h2 class="mb-4 text-center">目前所有公益活動</h2>

            <c:if test="${not empty eventList}">
                <table class="table table-bordered table-hover text-center align-middle">
                    <thead class="table-light">
                        <tr>
                            <th>名稱</th>
                            <th>時間</th>
                            <th>地點</th>
                            <th>說明</th>
                            <th>操作</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="event" items="${eventList}">
                            <tr>
                                <td>${event.eventName}</td>
                                <td>${event.eventDate}</td>
                                <td>${event.eventLocation}</td>
                                <td>${event.eventDescription}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${joinedIds contains event.eventId}">
                                            <span class="joined-label">✔ 已參加</span>
                                        </c:when>
                                        <c:otherwise>
                                            <form method="post"
                                                action="${pageContext.request.contextPath}/user/joinEvent"
                                                onsubmit="disableButton(this.querySelector('button'));">
                                                <input type="hidden" name="eventId" value="${event.eventId}" />
                                                <button type="submit" class="btn btn-sm btn-primary">參加</button>
                                            </form>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:if>

            <c:if test="${empty eventList}">
                <div class="alert alert-info text-center">目前沒有任何公益活動可供參加。</div>
            </c:if>

            <div class="mt-4 text-center">
                <a href="${pageContext.request.contextPath}/index" class="btn btn-secondary">回到首頁</a>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

        </body>

        </html>