package com.coursemanagement.web;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String role = request.getParameter("role");
        String password = request.getParameter("password");

        if ("admin".equals(role) && "your_admin_password_here".equals(password)) {
            // Redirect to admin page
            response.sendRedirect("adminPage.jsp");
        } else {
            // Redirect to error page or show error message
            response.sendRedirect("error.jsp");
        }
    }
}
