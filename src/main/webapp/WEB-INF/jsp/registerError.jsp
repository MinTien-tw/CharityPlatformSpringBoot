<%@ page contentType="text/html; charset=UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-Hant">

    <head>
        <meta charset="UTF-8" />
        <title>註冊失敗</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>

    <body class="bg-light d-flex justify-content-center align-items-center vh-100">

        <div class="card p-4 shadow-sm" style="max-width: 500px; width: 100%;">
            <h2 class="text-danger mb-3">⚠️ 註冊失敗</h2>
            <p class="mb-4">錯誤訊息：<strong>${errorMsg}</strong></p>
            <a href="register.jsp" class="btn btn-primary">返回註冊頁</a>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>