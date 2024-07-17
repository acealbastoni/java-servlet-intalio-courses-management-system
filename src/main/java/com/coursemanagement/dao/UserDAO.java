package com.coursemanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDAO {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/";
    private static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/course_module_db?useSSL=false";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "root";

    public UserDAO() {
        try (Connection connection = getInitialConnection()) {
            // Create the database if it doesn't exist
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS course_module_db";
            connection.createStatement().execute(createDatabaseSQL);
        } catch (SQLException e) {
            printSQLException(e);
        }

        try (Connection connection = getConnection()) {
            // Create courses table if it doesn't exist
            String createTableSQL = "CREATE TABLE IF NOT EXISTS courses ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(255) NOT NULL, "
                    + "description VARCHAR(255) NOT NULL)";
            connection.createStatement().execute(createTableSQL);
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    protected Connection getInitialConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

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

    public void addCourse(String name, String description) {
        String insertSQL = "INSERT INTO courses (name, description) VALUES (?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

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

    public static void main(String[] args) {
//        UserDAO userDAO = new UserDAO();
//        try (Connection connection = userDAO.getConnection()) {
//            if (connection != null) {
//                System.out.println("Connection successful!");
//            } else {
//                System.out.println("Failed to make connection!");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }
}
