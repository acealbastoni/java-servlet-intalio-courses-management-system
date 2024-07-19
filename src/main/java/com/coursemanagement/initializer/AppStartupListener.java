package com.coursemanagement.initializer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.coursemanagement.utilities.DBConnection;

@WebListener
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application started!");

        // Initialize database
    	DBConnection DBConnection = new DBConnection();
        Initializer initializer = new Initializer();
        Initializer.createDirectoryIfNotExists(DBConnection.UPLOAD_FILES_DIRECTORY);
        initializer.initializeDatabase();
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application stopped!");
    }
}
