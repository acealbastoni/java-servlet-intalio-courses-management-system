package com.coursemanagement.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coursemanagement.dao.CourseDAO;
import com.coursemanagement.model.Course;

@WebServlet("/AddCourseServlet")
public class AddCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private CourseDAO courseDAO;

	public void init() {
		courseDAO = new CourseDAO();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String name = request.getParameter("courseName");
		String description = request.getParameter("courseDescription");
		Course course = new Course();
		course.setName(name);
		course.setDescription(description);
		courseDAO.addCourse(course);
		response.sendRedirect("adminPage.html");
	}
}
