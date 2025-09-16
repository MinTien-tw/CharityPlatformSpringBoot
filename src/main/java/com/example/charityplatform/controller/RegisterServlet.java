package controller;

import util.AccountDAO;
import util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/RegisterServlet")
@MultipartConfig(
    fileSizeThreshold = 1024 * 1024 * 1,  // 1MB
    maxFileSize = 1024 * 1024 * 10,       // 10MB
    maxRequestSize = 1024 * 1024 * 15     // 15MB
)
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String accountType = request.getParameter("accountType");
        if (accountType == null) {
            request.setAttribute("errorMsg", "請選擇帳號類型");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try (Connection conn = DBUtil.getConnection()) {
            AccountDAO accountDAO = new AccountDAO(conn);

            if ("user".equals(accountType)) {
                // 一般使用者註冊
                String userAccount = request.getParameter("userAccount");
                if (userAccount == null || userAccount.isBlank()) {
                    request.setAttribute("errorMsg", "帳號不可為空");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }
                if (accountDAO.isAccountExist(userAccount)) {
                    request.setAttribute("errorMsg", "此帳號已被使用，請更換其他帳號");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }

                String password = request.getParameter("password");
                String fullName = request.getParameter("fullName");
                String email = request.getParameter("email");
                String phone = request.getParameter("phone");
                String address = request.getParameter("address");
                String birthday = request.getParameter("birthday"); // yyyy-MM-dd
                String gender = request.getParameter("gender");

                String sql = "INSERT INTO Users (UserAccount, Password, FullName, Email, Phone, Address, Birthday, Gender) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, userAccount);
                    ps.setString(2, password);
                    ps.setString(3, fullName);
                    ps.setString(4, email);
                    ps.setString(5, phone);
                    ps.setString(6, address);
                    ps.setString(7, birthday);
                    ps.setString(8, gender);

                    ps.executeUpdate();
                }

            } else if ("charity".equals(accountType)) {
                // 慈善團體註冊
                String charityAccount = request.getParameter("charityAccount");
                if (charityAccount == null || charityAccount.isBlank()) {
                    request.setAttribute("errorMsg", "帳號不可為空");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }
                if (accountDAO.isAccountExist(charityAccount)) {
                    request.setAttribute("errorMsg", "此帳號已被使用，請更換其他帳號");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }

                String charityPassword = request.getParameter("charityPassword");
                String charityAddress = request.getParameter("charityAddress");
                String charityPhone = request.getParameter("charityPhone");
                String charityNo = request.getParameter("charityNo");

                Part filePart = request.getPart("charityFile");
                if (filePart == null || filePart.getSize() == 0) {
                    request.setAttribute("errorMsg", "請選擇要上傳的檔案");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }

                String uploadPath = getServletContext().getRealPath("/upload");
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdirs();
                }

                String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                if (fileName == null || fileName.isBlank()) {
                    request.setAttribute("errorMsg", "檔案名稱不合法");
                    request.getRequestDispatcher("register.jsp").forward(request, response);
                    return;
                }

                File savedFile = new File(uploadDir, fileName);
                try (var input = filePart.getInputStream();
                     var output = new java.io.FileOutputStream(savedFile)) {
                    input.transferTo(output);
                }

                String dbFilePath = "upload/" + fileName;

                String sql = "INSERT INTO Charities (CharitiesAccount, CharitiesPassword, CharitiesAddress, CharitiesPhone, CharitiesNo, CharitiesFile) " +
                             "VALUES (?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setString(1, charityAccount);
                    ps.setString(2, charityPassword);
                    ps.setString(3, charityAddress);
                    ps.setString(4, charityPhone);
                    ps.setString(5, charityNo);
                    ps.setString(6, dbFilePath);

                    ps.executeUpdate();
                }

            } else {
                request.setAttribute("errorMsg", "不支援的帳號類型");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // 註冊成功
            request.getSession().setAttribute("message", "註冊完成，歡迎加入！");
            response.sendRedirect("login.jsp");

        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("errorMsg", "註冊發生錯誤，請稍後再試");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
