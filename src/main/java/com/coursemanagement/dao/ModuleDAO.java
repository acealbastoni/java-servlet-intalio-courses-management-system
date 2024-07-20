package com.coursemanagement.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.coursemanagement.model.Module;
import com.coursemanagement.utilities.DBConnection;

public class ModuleDAO {

	private static final String SELECT_ALL_MODULES = "SELECT * FROM modules";
	private static final String INSERT_MODULE_SQL = "INSERT INTO modules (courseID, moduleName, moduleDescription, pdfFileName, fileGuid) VALUES (?, ?, ?, ?, ?)";
	private static final String SELECT_MODULE_BY_ID = "SELECT moduleID, moduleName, moduleDescription, pdfFileName, fileGuid FROM modules WHERE moduleID = ?";

	public List<Module> getAllModules() {
		List<Module> modules = new ArrayList<>();
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_MODULES)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				Module module = new Module();
				module.setModuleID(resultSet.getInt("moduleID"));
				module.setModuleName(resultSet.getString("moduleName"));
				module.setCourseID(resultSet.getInt("courseID"));
				module.setPdfFileName(resultSet.getString("pdfFileName"));
				module.setModuleDescription(resultSet.getString("moduleDescription"));
				module.setFileGuid(resultSet.getString("fileGuid"));

				modules.add(module);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return modules;
	}

	public void addModule(Module module) {
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_MODULE_SQL)) {

			preparedStatement.setInt(1, module.getCourseID());
			preparedStatement.setString(2, module.getModuleName());
			preparedStatement.setString(3, module.getModuleDescription());
			preparedStatement.setString(4, module.getPdfFileName());
			preparedStatement.setString(5, module.getFileGuid());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public Module getModuleById(int id) {
		Module module = null;
		try (Connection connection = DBConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_MODULE_BY_ID)) {
			preparedStatement.setInt(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			if (rs.next()) {
				Integer moduleID = rs.getInt("moduleID");
				String moduleName = rs.getString("moduleName");
				String moduleDescription = rs.getString("moduleDescription");
				String pdfFileName = rs.getString("pdfFileName");
				String fileGUID = rs.getString("fileGuid");
				module = new Module(moduleID, moduleName, moduleDescription, pdfFileName, fileGUID);
			}
		} catch (SQLException e) {
			printSQLException(e);
		}
		return module;
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
		ModuleDAO moduleDAO = new ModuleDAO();
		Module module = moduleDAO.getModuleById(1); // moduleDAO.getAllModules();

		System.out.println("Module ID: " + module.getModuleID());
		System.out.println("Module Name: " + module.getModuleName());
		System.out.println("Course ID: " + module.getCourseID());
		System.out.println("PDF File Name: " + module.getPdfFileName());
		System.out.println();

	}
}
