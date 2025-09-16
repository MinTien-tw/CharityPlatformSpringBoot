<!-- updateSuccess.jsp -->
<%@ page contentType="text/html; charset=UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-Hant">

    <head>
        <meta charset="UTF-8" />
        <title>更新成功</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>

    <body class="bg-light d-flex justify-content-center align-items-center vh-100">
        <div class="card border-success shadow-sm" style="max-width: 400px; width: 100%;">
            <div class="card-header bg-success text-white text-center">
                <h4 class="mb-0">資料更新成功！</h4>
            </div>
            <div class="card-body text-center">
                <a href="${pageContext.request.contextPath}/index" class="btn btn-primary">回到首頁</a>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>