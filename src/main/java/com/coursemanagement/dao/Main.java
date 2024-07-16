package com.coursemanagement.dao;
import java.util.List;

import com.coursemanagement.model.Module;

public class Main {
    public static void main(String[] args) {
        ModuleDAO moduleDAO = new ModuleDAO();
        List<Module> modules = moduleDAO.getAllModules();
        
        // Print out the retrieved modules
        for (Module module : modules) {
            System.out.println("Module ID: " + module.getModuleID());
            System.out.println("Module Name: " + module.getModuleName());
            System.out.println("Course ID: " + module.getCourseID());
            System.out.println("PDF File Name: " + module.getPdfFileName());
            System.out.println();
        }
    }
}
