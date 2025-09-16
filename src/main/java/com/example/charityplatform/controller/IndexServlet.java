package controller;

import dao.EventDAO;
import dao.EventParticipantDAO;  // 你需要寫一個 DAO 來取得已參加的活動ID
import model.Event;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Integer userId = null;
        if (session != null) {
            userId = (Integer) session.getAttribute("userId");
        }

        try (Connection conn = DBUtil.getConnection()) {
            EventDAO eventDAO = new EventDAO(conn);
            List<Event> events = eventDAO.getAllEvents();
            request.setAttribute("eventList", events);

            if (userId != null) {
                EventParticipantDAO participantDAO = new EventParticipantDAO(conn);
                List<Integer> joinedEventIds = participantDAO.getJoinedEventIdsByUserId(userId);
                request.setAttribute("joinedEventIds", joinedEventIds);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "載入活動失敗");
        }

        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
