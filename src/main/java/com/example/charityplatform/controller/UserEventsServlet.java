package controller;

import dao.EventDAO;
import dao.EventParticipantDAO;
import model.Event;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/user/events") // 一般會員活動列表
public class UserEventsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = (session != null) ? (Integer) session.getAttribute("userId") : null;

        if (userId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            EventDAO eventDAO = new EventDAO(conn);
            EventParticipantDAO participantDAO = new EventParticipantDAO(conn);

            List<Event> eventList = eventDAO.getAllEvents();
            List<Integer> joinedIds = participantDAO.getJoinedEventIdsByUserId(userId);

            request.setAttribute("eventList", eventList);
            request.setAttribute("joinedEventIds", joinedIds);
            request.getRequestDispatcher("/events.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "活動載入失敗：" + e.getMessage());
            request.getRequestDispatcher("/events.jsp").forward(request, response);
        }
    }
}
