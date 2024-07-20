package com.coursemanagement.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coursemanagement.dao.CourseDAO;
import com.coursemanagement.dao.ModuleDAO;
import com.coursemanagement.model.Course;
import com.coursemanagement.model.Module;
import com.fasterxml.jackson.databind.ObjectMapper; // For JSON conversion

@WebServlet("/DownloadModuleServlet")
public class DownloadModuleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ModuleDAO moduleDAO;
    private CourseDAO courseDAO;
    
    @Override
    public void init() throws ServletException {
        moduleDAO = new ModuleDAO();
        courseDAO = new CourseDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Module> modules = moduleDAO.getAllModules();
        List<Course> courses = courseDAO.getAllCourses();

        // Create a map for course ID to course name
        Map<Integer, String> courseNameMap = new HashMap<>();
        for (Course course : courses) {
            courseNameMap.put(course.getId(), course.getName()); // Assuming Course class has getId() and getName() methods
        }

        // Set course names in modules
        for (Module module : modules) {
            module.setCourseName(courseNameMap.get(module.getCourseID()));
        }

        // Convert list of modules to JSON
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(modules);

        response.setContentType("application/json");
        response.getWriter().write(json);
    }
}
