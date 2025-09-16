<%@ page contentType="text/html; charset=UTF-8" %>
    <%@ page session="true" %>
        <!DOCTYPE html>
        <html lang="zh-Hant">

        <head>
            <meta charset="UTF-8">
            <title>修改會員資料</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
            <script>
                function validateForm() {
                    const pw1 = document.getElementById('newPassword').value;
                    const pw2 = document.getElementById('confirmPassword').value;
                    if (pw1 !== pw2) {
                        alert('兩次輸入的密碼不一致，請重新輸入！');
                        return false;
                    }
                    return true;
                }
            </script>
        </head>

        <body class="bg-light">

            <div class="container mt-5">
                <div class="card shadow-sm mx-auto" style="max-width: 480px;">
                    <div class="card-header bg-primary text-white">
                        <h4 class="mb-0">修改會員資料</h4>
                    </div>
                    <div class="card-body">
                        <form action="${pageContext.request.contextPath}/UpdateUserServlet" method="post"
                            onsubmit="return validateForm()">
                            <div class="mb-3">
                                <label class="form-label">帳號（無法更改）</label>
                                <input type="text" class="form-control" value="${sessionScope.account}" readonly>
                            </div>

                            <div class="mb-3">
                                <label for="newPassword" class="form-label">新密碼</label>
                                <input type="password" class="form-control" id="newPassword" name="newPassword" required
                                    minlength="6" placeholder="請輸入至少6位數密碼">
                            </div>

                            <div class="mb-3">
                                <label for="confirmPassword" class="form-label">確認新密碼</label>
                                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                                    required minlength="6" placeholder="請再次輸入新密碼">
                            </div>

                            <div class="mb-3">
                                <label for="newPhone" class="form-label">新電話</label>
                                <input type="text" class="form-control" id="newPhone" name="newPhone"
                                    value="${sessionScope.phone != null ? sessionScope.phone : ''}" placeholder="選填">
                            </div>

                            <div class="mb-3">
                                <label for="newAddress" class="form-label">新地址</label>
                                <input type="text" class="form-control" id="newAddress" name="newAddress"
                                    value="${sessionScope.address != null ? sessionScope.address : ''}"
                                    placeholder="選填">
                            </div>

                            <div class="d-grid">
                                <button type="submit" class="btn btn-success">更新資料</button>
                            </div>
                        </form>
                    </div>
                    <div class="card-footer text-center">
                        <a href="${pageContext.request.contextPath}/index" class="btn btn-primary">回到首頁</a>
                    </div>
                </div>
            </div>

            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
        </body>

        </html>