package com.coursemanagement.initializer;

import com.coursemanagement.hello.PasswordHash;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DatabaseInitializer2 {

	public static void main(String[] args) {
	    String url = "jdbc:mysql://localhost:3306?serverTimezone=UTC";
	    String username = "root";
	    String password = "root";

	    try (Connection connection = DriverManager.getConnection(url, username, password)) {
	        runScript(connection, "/script.sql");
	        insertUsers(connection);
	        insertCourses(connection);
	        insertModules(connection);
	        System.out.println("Script executed successfully.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	   /* //█████████████████████ █████████████████████████████ █████████████████████████████  */
	private static void insertCourses(Connection connection) {
	    String[] courseNames = {"Java", "JavaScript", "Python", "C#"};
	    String[] courseDescriptions = {
	        "Java Learning Course for intalio Task",
	        "JavaScript Learning Course for intalio Task",
	        "Python Learning Course for intalio Task",
	        "C# Learning Course for intalio Task"
	    };

	    try (Statement statement = connection.createStatement()) {
	        for (int i = 0; i < courseNames.length; i++) {
	            String courseName = courseNames[i];
	            String courseDescription = courseDescriptions[i];

	            // Check if the course already exists based on courseName
	            String checkCourseSQL = String.format(
	                "SELECT courseID FROM courses WHERE courseName = '%s';", courseName
	            );
	            try (ResultSet resultSet = statement.executeQuery(checkCourseSQL)) {
	                if (resultSet.next()) {
	                    System.out.println("Course " + courseName + " already exists. Skipping insertion.");
	                } else {
	                    String insertCourseSQL = String.format(
	                        "INSERT INTO courses (courseName, courseDescription) VALUES ('%s', '%s');",
	                        courseName, courseDescription
	                    );
	                    statement.execute(insertCourseSQL);
	                }
	            }
	        }
	        System.out.println("Courses inserted successfully.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	   /* //█████████████████████ █████████████████████████████ █████████████████████████████  */
	private static void insertModules(Connection connection) {
	    String[] moduleNames = {
	        "Introduction to Object-Oriented Programming",
	        "Advanced Java Programming",
	        "JavaScript Basics",
	        "Frontend Development with React",
	        "Python Fundamentals",
	        "Data Analysis with Python",
	        "C# Basics",
	        ".NET Framework Overview"
	    };
	    int[] courseIds = {1, 1, 2, 2, 3, 3, 4, 4};
	    String[] pdfFileNames = {
	        "java_intro.pdf", "java_advanced.pdf",
	        "javascript_basics.pdf", "react_frontend.pdf",
	        "python_fundamentals.pdf", "python_data_analysis.pdf",
	        "csharp_basics.pdf", "dotnet_overview.pdf"
	    };
	    String[] moduleDescriptions = {
	        "Java Learning Course for intalio Task", "Java Learning Course for intalio Task",
	        "JavaScript Learning Course for intalio Task", "JavaScript Learning Course for intalio Task",
	        "Python Learning Course for intalio Task", "Python Learning Course for intalio Task",
	        "C# Learning Course for intalio Task", "C# Learning Course for intalio Task"
	    };
	    String[] fileGuids = {
	        "a1c351e7-17c9-4e70-9b1e-b0f8fdb1794b",
			"0f512340-cf19-42ad-9c3e-43ebfc792dde",
			"ea73fa08-8de0-43ba-bc93-1f4958648220",
			"2ba569ef-e6d0-48bd-890c-19fe20efbaff", 
	        "8126d7f2-cb61-44ee-ac78-264bbc8a3aa0",
			"15576944-7b55-46ba-b5b2-50c39d815ef4",
			"dc175ac1-7142-43d1-9892-2b6dd1297984",
			"16cbabe7-2851-49c1-9a46-1addfc1c5348"
	    };

	    try (Statement statement = connection.createStatement()) {
	        for (int i = 0; i < moduleNames.length; i++) {
	            String moduleName = moduleNames[i];
	            int courseId = courseIds[i];
	            String pdfFileName = pdfFileNames[i];
	            String moduleDescription = moduleDescriptions[i];
	            String fileGuid = fileGuids[i];

	            // Check if the module already exists based on moduleName and courseID
	            String checkModuleSQL = String.format(
	                "SELECT moduleID FROM modules WHERE moduleName = '%s' AND courseID = %d;", 
	                moduleName, courseId
	            );
	            try (ResultSet resultSet = statement.executeQuery(checkModuleSQL)) {
	                if (resultSet.next()) {
	                    System.out.println("Module " + moduleName + " for course ID " + courseId + " already exists. Skipping insertion.");
	                } else {
	                    String insertModuleSQL = String.format(
	                        "INSERT INTO modules (moduleName, courseID, pdfFileName, moduleDescription, fileGuid) VALUES ('%s', %d, '%s', '%s', '%s');",
	                        moduleName, courseId, pdfFileName, moduleDescription, fileGuid
	                    );
	                    statement.execute(insertModuleSQL);
	                }
	            }
	        }
	        System.out.println("Modules inserted successfully.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	   /* //█████████████████████ █████████████████████████████ █████████████████████████████  */
    private static void runScript(Connection connection, String resourcePath) {
        try (InputStream inputStream = DatabaseInitializer2.class.getResourceAsStream(resourcePath);
             Scanner scanner = new Scanner(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {

            scanner.useDelimiter(";");

            try (Statement statement = connection.createStatement()) {
                while (scanner.hasNext()) {
                    String sqlStatement = scanner.next().trim();
                    if (!sqlStatement.isEmpty()) {
                        statement.execute(sqlStatement + ";");
                    }
                }
                System.out.println("Script executed successfully.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* //█████████████████████ █████████████████████████████ █████████████████████████████  */
    private static void insertUsers(Connection connection) {
        PasswordHash passwordHash = new PasswordHash();

        String[] usernames = {"admin", "student", "student1", "student2", "instructor"};
        String[] roles = {"admin", "student", "student", "student", "student"};

        try (Statement statement = connection.createStatement()) {
            for (int i = 0; i < usernames.length; i++) {
                String username = usernames[i];
                String role = roles[i];

                // Check if the user already exists
                String checkUserSQL = String.format("SELECT COUNT(*) FROM users WHERE username = '%s';", username);
                try (ResultSet resultSet = statement.executeQuery(checkUserSQL)) {
                    resultSet.next();
                    int count = resultSet.getInt(1);

                    if (count == 0) {
                        String hashedPassword = passwordHash.getHashedPassword(username);
                        String insertUserSQL = String.format(
                                "INSERT INTO users (username, password, role) VALUES ('%s', '%s', '%s');",
                                username, hashedPassword, role
                        );
                        statement.execute(insertUserSQL);
                    } else {
                        System.out.println("User " + username + " already exists. Skipping insertion.");
                    }
                }
            }
            System.out.println("Users inserted successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
