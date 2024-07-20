package com.coursemanagement.initializer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
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
            System.out.println("Script executed successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void runScript(Connection connection, String resourcePath) {
        try (InputStream inputStream = DatabaseInitializer.class.getResourceAsStream(resourcePath);
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
}
