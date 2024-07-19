package com.coursemanagement.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	static {
	    try {
	        Class.forName("com.mysql.cj.jdbc.Driver");
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}

	private static final String JDBC_DB_URL =   "jdbc:mysql://localhost:3306/course_module_db?useSSL=false&serverTimezone=UTC";
    //private static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/course_module_db?useSSL=false";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";
 
    public static final String UPLOAD_FILES_DIRECTORY = "C:\\AcelAlBastoniIntalioTaskMohamedAbdelhamid\\";
    

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASSWORD);
    }
}
