package com.coursemanagement.initializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.coursemanagement.utilities.DBConnection;

public class Initializer {
    public Initializer() {
        try (Connection connection = DBConnection.getConnection()) {
            // Create the database if it doesn't exist
            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS course_module_db";
            connection.createStatement().execute(createDatabaseSQL);
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public void initializeDatabase() {
        try (Connection connection = DBConnection.getConnection()) {
            if (!tableExists(connection, "courses")) {
                createCoursesTable(connection);
                insertDummyCourses(connection);
            }
            if (!tableExists(connection, "modules")) {
                createModulesTable(connection);
                insertDummyModules(connection);
            }
            if (!tableExists(connection, "users")) {
                createUsersTable(connection);
                insertDummyUsers(connection);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    private boolean tableExists(Connection connection, String tableName) throws SQLException {
        DatabaseMetaData meta = connection.getMetaData();
        try (ResultSet resultSet = meta.getTables(null, null, tableName, new String[] { "TABLE" })) {
            return resultSet.next();
        }
    }

    private void createCoursesTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE courses (" +
                "courseID INT AUTO_INCREMENT PRIMARY KEY, " +
                "courseName VARCHAR(255) NOT NULL, " +
                "courseDescription VARCHAR(255))";
        connection.createStatement().execute(createTableSQL);
    }

    private void insertDummyCourses(Connection connection) throws SQLException {
        String insertSQL = "INSERT INTO courses (courseName, courseDescription) VALUES " +
                "('Java', 'Java Learning Course for intalio Task'), " +
                "('JavaScript', 'JavaScript Learning Course for intalio Task'), " +
                "('Python', 'Python Learning Course for intalio Task'), " +
                "('C#', 'C# Learning Course for intalio Task')";
        connection.createStatement().execute(insertSQL);
    }

    private void createModulesTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE modules (" +
                "moduleID INT AUTO_INCREMENT PRIMARY KEY, " +
                "moduleName VARCHAR(255) NOT NULL, " +
                "courseID INT, " +
                "pdfFileName VARCHAR(255), " +
                "FOREIGN KEY (courseID) REFERENCES courses(courseID))";
        connection.createStatement().execute(createTableSQL);
    }

    private void insertDummyModules(Connection connection) throws SQLException {
        String insertSQL = "INSERT INTO modules (moduleName, courseID, pdfFileName) VALUES " +
                "('Introduction to Object-Oriented Programming', 1, 'java_intro.pdf'), " +
                "('Advanced Java Programming', 1, 'java_advanced.pdf'), " +
                "('JavaScript Basics', 2, 'javascript_basics.pdf'), " +
                "('Frontend Development with React', 2, 'react_frontend.pdf'), " +
                "('Python Fundamentals', 3, 'python_fundamentals.pdf'), " +
                "('Data Analysis with Python', 3, 'python_data_analysis.pdf'), " +
                "('C# Basics', 4, 'csharp_basics.pdf'), " +
                "('.NET Framework Overview', 4, 'dotnet_overview.pdf'), " +
                "('Introduction to Object-Oriented Programming', 1, 'java_HibernateFramework.pdf')";
        connection.createStatement().execute(insertSQL);
    }

    private void createUsersTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY, " +
                "username VARCHAR(50) NOT NULL UNIQUE, " +
                "password VARCHAR(255) NOT NULL, " +
                "role VARCHAR(10) NOT NULL)";
        connection.createStatement().execute(createTableSQL);
    }

    private void insertDummyUsers(Connection connection) throws SQLException {
        String insertSQL = "INSERT INTO users (username, password, role) VALUES " +
                "('admin', '$2a$10$cqKzb5I2tb11eSCDhF8hMuVthVoZ0vy0LioBj7VjaE6oWcdzeUqSK', 'admin'), " +
                "('student1', '$2a$12$e9Bjz0W9U4QfWfouY/JDhOBXHZLTOt.Ddl0YETGA6NGZVpBDMZlaC', 'student'), " +
                "('student2', '$2a$12$e9Bjz0W9U4QfWfouY/JDhOBXHZLTOt.Ddl0YETGA6NGZVpBDMZlaC', 'student')";
        connection.createStatement().execute(insertSQL);
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

    public static void createDirectoryIfNotExists(String directoryPath) {
        Path path = Paths.get(directoryPath);
        try {
            if (Files.notExists(path)) {
                Files.createDirectories(path);
                System.out.println("Directory created: " + path.toString());
            } else {
                System.out.println("Directory already exists: " + path.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
