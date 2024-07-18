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
@WebServlet("/DownloadModuleServlet")
public class DownloadModuleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String moduleID = request.getParameter("moduleID");
        // Assuming filePath is constructed based on moduleID
        String filePath = "C:\\AcelAlBastoniIntalioTaskMohamedAbdelhamid\\" + "cntract.pdf";
        
        System.out.println("File path: " + filePath);  // Debugging line

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist: " + filePath);  // Debugging line
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());

        try (FileInputStream inStream = new FileInputStream(file);
             OutputStream outStream = response.getOutputStream()) {

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
        }
    }
}
