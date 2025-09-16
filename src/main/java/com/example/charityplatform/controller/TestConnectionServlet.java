package controller;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import util.DBUtil;

@WebServlet("/testConnection")
public class TestConnectionServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/plain; charset=UTF-8"); // 設定回應格式與編碼

        try (Connection conn = DBUtil.getConnection()) {
            if (conn != null && !conn.isClosed()) {
                response.getWriter().println("資料庫連線成功（JNDI）！");
            } else {
                response.getWriter().println("連線失敗！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("錯誤：" + e.getMessage());
        }
    }
}
