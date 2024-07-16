package com.coursemanagement.web;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/PublicServlet")
public class PublicServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String role = request.getParameter("role");

        if ("public".equals(role)) {
            // Redirect to public user page
            response.sendRedirect("publicStudents.html");
        } else {
            // Redirect to error page or show error message
            response.sendRedirect("error.jsp");
        }
    }
}
