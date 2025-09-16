<%@ page contentType="text/html; charset=UTF-8" session="true" %>
    <% session.invalidate(); // 清除 Session %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta charset="UTF-8" />
            <title>登出成功</title>
            <meta http-equiv="refresh" content="3; URL=${pageContext.request.contextPath}/index" />
        </head>

        <body>
            <h2>登出成功</h2>
            <p>您已成功登出，將在3秒後自動跳轉回首頁...</p>
            <p>如果沒有自動跳轉，請點<a href="${pageContext.request.contextPath}/index">這裡返回首頁</a></p>
        </body>

        </html>