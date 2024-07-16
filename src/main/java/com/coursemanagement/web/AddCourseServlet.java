package com.coursemanagement.web;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coursemanagement.dao.UserDAO;

@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("courseName");
        String description = request.getParameter("courseDescription");

        userDAO.addCourse(name, description);

        response.sendRedirect("admin.html");
    }
}
