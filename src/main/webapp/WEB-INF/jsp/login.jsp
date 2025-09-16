<%@ page contentType="text/html; charset=UTF-8" language="java" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <!DOCTYPE html>
        <html lang="zh-Hant">

        <head>
            <meta charset="UTF-8" />
            <title>會員登入</title>
            <!-- Bootstrap 5 CSS -->
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
        </head>

        <body class="bg-light">

            <div class="container d-flex justify-content-center align-items-center min-vh-100">
                <div class="card shadow-lg" style="width: 100%; max-width: 400px;">
                    <div class="card-header bg-primary text-white text-center">
                        <h3 class="mb-0">會員登入</h3>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/LoginServlet" method="post">
                            <div class="mb-3">
                                <label for="account" class="form-label">帳號</label>
                                <input type="text" class="form-control" id="account" name="account" required />
                            </div>

                            <div class="mb-3">
                                <label for="password" class="form-label">密碼</label>
                                <input type="password" class="form-control" id="password" name="password" required />
                            </div>

                            <div class="mb-3">
                                <label for="accountType" class="form-label">帳號類型</label>
                                <select class="form-select" id="accountType" name="accountType" required>
                                    <option value="user">一般會員</option>
                                    <option value="charity">慈善團體</option>
                                </select>
                            </div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-success">登入</button>
                                <a href="${pageContext.request.contextPath}/index"
                                    class="btn btn-outline-primary">回到首頁</a>
                            </div>

                            <c:if test="${not empty errorMsg}">
                                <div class="alert alert-danger mt-3" role="alert">
                                    ${errorMsg}
                                </div>
                            </c:if>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Bootstrap 5 JS -->
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>