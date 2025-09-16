package controller;

import dao.EventDAO;
import model.Event;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;

@WebServlet("/charity/update_event")
public class UpdateEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        Integer charityId = (session != null) ? (Integer) session.getAttribute("charityId") : null;

        // 驗證是否為登入的慈善團體
        if (charityId == null || !"charity".equals(session.getAttribute("accountType"))) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 取得參數
        String eventIdStr = request.getParameter("eventId");
        String name = request.getParameter("eventName");
        String description = request.getParameter("eventDescription");
        String location = request.getParameter("eventLocation");
        String dateStr = request.getParameter("eventDate");
        String startStr = request.getParameter("eventStart");
        String endStr = request.getParameter("eventEnd");

        // 參數檢查
        if (eventIdStr == null || eventIdStr.isBlank()) {
            request.setAttribute("errorMsg", "缺少活動 ID，無法修改！");
            request.getRequestDispatcher("/charity/edit_event.jsp").forward(request, response);
            return;
        }

        int eventId;
        try {
            eventId = Integer.parseInt(eventIdStr);
        } catch (NumberFormatException e) {
            request.setAttribute("errorMsg", "活動 ID 格式錯誤！");
            request.getRequestDispatcher("/charity/edit_event.jsp").forward(request, response);
            return;
        }

        if (name == null || name.isBlank()) {
            request.setAttribute("errorMsg", "活動名稱不可為空！");
            request.getRequestDispatcher("/charity/edit_event.jsp").forward(request, response);
            return;
        }

        // 格式轉換
        Date date = null;
        Time startTime = null;
        Time endTime = null;
        try {
            if (dateStr != null && !dateStr.isBlank()) date = Date.valueOf(dateStr);
            if (startStr != null && !startStr.isBlank()) startTime = Time.valueOf(startStr + ":00");
            if (endStr != null && !endStr.isBlank()) endTime = Time.valueOf(endStr + ":00");
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMsg", "日期或時間格式錯誤！");
            request.getRequestDispatcher("/charity/edit_event.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            EventDAO eventDAO = new EventDAO(conn);

            Event originalEvent = eventDAO.getEventById(eventId);
            if (originalEvent == null || originalEvent.getCharityId() != charityId) {
                request.setAttribute("errorMsg", "無權限修改此活動！");
                request.getRequestDispatcher("/charity/edit_event.jsp").forward(request, response);
                return;
            }

            // 設定新資料
            originalEvent.setEventName(name);
            originalEvent.setEventDescription(description);
            originalEvent.setEventLocation(location);
            originalEvent.setEventDate(date);
            originalEvent.setEventStart(startTime);
            originalEvent.setEventEnd(endTime);

            boolean success = eventDAO.updateEvent(originalEvent);
            if (success) {
                session.setAttribute("successMsg", "活動修改成功！");
                response.sendRedirect(request.getContextPath() + "/charity/events");
            } else {
                request.setAttribute("errorMsg", "資料庫更新失敗！");
                request.setAttribute("event", originalEvent);
                request.getRequestDispatcher("/charity/edit_event.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "系統錯誤：" + e.getMessage());
            request.getRequestDispatcher("/charity/edit_event.jsp").forward(request, response);
        }
    }
}
