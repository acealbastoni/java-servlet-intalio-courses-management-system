package com.coursemanagement.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coursemanagement.dao.ModuleDAO;
import com.coursemanagement.model.Module;
import com.google.gson.Gson;

@WebServlet("/ModulesServlet")
public class ModulesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private ModuleDAO moduleDAO;

    @Override
    public void init() {
        moduleDAO = new ModuleDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ModuleDAO moduleDAO = new ModuleDAO();
        List<Module> modules = moduleDAO.getAllModules();

        // Convert modules to JSON
        Gson gson = new Gson();
        String jsonModules = gson.toJson(modules);

        // Set response type and send JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonModules);
    }
    
    
    

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get form parameters
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        String moduleName = request.getParameter("moduleName");
        String moduleDescription = request.getParameter("moduleDescription");

        // Create a new Module object
        Module module = new Module();
        module.setCourseID(courseId);
        module.setModuleName(moduleName);
        module.setPdfFileName(moduleDescription);

        // Add the module to the database
        moduleDAO.addModule(module);

        // Redirect to the admin page or another page as needed
        response.sendRedirect("adminPage.html");
    }
    
    
    
    
    
    
}