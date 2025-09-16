<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <!DOCTYPE html>
            <html>

            <head>
                <title>慈善團體管理介面</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
                <style>
                    /* 你的 CSS 保持不變 */
                </style>
            </head>

            <body>

                <div class="container-custom">

                    <!-- 成功或錯誤訊息 -->
                    <c:if test="${not empty sessionScope.successMsg}">
                        <div class="alert alert-success text-center">${sessionScope.successMsg}</div>
                        <c:remove var="successMsg" scope="session" />
                    </c:if>
                    <c:if test="${not empty sessionScope.errorMsg}">
                        <div class="alert alert-danger text-center">${sessionScope.errorMsg}</div>
                        <c:remove var="errorMsg" scope="session" />
                    </c:if>

                    <h2>歡迎回來，${sessionScope.account}！您是慈善團體</h2>

                    <div class="btn-nav">
                        <a href="${pageContext.request.contextPath}/index" class="btn btn-outline-primary">回到首頁</a>
                        <a href="${pageContext.request.contextPath}/charity/events"
                            class="btn btn-outline-primary">活動列表</a>
                    </div>

                    <c:if test="${sessionScope.accountType == 'charity'}">
                        <div class="mb-4 text-end">
                            <a href="${pageContext.request.contextPath}/charity/add_event.jsp"
                                class="btn btn-primary px-4 py-2">新增公益活動</a>
                        </div>
                    </c:if>

                    <table class="table table-bordered align-middle text-center">
                        <thead>
                            <tr>
                                <th>活動名稱</th>
                                <th>日期</th>
                                <th>地點</th>
                                <th>操作</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="event" items="${eventList}">
                                <tr>
                                    <td class="text-start">${event.eventName}</td>
                                    <td>
                                        <fmt:formatDate value="${event.eventDate}" pattern="yyyy-MM-dd" />
                                    </td>
                                    <td>${event.eventLocation}</td>
                                    <td>
                                        <div class="btn-group justify-content-center">
                                            <c:if test="${sessionScope.accountType == 'charity'}">
                                                <a
                                                    href="${pageContext.request.contextPath}/charity/edit_event?eventId=${event.eventId}">修改</a>


                                                <form
                                                    action="${pageContext.request.contextPath}/charity/DeleteEventServlet"
                                                    method="post" style="display:inline;"
                                                    onsubmit="return confirm('確定要刪除此活動嗎？');">
                                                    <input type="hidden" name="eventId" value="${event.eventId}" />
                                                    <button type="submit" class="btn btn-danger btn-sm">刪除</button>
                                                </form>
                                            </c:if>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                            <c:if test="${empty eventList}">
                                <tr>
                                    <td colspan="4" class="text-muted">目前尚無活動資料。</td>
                                </tr>
                            </c:if>
                        </tbody>
                    </table>
                </div>

            </body>

            </html>