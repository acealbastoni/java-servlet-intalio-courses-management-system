package com.coursemanagement.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.coursemanagement.dao.ModuleDAO;
import com.coursemanagement.model.Module;
import com.coursemanagement.utilities.DBConnection;

@WebServlet("/DownloadModuleServlet")
public class DownloadModuleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ModuleDAO moduleADO;

    @Override
    public void init() throws ServletException {
        moduleADO = new ModuleDAO(); 
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String moduleID = request.getParameter("moduleID");
        if (moduleID == null || moduleID.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Module ID is missing");
            return;
        }

        try {
            int moduleIdInt = Integer.parseInt(moduleID);
            Module module = moduleADO.getModuleById(moduleIdInt);
            if (module == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Module not found");
                return;
            }

            String filePath = DBConnection.UPLOAD_FILES_DIRECTORY + module.getPdfFileName();
            File file = new File(filePath);
            if (!file.exists()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found");
                return;
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

            try (FileInputStream inStream = new FileInputStream(file);
                 OutputStream outStream = response.getOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead;

                while ((bytesRead = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, bytesRead);
                }
            }
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid module ID");
        }
    }
}
