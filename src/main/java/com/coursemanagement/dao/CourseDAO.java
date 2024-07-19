package com.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coursemanagement.model.Course;
import com.coursemanagement.utilities.DBConnection;

public class CourseDAO {
    private static final String INSERT_COURSE_SQL = "INSERT INTO courses (courseName, courseDescription) VALUES (?, ?)";
    private static final String SELECT_ALL_COURSES = "SELECT * FROM courses";

    public void addCourse(Course course) {
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COURSE_SQL)) {
            preparedStatement.setString(1, course.getName());
            preparedStatement.setString(2, course.getDescription());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            printSQLException(e);
        }
    }

    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COURSES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("courseID"));
                course.setName(resultSet.getString("courseName"));
                course.setDescription(resultSet.getString("courseDescription"));
                courses.add(course);
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        return courses;
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
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courses = courseDAO.getAllCourses();

        // Print out the retrieved courses
        for (Course course : courses) {
            System.out.println("Course ID: " + course.getId());
            System.out.println("Course Name: " + course.getName());
            System.out.println("Course Description: " + course.getDescription());
            System.out.println();
        }
    }
}
