package com.coursemanagement.initializer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.coursemanagement.utilities.DBConnection;

public class DatabaseInitializer {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC";
    private static final String DB_NAME = "course_module_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void main(String[] args) {
        try {
            createDatabase();
            new Initializer().initializeDatabase();
            try (Connection connection = getConnection()) {
                             
               
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createDatabase() throws SQLException {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement statement = connection.createStatement()) {
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS " + DB_NAME;
            statement.executeUpdate(createDatabaseSQL);
        }
    }

    private static Connection getConnection() throws SQLException {
    	 return DriverManager.getConnection(JDBC_URL + "/" + DB_NAME, USER, PASSWORD);
    }






}
