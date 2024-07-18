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
		// Retrieve form parameters
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");

		// For demonstration purposes, print the parameters to the console
		System.out.println("Username: " + username);
		System.out.println("Password: " + password);
		System.out.println("Role: " + role);

		// if ("admin".equals(role) && "your_admin_password_here".equals(password)) {
		if ("admin".equals(role) && "intalio".equals(password) && "intalio".equals(username)) {
			// Redirect to admin page
			response.sendRedirect("adminPage.html");
		} else if ("student".equals(role)) {
			// Redirect to error page or show error message
			response.sendRedirect("student.html");
		} else {
			response.sendRedirect("Error.jsp");
		}
	}
}
