package com.coursemanagement.web;

import com.coursemanagement.model.Module;
import com.coursemanagement.dao.ModuleDAO;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/FetchModulesServlet")
public class FetchModulesServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

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
}