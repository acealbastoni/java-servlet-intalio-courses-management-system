package com.coursemanagement.model;

public class Module {
    private int moduleID;
    private String moduleName;
    private int courseID;
    private String  moduleDescription;
    private String pdfFileName; // Optional, if storing PDF file names in the database

    // Constructors, getters, setters (based on your requirements)
    public Module() {
    }

    public Module(int moduleID, String moduleName, int courseID) {
        this.moduleID = moduleID;
        this.moduleName = moduleName;
        this.courseID = courseID;
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

	

	
}