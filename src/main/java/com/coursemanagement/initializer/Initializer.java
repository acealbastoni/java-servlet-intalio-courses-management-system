package com.coursemanagement.initializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Initializer {
	private static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/course_module_db?useSSL=false&serverTimezone=UTC";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASSWORD = "root";

	public Initializer() {
		try (Connection connection = getInitialConnection()) {
			// Create the database if it doesn't exist
			String createDatabaseSQL = "CREATE DATABASE IF NOT EXISTS course_module_db";
			connection.createStatement().execute(createDatabaseSQL);
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	protected Connection getInitialConnection() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(JDBC_DB_URL, JDBC_USER, JDBC_PASSWORD);
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

	public void initializeDatabase() {
		try (Connection connection = getConnection()) {
			if (!tableExists(connection, "courses")) {
				createCoursesTable(connection);
				insertDummyCourses(connection);
			}
			if (!tableExists(connection, "modules")) {
				createModulesTable(connection);
				insertDummyModules(connection);
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
		String createTableSQL = "CREATE TABLE courses (" + "courseID INT AUTO_INCREMENT PRIMARY KEY, "
				+ "courseName VARCHAR(255) NOT NULL, " + "courseDescription VARCHAR(255))";
		connection.createStatement().execute(createTableSQL);
	}

	private void insertDummyCourses(Connection connection) throws SQLException {
		String insertSQL = "INSERT INTO courses (courseID, courseName, courseDescription) VALUES "
				+ "(1, 'Java', 'Java Learning Course for intalio Task'), "
				+ "(2, 'JavaScript', 'JavaScript Learning Course for intalio Task'), "
				+ "(3, 'Python', 'Python Learning Course for intalio Task'), "
				+ "(4, 'C#', 'C# Learning Course for intalio Task')";
		connection.createStatement().execute(insertSQL);
	}

	private void createModulesTable(Connection connection) throws SQLException {
		String createTableSQL = "CREATE TABLE modules (" + "moduleID INT AUTO_INCREMENT PRIMARY KEY, "
				+ "moduleName VARCHAR(255) NOT NULL, " + "courseID INT, " + "pdfFileName VARCHAR(255), "
				+ "FOREIGN KEY (courseID) REFERENCES courses(courseID))";
		connection.createStatement().execute(createTableSQL);
	}

	private void insertDummyModules(Connection connection) throws SQLException {
		String insertSQL = "INSERT INTO modules (moduleID, moduleName, courseID, pdfFileName) VALUES "
				+ "(1, 'Introduction to Object-Oriented Programming', 1, 'java_intro.pdf'), "
				+ "(2, 'Advanced Java Programming', 1, 'java_advanced.pdf'), "
				+ "(3, 'JavaScript Basics', 2, 'javascript_basics.pdf'), "
				+ "(4, 'Frontend Development with React', 2, 'react_frontend.pdf'), "
				+ "(5, 'Python Fundamentals', 3, 'python_fundamentals.pdf'), "
				+ "(6, 'Data Analysis with Python', 3, 'python_data_analysis.pdf'), "
				+ "(7, 'C# Basics', 4, 'csharp_basics.pdf'), "
				+ "(8, '.NET Framework Overview', 4, 'dotnet_overview.pdf'), "
				+ "(9, 'Introduction to Object-Oriented Programming', 1, 'java_HibernateFramework.pdf')";
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
