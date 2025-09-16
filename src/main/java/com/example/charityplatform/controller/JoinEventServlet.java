package controller;

import dao.EventParticipantDAO;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/JoinEventServlet")
public class JoinEventServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String contextPath = request.getContextPath();

        if (session == null) {
            response.sendRedirect(contextPath + "/login.jsp");
            return;
        }

        Integer userId = (Integer) session.getAttribute("userId");
        String eventIdStr = request.getParameter("eventId");

        if (userId == null) {
            response.sendRedirect(contextPath + "/login.jsp?redirect=joinEvent&eventId=" + eventIdStr);
            return;
        }

        if (eventIdStr == null || eventIdStr.isEmpty()) {
            session.setAttribute("errorMsg", "活動ID不存在");
            response.sendRedirect(contextPath + "/index.jsp");
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(eventIdStr);
        } catch (NumberFormatException e) {
            session.setAttribute("errorMsg", "活動ID格式錯誤");
            response.sendRedirect(contextPath + "/index.jsp");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            EventParticipantDAO participantDAO = new EventParticipantDAO(conn);
            if (participantDAO.getJoinedEventIdsByUserId(userId).contains(eventId)) {
                session.setAttribute("errorMsg", "您已參加此活動！");
                response.sendRedirect(contextPath + "/index.jsp");
                return;
            }

            boolean success = participantDAO.joinEvent(userId, eventId);
            if (success) {
                session.setAttribute("successMsg", "成功報名活動！");
            } else {
                session.setAttribute("errorMsg", "報名活動失敗！");
            }

            response.sendRedirect(contextPath + "/index.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("errorMsg", "系統錯誤：" + e.getMessage());
            response.sendRedirect(contextPath + "/index.jsp");
        }
    }
}
