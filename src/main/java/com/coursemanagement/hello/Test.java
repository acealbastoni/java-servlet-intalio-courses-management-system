package com.coursemanagement.hello;

import java.util.UUID;

public class Test {

	public static void main(String[] args) {
		//System.out.println( UUID.randomUUID().toString());
		  //String sourceDirPath = getServletContext().getRealPath("/WEB-INF/pdf/");
			String sourceDirPath = "C:\\sevlet\\java-servlet-intalio-courses-management-system\\src\\main\\webapp\\WEB-INF\\pdf";
	        String destinationDirPath = "C:\\AcelAlBastoniIntalioTaskMohamedAbdelhamid";

	        FileCopyUtil.copyPdfFilesIfNotExist(sourceDirPath, destinationDirPath);
		
		
		
	}

}
