package com.coursemanagement.web;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.coursemanagement.dao.UserDAO;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
	private UserDAO userDAO;
	
	public void init() {
		//userDAO = new UserDAO();
	}
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String role = request.getParameter("role");
        String password = request.getParameter("password");

        //if ("admin".equals(role) && "your_admin_password_here".equals(password)) {
        if (true) {
            // Redirect to admin page
            response.sendRedirect("adminPage.html");
        } else {
            // Redirect to error page or show error message
            response.sendRedirect("error.jsp");
        }
    }
}
