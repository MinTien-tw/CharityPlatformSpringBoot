package controller;

import dao.EventDAO;
import model.Event;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/charity/dashboard")
public class CharityDashboardServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(CharityDashboardServlet.class.getName());

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null 
            || !"charity".equals(session.getAttribute("accountType")) 
            || session.getAttribute("charityId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        Integer charityId = (Integer) session.getAttribute("charityId");

        try (Connection conn = DBUtil.getConnection()) {
            EventDAO eventDAO = new EventDAO(conn);
            List<Event> eventList = eventDAO.getEventByCharityId(charityId);

            request.setAttribute("eventList", eventList);
            request.getRequestDispatcher("/charity_dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "無法取得活動資料", e);
            // 可改成轉發錯誤頁面，這邊示範直接 500
            response.sendError(500, "無法取得活動資料");
        }
    }
}
