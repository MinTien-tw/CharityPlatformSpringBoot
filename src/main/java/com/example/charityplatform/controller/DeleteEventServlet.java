package controller;

import dao.EventDAO;
import model.Event;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/charity/DeleteEventServlet")
public class DeleteEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(DeleteEventServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null 
            || !"charity".equals(session.getAttribute("accountType")) 
            || session.getAttribute("charityId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String eventIdStr = request.getParameter("eventId");
        if (eventIdStr == null) {
            session.setAttribute("errorMsg", "活動編號不可為空！");
            response.sendRedirect(request.getContextPath() + "/charity/dashboard");
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(eventIdStr);
        } catch (NumberFormatException e) {
            session.setAttribute("errorMsg", "活動編號格式錯誤！");
            response.sendRedirect(request.getContextPath() + "/charity/dashboard");
            return;
        }

        Integer charityId = (Integer) session.getAttribute("charityId");

        try (Connection conn = DBUtil.getConnection()) {
            EventDAO eventDAO = new EventDAO(conn);

            // 你可以新增 eventDAO.hasPermission(eventId, charityId) 來判斷權限，這裡示範用 getEventById
            Event event = eventDAO.getEventById(eventId);
            if (event != null && charityId.equals(event.getCharityId())) {
                eventDAO.deleteEventAndParticipants(eventId);
                session.setAttribute("successMsg", "活動與參與者成功刪除！");
            } else {
                session.setAttribute("errorMsg", "無權限刪除此活動！");
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, "刪除活動時發生錯誤", e);
            session.setAttribute("errorMsg", "刪除活動時發生錯誤！");
        }

        response.sendRedirect(request.getContextPath() + "/charity/dashboard");
    }

    // 如非要支持 GET 也可寫，且避免直接刪除
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.sendError(405); // Method Not Allowed
    }
}
