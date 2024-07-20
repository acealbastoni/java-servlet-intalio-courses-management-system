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

import com.coursemanagement.dao.CourseDAO;
import com.coursemanagement.dao.ModuleDAO;
import com.coursemanagement.initializer.DatabaseInitializer;
import com.coursemanagement.model.Module;
import com.coursemanagement.utilities.DBConnection;

@WebServlet("/DownloadPDF")
public class DownloadPDF extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String TEST_FILE_PATH = DBConnection.UPLOAD_FILES_DIRECTORY;
    
    private ModuleDAO moduleDAO;
    
    
    @Override
    public void init() throws ServletException {
        moduleDAO = new ModuleDAO();
        
    }
       
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileGuid = request.getParameter("fileGuid");
        System.out.println(fileGuid);
        String moduleID = request.getParameter("moduleID");
        System.out.println(moduleID);
        // Construct the file path from the moduleID
        File file = new File(TEST_FILE_PATH + fileGuid + ".pdf");  

        if (file.exists()) {
        	   sendFileWithRenamed(response, file, fileGuid,Integer.valueOf(moduleID));
        	
        
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "File not found: " + file.getAbsolutePath());
        }
    }

    /**
     * Sends the file with its original name.
     */
//    private void sendFileWithOriginalName(HttpServletResponse response, File file) throws IOException {
//        response.setContentType("application/pdf");
//        response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");
//
//        try (FileInputStream fis = new FileInputStream(file);
//             OutputStream os = response.getOutputStream()) {
//            byte[] buffer = new byte[1024];
//            int bytesRead;
//            while ((bytesRead = fis.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//        }
//    }

    /**
     * Sends the file with a renamed filename.
     */
    private void sendFileWithRenamed(HttpServletResponse response, File file, String fileGuid,int moduleID) throws IOException {
        response.setContentType("application/pdf");

        // Example of renaming the file: prefixing with moduleID
        
      Module m= moduleDAO.getModuleById(moduleID);
        String newFileName = m.getModuleName() + ".pdf"; // Change this as needed
        response.setHeader("Content-Disposition", "attachment; filename=\"" + newFileName + "\"");

        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }

}
