package com.coursemanagement.initializer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.coursemanagement.utilities.DBConnection;

@WebListener
public class AppStartupListener implements ServletContextListener {
	static {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application started!");

        // Initialize database
        //DatabaseInitializer.main(null);
        DatabaseInitializer2.main(null);
    	//DBConnection DBConnection = new DBConnection();
    	//Initializer.createDirectoryIfNotExists(DBConnection.UPLOAD_FILES_DIRECTORY);
        //Initializer initializer = new Initializer();
        //initializer.initializeDatabase();
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application stopped!");
    }
}
