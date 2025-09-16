package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import util.DBUtil;

@WebServlet("/CancelJoinEventServlet")
public class CancelJoinEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String eventIdStr = request.getParameter("eventId");
        if (eventIdStr == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(eventIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String deleteSql = "DELETE FROM EventParticipants WHERE UserID = ? AND EventID = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                ps.setInt(1, userId);
                ps.setInt(2, eventId);
                int rows = ps.executeUpdate();

                if (rows > 0) {
                    session.setAttribute("successMsg", "已成功取消報名該活動");
                } else {
                    session.setAttribute("errorMsg", "取消報名失敗，找不到報名資料");
                }
            }
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "系統錯誤：" + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
    }
}
