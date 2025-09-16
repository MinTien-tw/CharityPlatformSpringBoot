package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import util.DBUtil;

@WebServlet("/charity/AddEventServlet")
public class AddEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        HttpSession session = request.getSession();
        Integer charityId = (Integer) session.getAttribute("charityId");

        if (charityId == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String eventName = request.getParameter("eventName");
        String eventDescription = request.getParameter("eventDescription");
        String eventLocation = request.getParameter("eventLocation");
        String eventDateStr = request.getParameter("eventDate");
        String eventStartStr = request.getParameter("eventStart");
        String eventEndStr = request.getParameter("eventEnd");

        // 轉換日期與時間，並檢查格式
        Date eventDate = null;
        Time eventStart = null;
        Time eventEnd = null;

        try {
            if (eventDateStr != null && !eventDateStr.trim().isEmpty()) {
                eventDate = Date.valueOf(eventDateStr);  // yyyy-MM-dd
            } else {
                throw new IllegalArgumentException("活動日期不可為空");
            }
            if (eventStartStr != null && !eventStartStr.trim().isEmpty()) {
                eventStart = Time.valueOf(eventStartStr + ":00"); // HH:mm:ss
            } else {
                throw new IllegalArgumentException("活動開始時間不可為空");
            }
            if (eventEndStr != null && !eventEndStr.trim().isEmpty()) {
                eventEnd = Time.valueOf(eventEndStr + ":00"); // HH:mm:ss
            } else {
                throw new IllegalArgumentException("活動結束時間不可為空");
            }
        } catch (IllegalArgumentException e) {
            request.setAttribute("errorMsg", "日期或時間格式錯誤或空白：" + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/add_event.jsp").forward(request, response);
            return;
        }

        // 新增資料庫指令，包含開始和結束時間
        String sql = "INSERT INTO Events (CharitiesID, EventName, EventDescription, EventDate, Location, EventStart, EventEnd) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, charityId);
            ps.setString(2, eventName);
            ps.setString(3, eventDescription);
            ps.setDate(4, eventDate);
            ps.setString(5, eventLocation);
            ps.setTime(6, eventStart);
            ps.setTime(7, eventEnd);

            ps.executeUpdate();

            session.setAttribute("successMsg", "新增活動成功！");
            response.sendRedirect(request.getContextPath() + "/charity/events");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "新增活動時發生錯誤！");
            request.getRequestDispatcher("/WEB-INF/add_event.jsp").forward(request, response);
        }
    }
}
