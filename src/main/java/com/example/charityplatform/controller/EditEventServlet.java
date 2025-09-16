package controller;

import dao.EventDAO;
import model.Event;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/charity/edit_event")
public class EditEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("charityId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        Integer charityId = (Integer) session.getAttribute("charityId");

        String eventIdStr = request.getParameter("eventId");
        if (eventIdStr == null || eventIdStr.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/charity/events");
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(eventIdStr);
        } catch (NumberFormatException e) {
            response.sendRedirect(request.getContextPath() + "/charity/events");
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            EventDAO eventDAO = new EventDAO(conn);

            // 判斷該活動是否屬於此 charityId
            if (!eventDAO.isEventOwnedByCharity(eventId, charityId)) {
                // 無權限存取
                response.sendRedirect(request.getContextPath() + "/charity/events");
                return;
            }

            Event event = eventDAO.getEventById(eventId);
            if (event == null) {
                response.sendRedirect(request.getContextPath() + "/charity/events");
                return;
            }

            request.setAttribute("event", event);
            request.getRequestDispatcher("/charity/edit_event.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/charity/events");
        }
    }
}
