<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List,model.Event" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

                <!DOCTYPE html>
                <html lang="zh-Hant">

                <head>
                    <meta charset="UTF-8" />
                    <title>我的公益活動</title>
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
                        rel="stylesheet" />
                    <style>
                        body {
                            background-color: #f0f2f5;
                        }

                        h2 {
                            font-weight: 600;
                            color: #2c3e50;
                        }

                        .card {
                            border: none;
                        }

                        .btn-primary {
                            background-color: #3498db;
                            border-color: #3498db;
                        }

                        .btn-primary:hover {
                            background-color: #2980b9;
                        }

                        .btn-success {
                            background-color: #27ae60;
                            border-color: #27ae60;
                        }

                        .btn-success:hover {
                            background-color: #1e8449;
                        }

                        .table th,
                        .table td {
                            vertical-align: middle;
                        }

                        .no-activity {
                            font-size: 1.1rem;
                            color: #888;
                        }

                        .action-buttons a {
                            margin-right: 5px;
                        }

                        /* 讓說明欄位可以換行 */
                        .description-cell {
                            white-space: pre-wrap;
                            text-align: left;
                        }
                    </style>
                </head>

                <body>

                    <div class="container py-5">
                        <div class="d-flex justify-content-between align-items-center mb-4">
                            <h2>我的公益活動清單</h2>
                            <a href="${pageContext.request.contextPath}/charity/add_event.jsp" class="btn btn-success">
                                ➕ 新增活動
                            </a>
                        </div>

                        <div class="card shadow-sm">
                            <div class="card-body p-4">
                                <c:if test="${not empty eventList}">
                                    <table class="table table-hover table-bordered text-center">
                                        <thead class="table-primary">
                                            <tr>
                                                <th scope="col">活動名稱</th>
                                                <th scope="col">日期</th>
                                                <th scope="col">地點</th>
                                                <th scope="col">說明</th>
                                                <th scope="col">操作</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="event" items="${eventList}">
                                                <tr>
                                                    <td>${fn:escapeXml(event.eventName)}</td>
                                                    <td>
                                                        <fmt:formatDate value="${event.eventDate}"
                                                            pattern="yyyy-MM-dd" />
                                                    </td>
                                                    <td>${fn:escapeXml(event.eventLocation)}</td>
                                                    <td class="description-cell">${fn:escapeXml(event.eventDescription)}
                                                    </td>
                                                    <td class="action-buttons">
                                                        <a href="${pageContext.request.contextPath}/charity/edit_event.jsp?eventId=${event.eventId}"
                                                            class="btn btn-sm btn-primary">修改</a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </c:if>

                                <c:if test="${empty eventList}">
                                    <div class="text-center no-activity py-4">目前沒有任何公益活動</div>
                                </c:if>

                                <!-- 回首頁按鈕 -->
                                <div class="text-center mt-4">
                                    <a href="${pageContext.request.contextPath}/index"
                                        class="btn btn-outline-secondary">
                                        ⬅ 回到首頁
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
                </body>

                </html>