<%@ page contentType="text/html; charset=UTF-8" %>
    <!DOCTYPE html>
    <html lang="zh-Hant">

    <head>
        <meta charset="UTF-8" />
        <title>新增公益活動</title>
        <!-- Bootstrap 5 CDN -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    </head>

    <body class="bg-light">

        <div class="container mt-5" style="max-width: 600px;">
            <h2 class="mb-4">新增公益活動</h2>

            <form action="${pageContext.request.contextPath}/charity/AddEventServlet" method="post">
                <div class="mb-3">
                    <label for="eventName" class="form-label">活動名稱</label>
                    <input type="text" id="eventName" name="eventName" class="form-control" required />
                </div>

                <div class="mb-3">
                    <label for="eventDate" class="form-label">活動日期</label>
                    <input type="date" id="eventDate" name="eventDate" class="form-control" required />
                </div>

                <div class="mb-3">
                    <label for="eventLocation" class="form-label">活動地點</label>
                    <input type="text" id="eventLocation" name="eventLocation" class="form-control" required />
                </div>

                <div class="mb-3">
                    <label for="eventDescription" class="form-label">活動內容</label>
                    <textarea id="eventDescription" name="eventDescription" rows="5" class="form-control"
                        required></textarea>
                </div>

                <button type="submit" class="btn btn-primary">新增活動</button>
                <a href="${pageContext.request.contextPath}/charity/dashboard" class="btn btn-secondary ms-2">回管理介面</a>
            </form>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    </body>

    </html>