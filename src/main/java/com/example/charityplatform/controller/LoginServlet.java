package controller;

import util.AccountDAO;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String account = request.getParameter("account");
        String password = request.getParameter("password");
        String accountType = request.getParameter("accountType");

        String redirect = request.getParameter("redirect");
        String eventId = request.getParameter("eventId");

        // 簡單參數檢查
        if (account == null || password == null || accountType == null) {
            request.setAttribute("errorMsg", "請輸入完整帳號密碼與帳號類型");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            AccountDAO accountDAO = new AccountDAO(conn);

            boolean valid = false;
            if ("charity".equals(accountType)) {
                valid = accountDAO.validateCharity(account, password);
            } else if ("user".equals(accountType)) {
                valid = accountDAO.validateUser(account, password);
            }

            if (valid) {
                HttpSession session = request.getSession();
                session.setAttribute("accountType", accountType);
                session.setAttribute("account", account);

                if ("charity".equals(accountType)) {
                    int charityId = accountDAO.getCharityIdByAccount(account);
                    session.setAttribute("charityId", charityId);
                    // 導向慈善團體後台 Servlet
                    response.sendRedirect(request.getContextPath() + "/charity/dashboard");
                } else { // 一般使用者
                    int userId = accountDAO.getUserIdByAccount(account);
                    session.setAttribute("userId", userId);

                    if ("joinEvent".equals(redirect) && eventId != null) {
                        response.sendRedirect(request.getContextPath() + "/JoinEventServlet?eventId=" + eventId);
                    } else {
                        response.sendRedirect(request.getContextPath() + "/events.jsp");
                    }
                }
            } else {
                request.setAttribute("errorMsg", "帳號或密碼錯誤");
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "系統錯誤，請稍後再試");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
    }
}
