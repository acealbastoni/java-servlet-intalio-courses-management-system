package com.coursemanagement.model;

public class Module {
	private int moduleID;
	private String moduleName;
	private int courseID;
	private String moduleDescription;
	private String pdfFileName;
	private String fileGuid;
	private String courseName;

	public Module() {
	}

	public Module(int moduleID, String moduleName, String moduleDescription, String pdfFileName, String fileGuid) {
		this.moduleID = moduleID;
		this.moduleName = moduleName;
		this.moduleDescription = moduleDescription;
		this.pdfFileName = pdfFileName;
		this.fileGuid = fileGuid;
	}

	public int getModuleID() {
		return moduleID;
	}

	public void setModuleID(int moduleID) {
		this.moduleID = moduleID;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getModuleDescription() {
		return moduleDescription;
	}

	public void setModuleDescription(String moduleDescription) {
		this.moduleDescription = moduleDescription;
	}

	public String getPdfFileName() {
		return pdfFileName;
	}

	public void setPdfFileName(String pdfFileName) {
		this.pdfFileName = pdfFileName;
	}

	public String getFileGuid() {
		return fileGuid;
	}

	public void setFileGuid(String fileGuid) {
		this.fileGuid = fileGuid;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

}