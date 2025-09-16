<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

            <!DOCTYPE html>
            <html lang="zh-Hant">

            <head>
                <meta charset="UTF-8" />
                <title>編輯公益活動</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
                <style>
                    body {
                        background: linear-gradient(135deg, #e0f7fa, #80deea);
                        min-height: 100vh;
                    }

                    .card {
                        margin-top: 60px;
                        box-shadow: 0 8px 24px rgba(0, 0, 0, 0.12);
                        border-radius: 12px;
                    }

                    h2 {
                        color: #00695c;
                        font-weight: 700;
                    }

                    .btn-primary {
                        background-color: #00695c;
                        border: none;
                    }

                    .btn-primary:hover {
                        background-color: #004d40;
                    }

                    label {
                        font-weight: 600;
                        color: #004d40;
                    }

                    /* Debug 訊息區 */
                    #debug {
                        color: red;
                        margin: 10px auto;
                        max-width: 600px;
                        font-weight: bold;
                        text-align: center;
                    }
                </style>
            </head>

            <body>
                <div class="container">
                    <div class="card p-4 mx-auto" style="max-width: 600px;">
                        <h2 class="text-center mb-4">編輯公益活動</h2>

                        <c:if test="${not empty errorMsg}">
                            <div class="alert alert-danger text-center fw-semibold">${errorMsg}</div>
                        </c:if>

                        <form action="${pageContext.request.contextPath}/charity/update_event" method="post">
                            <input type="hidden" name="eventId" value="${event.eventId}" />

                            <div class="mb-3">
                                <label for="eventName" class="form-label">活動名稱</label>
                                <input type="text" id="eventName" name="eventName" class="form-control"
                                    value="${event.eventName}" required />
                            </div>

                            <div class="row g-3 mb-3">
                                <div class="col-md-6">
                                    <label for="eventDate" class="form-label">活動日期</label>
                                    <input type="date" id="eventDate" name="eventDate" class="form-control"
                                        value="${event.eventDate}" required />
                                </div>

                                <div class="col-md-3">
                                    <label for="eventStart" class="form-label">活動開始時間</label>
                                    <c:choose>
                                        <c:when test="${not empty event.eventStart}">
                                            <input type="time" id="eventStart" name="eventStart" class="form-control"
                                                value="${fn:substring(event.eventStart.toString(), 0, 5)}" required />
                                        </c:when>
                                        <c:otherwise>
                                            <input type="time" id="eventStart" name="eventStart" class="form-control"
                                                required />
                                        </c:otherwise>
                                    </c:choose>
                                </div>

                                <div class="col-md-3">
                                    <label for="eventEnd" class="form-label">活動結束時間</label>
                                    <c:choose>
                                        <c:when test="${not empty event.eventEnd}">
                                            <input type="time" id="eventEnd" name="eventEnd" class="form-control"
                                                value="${fn:substring(event.eventEnd.toString(), 0, 5)}" required />
                                        </c:when>
                                        <c:otherwise>
                                            <input type="time" id="eventEnd" name="eventEnd" class="form-control"
                                                required />
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="eventLocation" class="form-label">活動地點</label>
                                <input type="text" id="eventLocation" name="eventLocation" class="form-control"
                                    value="${event.eventLocation}" required />
                            </div>

                            <div class="mb-3">
                                <label for="eventDescription" class="form-label">活動說明</label>
                                <textarea id="eventDescription" name="eventDescription" class="form-control" rows="5"
                                    placeholder="請輸入活動說明..."><c:out value="${event.eventDescription}" /></textarea>
                            </div>

                            <button type="submit" class="btn btn-primary w-100 py-2 fw-bold">更新活動</button>
                        </form>
                    </div>
                </div>



                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

            </body>

            </html>