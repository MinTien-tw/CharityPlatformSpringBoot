<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

                <!DOCTYPE html>
                <html lang="zh-Hant">

                <head>
                    <meta charset="UTF-8" />
                    <title>所有公益活動</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
                        rel="stylesheet" />
                </head>

                <body class="container mt-4">
                    <h2 class="mb-4">所有公益活動</h2>

                    <c:if test="${not empty eventList}">
                        <table class="table table-bordered align-middle text-center">
                            <thead class="table-light">
                                <tr>
                                    <th>活動名稱</th>
                                    <th>日期</th>
                                    <th>地點</th>
                                    <th>說明</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach var="event" items="${eventList}">
                                    <c:set var="joined" value="false" />
                                    <c:forEach var="id" items="${joinedEventIds}">
                                        <c:if test="${id == event.eventId}">
                                            <c:set var="joined" value="true" />
                                        </c:if>
                                    </c:forEach>

                                    <tr>
                                        <td>${fn:escapeXml(event.eventName)}</td>
                                        <td>
                                            <fmt:formatDate value="${event.eventDate}" pattern="yyyy-MM-dd" />
                                        </td>
                                        <td>${fn:escapeXml(event.eventLocation)}</td>
                                        <td>${fn:escapeXml(event.eventDescription)}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${joined}">
                                                    <span class="text-success fw-bold">已參加</span>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="${pageContext.request.contextPath}/JoinEventServlet"
                                                        method="post" style="margin:0;">
                                                        <input type="hidden" name="eventId" value="${event.eventId}" />
                                                        <button type="submit" class="btn btn-primary btn-sm">參加</button>
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
                        <p class="text-muted">目前尚無活動。</p>
                    </c:if>

                    <div class="mt-4 d-flex justify-content-center gap-3">
                        <c:choose>
                            <c:when test="${sessionScope.accountType == 'charity'}">
                                <a href="${pageContext.request.contextPath}/charity/events"
                                    class="btn btn-secondary">回到活動列表</a>
                            </c:when>
                            <c:otherwise>
                                <a href="${pageContext.request.contextPath}/index" class="btn btn-secondary">回到活動列表</a>
                            </c:otherwise>
                        </c:choose>

                        <a href="${pageContext.request.contextPath}/index" class="btn btn-outline-primary">回到首頁</a>
                    </div>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                </body>

                </html>