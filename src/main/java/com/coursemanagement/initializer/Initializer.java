package com.coursemanagement.initializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.coursemanagement.utilities.DBConnection;
import com.coursemanagement.utilities.PasswordUtil;

public class Initializer {
    public Initializer() {
    	DatabaseInitializer.main(null);
        try (Connection connection = DBConnection.getConnection()) {
        	 initializeDatabase();
//            // Create the database if it doesn't exist
//            String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS course_module_db";
//            connection.createStatement().execute(createDatabaseSQL);
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
    /* //█████████████████████ █████████████████████████████ █████████████████████████████  */
    private void createCoursesTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS courses (" +
                "courseID INT NOT NULL AUTO_INCREMENT, " +
                "courseName VARCHAR(255) NOT NULL, " +
                "courseDescription VARCHAR(255), " +
                "PRIMARY KEY (courseID))";
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
    /* //█████████████████████ █████████████████████████████ █████████████████████████████  */
    private void createModulesTable(Connection connection) throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS modules (" +
                "moduleID INT AUTO_INCREMENT PRIMARY KEY, " +
                "moduleName VARCHAR(255) NOT NULL, " +
                "courseID INT, " +
                "pdfFileName VARCHAR(255), " +
                "moduleDescription TEXT NOT NULL, " +
                "fileGuid VARCHAR(255);";
                //"FOREIGN KEY (courseID) REFERENCES courses(courseID))";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            //connection.commit();  // Commit the transaction
        } catch (SQLException e) {
            connection.rollback();  // Rollback the transaction in case of error
            throw e;
        }
    }



    private void insertDummyModules(Connection connection) throws SQLException {
        String insertSQL = "INSERT INTO modules (moduleName, courseID, pdfFileName, moduleDescription, fileGuid) VALUES " +
                "('Introduction to Object-Oriented Programming', 1, 'java_intro.pdf', 'Java Learning Course for intalio Task', 'guid1'), " +
                "('Advanced Java Programming', 1, 'java_advanced.pdf', 'Java Learning Course for intalio Task', 'guid2'), " +
                "('JavaScript Basics', 2, 'javascript_basics.pdf', 'JavaScript Learning Course for intalio Task', 'guid3'), " +
                "('Frontend Development with React', 2, 'react_frontend.pdf', 'JavaScript Learning Course for intalio Task', 'guid4'), " +
                "('Python Fundamentals', 3, 'python_fundamentals.pdf', 'Python Learning Course for intalio Task', 'guid5'), " +
                "('Data Analysis with Python', 3, 'python_data_analysis.pdf', 'Python Learning Course for intalio Task', 'guid6'), " +
                "('C# Basics', 4, 'csharp_basics.pdf', 'C# Learning Course for intalio Task', 'guid7'), " +
                "('.NET Framework Overview', 4, 'dotnet_overview.pdf', 'C# Learning Course for intalio Task', 'guid8')"; 
               
        connection.setAutoCommit(false); // Disable auto-commit mode
        try (Statement statement = connection.createStatement()) {
            statement.execute(insertSQL);
            connection.commit(); // Commit the transaction
        } catch (SQLException e) {
            connection.rollback(); // Rollback the transaction in case of error
            e.printStackTrace(); // Print the exception details
            throw e; // Re-throw the exception if necessary
        } finally {
            connection.setAutoCommit(true); // Re-enable auto-commit mode
        }
    }


    /* //█████████████████████ █████████████████████████████ █████████████████████████████  */
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
                "('admin', '"+PasswordUtil.hashPassword("admin")+"', 'admin'), " +
                "('student', '"+PasswordUtil.hashPassword("student")+"', 'student'), " +
                "('student1', '"+PasswordUtil.hashPassword("student1")+"', 'student'),"+
                "('student2', '"+PasswordUtil.hashPassword("student2")+"', 'student'),"+
                "('instructor', '"+PasswordUtil.hashPassword("instructor")+"', 'student')";
                
        connection.createStatement().execute(insertSQL);
    }

    /* //█████████████████████ █████████████████████████████ █████████████████████████████  */

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
    
    /* //█████████████████████ █████████████████████████████ █████████████████████████████  */
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
}
