<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ page session="true" %>

            <!DOCTYPE html>
            <html lang="zh-Hant">

            <head>
                <meta charset="UTF-8" />
                <title>公益活動首頁</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
                <style>
                    body {
                        font-family: "微軟正黑體", Arial, sans-serif;
                        background-color: #f7f9fc;
                        color: #333;
                        min-height: 100vh;
                    }

                    .card {
                        min-height: 280px;
                        display: flex;
                        flex-direction: column;
                        justify-content: space-between;
                    }

                    .card-title {
                        font-weight: 700;
                        font-size: 1.25rem;
                        color: #34495e;
                    }

                    .card-text strong {
                        color: #555;
                    }

                    .navbar-custom {
                        margin-bottom: 30px;
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        gap: 15px;
                        flex-wrap: wrap;
                    }

                    .navbar-custom>div {
                        display: flex;
                        gap: 10px;
                        align-items: center;
                    }

                    .card-text {
                        max-height: 100px;
                        overflow: hidden;
                        text-overflow: ellipsis;
                    }

                    form>button {
                        width: 100%;
                    }
                </style>
            </head>

            <body>
                <div class="container mt-4">

                    <!-- 標題 -->
                    <h1 class="text-center">公益活動平台</h1>

                    <!-- 成功 / 失敗訊息 -->
                    <c:if test="${not empty sessionScope.successMsg}">
                        <div class="alert alert-success text-center">${sessionScope.successMsg}</div>
                        <c:remove var="successMsg" scope="session" />
                    </c:if>
                    <c:if test="${not empty sessionScope.errorMsg}">
                        <div class="alert alert-danger text-center">${sessionScope.errorMsg}</div>
                        <c:remove var="errorMsg" scope="session" />
                    </c:if>

                    <!-- 導覽列 -->
                    <div class="navbar-custom">
                        <div>
                            <c:choose>
                                <c:when test="${not empty sessionScope.account}">
                                    <span>您好，<strong>${sessionScope.account}</strong>（
                                        <c:choose>
                                            <c:when test="${sessionScope.accountType == 'user'}">一般會員</c:when>
                                            <c:when test="${sessionScope.accountType == 'charity'}">慈善團體</c:when>
                                            <c:otherwise>訪客</c:otherwise>
                                        </c:choose>
                                        ）
                                    </span>
                                </c:when>
                                <c:otherwise>
                                    <span>您好，訪客</span>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div>
                            <c:choose>
                                <c:when test="${not empty sessionScope.account}">
                                    <a href="${pageContext.request.contextPath}/updateUser.jsp"
                                        class="btn btn-outline-primary btn-sm">修改會員資料</a>
                                    <a href="${pageContext.request.contextPath}/logout.jsp"
                                        class="btn btn-outline-danger btn-sm">登出</a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/login.jsp"
                                        class="btn btn-primary btn-sm">登入</a>
                                    <a href="${pageContext.request.contextPath}/register.jsp"
                                        class="btn btn-success btn-sm">註冊</a>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <!-- 慈善團體專用按鈕 -->
                    <c:if test="${sessionScope.accountType == 'charity'}">
                        <div class="text-end mb-4">
                            <a href="${pageContext.request.contextPath}/charity/events" class="btn btn-warning fw-bold">
                                ✏️ 前往管理我的公益活動
                            </a>
                        </div>
                    </c:if>

                    <!-- 活動簡介 -->
                    <p class="lead text-center mb-5">
                        歡迎參與公益活動！支持社會善舉，一起改變世界。
                    </p>

                    <!-- 活動清單 -->
                    <div class="row">
                        <c:if test="${not empty eventList}">
                            <c:forEach var="event" items="${eventList}">
                                <div class="col-md-6 mb-4">
                                    <div class="card shadow-sm">
                                        <div class="card-body d-flex flex-column">
                                            <h5 class="card-title">${event.eventName}</h5>
                                            <p class="card-text">
                                                <strong>日期：</strong>${event.eventDate} <br />
                                                <strong>地點：</strong>${event.eventLocation} <br />
                                                <strong>說明：</strong>${event.eventDescription}
                                            </p>

                                            <!-- 一般會員可報名 / 取消 -->
                                            <c:if test="${sessionScope.accountType == 'user'}">
                                                <c:set var="joined" value="false" />
                                                <c:forEach var="id" items="${joinedEventIds}">
                                                    <c:if test="${id == event.eventId}">
                                                        <c:set var="joined" value="true" />
                                                    </c:if>
                                                </c:forEach>

                                                <div class="mt-auto">
                                                    <c:choose>
                                                        <c:when test="${joined}">
                                                            <span class="text-success fw-bold">✅ 已參加</span>
                                                            <form
                                                                action="${pageContext.request.contextPath}/CancelJoinEventServlet"
                                                                method="post" class="d-inline">
                                                                <input type="hidden" name="eventId"
                                                                    value="${event.eventId}" />
                                                                <button type="submit"
                                                                    class="btn btn-danger btn-sm ms-2">取消參加</button>
                                                            </form>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <form
                                                                action="${pageContext.request.contextPath}/JoinEventServlet"
                                                                method="post" class="d-grid gap-2">
                                                                <input type="hidden" name="eventId"
                                                                    value="${event.eventId}" />
                                                                <button type="submit"
                                                                    class="btn btn-primary btn-sm">我要參加</button>
                                                            </form>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </div>
                                            </c:if>

                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </c:if>

                        <c:if test="${empty eventList}">
                            <div class="col-12 text-center text-muted">
                                <p>目前尚無任何活動。</p>
                            </div>
                        </c:if>
                    </div>

                    <!-- 回首頁按鈕 -->
                    <div class="text-center mt-5">
                        <a href="${pageContext.request.contextPath}/index" class="btn btn-outline-secondary">回首頁</a>
                    </div>

                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>