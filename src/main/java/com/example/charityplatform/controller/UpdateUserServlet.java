package controller;

import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession(false);

        String account = (session != null) ? (String) session.getAttribute("account") : null;

        if (account == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String newPassword = request.getParameter("newPassword");
        String newPhone = request.getParameter("newPhone");
        String newAddress = request.getParameter("newAddress");

        // 基本驗證
        if (newPassword == null || newPassword.isBlank() ||
            newPhone == null || newPhone.isBlank() ||
            newAddress == null || newAddress.isBlank()) {

            request.setAttribute("errorMsg", "所有欄位皆為必填，請確認填寫！");
            request.getRequestDispatcher("update_profile.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE Users SET Password = ?, Phone = ?, Address = ? WHERE UserAccount = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, newPassword);
                ps.setString(2, newPhone);
                ps.setString(3, newAddress);
                ps.setString(4, account);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    session.setAttribute("successMsg", "資料更新成功！");
                    response.sendRedirect("updateSuccess.jsp");
                } else {
                    request.setAttribute("errorMsg", "更新失敗，帳號不存在或資料未變更！");
                    request.getRequestDispatcher("update_profile.jsp").forward(request, response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "系統錯誤：" + e.getMessage());
            request.getRequestDispatcher("update_profile.jsp").forward(request, response);
        }
    }
}
