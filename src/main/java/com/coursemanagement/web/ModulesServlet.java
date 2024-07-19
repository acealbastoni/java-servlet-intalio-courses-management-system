package com.coursemanagement.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.coursemanagement.dao.ModuleDAO;
import com.coursemanagement.model.Module;
import com.coursemanagement.utilities.DBConnection;
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
        // Check if the request is multipart
        if (!ServletFileUpload.isMultipartContent(request)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Form must have enctype=multipart/form-data.");
            return;
        }

        try {
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            int courseId = 0;
            String moduleName = null;
            String moduleDescription = null;
            String fileName = null;

            for (FileItem item : upload.parseRequest(request)) {
                if (item.isFormField()) {
                    String fieldName = item.getFieldName();
                    String fieldValue = item.getString();

                    if ("courseId".equals(fieldName)) {
                        courseId = Integer.parseInt(fieldValue);
                    } else if ("moduleName".equals(fieldName)) {
                        moduleName = fieldValue;
                    } else if ("moduleDescription".equals(fieldName)) {
                        moduleDescription = fieldValue;
                    }
                } else {
                    if ("moduleFile".equals(item.getFieldName())) {
                        fileName = Paths.get(item.getName()).getFileName().toString();
                        InputStream fileContent = item.getInputStream();

                        // Save the file to the desired location
                        //String uploadDir = getServletContext().getRealPath("/uploads");
                        String uploadDir =  DBConnection.UPLOAD_FILES_DIRECTORY;
                        File uploads = new File(uploadDir);
                        if (!uploads.exists()) {
                            uploads.mkdir();
                        }
                        File file = new File(uploads, fileName);
                        try (OutputStream out = new FileOutputStream(file)) {
                            byte[] buffer = new byte[1024];
                            int bytesRead;
                            while ((bytesRead = fileContent.read(buffer)) != -1) {
                                out.write(buffer, 0, bytesRead);
                            }
                        }
                    }
                }
            }

            // Debugging: Log the retrieved values
            System.out.println("courseId: " + courseId);
            System.out.println("moduleName: " + moduleName);
            System.out.println("moduleDescription: " + moduleDescription);
            System.out.println("fileName: " + fileName);

            if (moduleName == null || moduleName.isEmpty() || moduleDescription == null || moduleDescription.isEmpty() || fileName == null) {
                request.setAttribute("errorMessage", "All fields are required.");
                request.getRequestDispatcher("/errorPage.jsp").forward(request, response);
                return;
            }

            // Create a new Module object
            Module module = new Module();
            module.setCourseID(courseId);
            module.setModuleName(moduleName);
            module.setModuleDescription(moduleDescription);
            module.setPdfFileName(fileName);

            // Add the module to the database
            moduleDAO.addModule(module);

            // Redirect to the admin page or another page as needed
            response.sendRedirect("adminPage.html");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }

    
    
}