package com.coursemanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coursemanagement.model.Course;

public class CourseDAO {
	private static final String JDBC_DB_URL = "jdbc:mysql://localhost:3306/course_module_db?useSSL=false&serverTimezone=UTC";
	private static final String JDBC_USER = "root";
	private static final String JDBC_PASSWORD = "root";

	private static final String INSERT_COURSE_SQL = "INSERT INTO courses (courseName, courseDescription) VALUES (?, ?)";
	private static final String SELECT_COURSE_BY_ID = "SELECT id, name, description FROM courses WHERE id = ?";
	private static final String SELECT_ALL_COURSES = "SELECT * FROM courses";
	private static final String DELETE_COURSE_SQL = "DELETE FROM courses WHERE id = ?";
	private static final String UPDATE_COURSE_SQL = "UPDATE courses SET name = ?, description = ? WHERE id = ?";

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

	public void addCourse(Course course) {
		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COURSE_SQL)) {

			preparedStatement.setString(1, course.getName());
			preparedStatement.setString(2, course.getDescription());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

//    public Course selectCourse(int id) {
//        Course course = null;
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COURSE_BY_ID)) {
//            preparedStatement.setInt(1, id);
//            ResultSet rs = preparedStatement.executeQuery();
//            if (rs.next()) {
//                String name = rs.getString("name");
//                String courseDescription = rs.getString("courseDescription");
//                course = new Course(id, name, courseDescription);
//            }
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//        return course;
//    }

//    public boolean deleteCourse(int id) {
//        boolean rowDeleted = false;
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(DELETE_COURSE_SQL)) {
//            statement.setInt(1, id);
//            rowDeleted = statement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//        return rowDeleted;
//    }

//    public boolean updateCourse(Course course) {
//        boolean rowUpdated = false;
//        try (Connection connection = getConnection();
//             PreparedStatement statement = connection.prepareStatement(UPDATE_COURSE_SQL)) {
//            statement.setString(1, course.getName());
//            statement.setString(2, course.getDescription());
//            statement.setInt(3, course.getId());
//            rowUpdated = statement.executeUpdate() > 0;
//        } catch (SQLException e) {
//            printSQLException(e);
//        }
//        return rowUpdated;
//    }

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

	// Method to get all Courses from the database
	public List<Course> getAllCourses() {
		List<Course> courses = new ArrayList<>();
		try (Connection connection = getConnection();
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

	public static void main(String[] args) {
		CourseDAO courseDAO = new CourseDAO();
		List<Course> courses = courseDAO.getAllCourses();

		// Print out the retrieved modules
		for (Course course : courses) {
			System.out.println("Course ID: " + course.getId());
			System.out.println("Course Name: " + course.getName());
			System.out.println("Course ID: " + course.getDescription());
			System.out.println();
		}
	}

}
