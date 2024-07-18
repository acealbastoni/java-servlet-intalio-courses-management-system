package com.coursemanagement.initializer;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppStartupListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Application started!");

        // Initialize database
        Initializer initializer = new Initializer();
        Initializer.createDirectoryIfNotExists("C:\\AcelAlBastoniIntalioTaskMohamedAbdelhamid");
        initializer.initializeDatabase();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("Application stopped!");
    }
}
