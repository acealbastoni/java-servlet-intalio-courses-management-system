package com.coursemanagement.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coursemanagement.model.Module;

public class ModuleDAO {
    // JDBC connection details
    private static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/course_module_db?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    // SQL queries
    private static final String SELECT_ALL_MODULES =
            "SELECT * FROM modules";

//    // Method to get a database connection
//    protected Connection getConnection() throws SQLException {
//        return DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASSWORD);
//    }
//    
//    
    
    
    
    
    protected Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    
    
    
    
    public static void main(String[] args) {
        ModuleDAO moduleDAO = new ModuleDAO();
        List<Module> modules = moduleDAO.getAllModules();
        
        // Print out the retrieved modules
        for (Module module : modules) {
            System.out.println("Module ID: " + module.getModuleID());
            System.out.println("Module Name: " + module.getModuleName());
            System.out.println("Course ID: " + module.getCourseID());
            System.out.println("PDF File Name: " + module.getPdfFileName());
            System.out.println();
        }
    }
    
    
    
    

    // Method to get all modules from the database
    public List<Module> getAllModules() {
        List<Module> modules = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MODULES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Module module = new Module();
                module.setModuleID(resultSet.getInt("moduleID"));
                module.setModuleName(resultSet.getString("moduleName"));
                module.setCourseID(resultSet.getInt("courseID"));
                module.setPdfFileName(resultSet.getString("pdfFileName")); // If storing PDF file names in database
                modules.add(module);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return modules;
    }

    

    
    
    
    
    
    
    // Method to handle SQLException
    private void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
//	
//	public void addCourse(Module module) {
//		try (Connection connection = getConnection();
//				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MODULE_SQL)) {
//
//			preparedStatement.setString(1, module.getName());
//			preparedStatement.setString(2, module.getDescription());
//			preparedStatement.executeUpdate();
//		} catch (SQLException e) {
//			printSQLException(e);
//		}
//	}
//    

	 private static final String INSERT_MODULE_SQL = "INSERT INTO modules (moduleName, courseID, moduleDescription, pdfFileName) VALUES (?, ?, ?, ?)";

	    public void addModule(Module module) {
	        try (Connection connection =getConnection();// DatabaseUtil.getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MODULE_SQL)) {
	            preparedStatement.setString(1, module.getModuleName());
	            preparedStatement.setInt(2, module.getCourseID());
	            preparedStatement.setString(3, module.getModuleDescription());
	            preparedStatement.setString(4, module.getPdfFileName());
	            preparedStatement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}