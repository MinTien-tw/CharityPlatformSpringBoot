package controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    HttpSession session = request.getSession(false);
	    if (session != null) {
	        session.invalidate();
	    }
	    response.setContentType("text/html;charset=UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.getWriter().write("<!DOCTYPE html><html><head><meta charset='UTF-8'><title>登出</title></head><body>"
	        + "<h2>登出成功</h2><p>您已成功登出，<a href='" + request.getContextPath() + "/index'>點此回首頁</a></p></body></html>");
	}
}
