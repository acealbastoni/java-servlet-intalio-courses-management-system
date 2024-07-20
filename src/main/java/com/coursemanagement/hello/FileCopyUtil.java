package com.coursemanagement.hello;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FileCopyUtil {

    /**
     * Copies PDF files from the source directory to the destination directory if they do not already exist in the destination.
     *
     * @param sourceDirPath      The path to the source directory containing the PDF files (e.g., WEB-INF/pdf/).
     * @param destinationDirPath The path to the destination directory where files should be copied.
     */
    public static void copyPdfFilesIfNotExist(String sourceDirPath, String destinationDirPath) {
        File sourceDir = new File(sourceDirPath);
        File destinationDir = new File(destinationDirPath);

        if (!sourceDir.exists() || !sourceDir.isDirectory()) {
            System.err.println("Source directory does not exist or is not a directory.");
            return;
        }

        if (!destinationDir.exists()) {
            destinationDir.mkdirs(); // Create destination directory if it doesn't exist
        }

        File[] files = sourceDir.listFiles((dir, name) -> name.endsWith(".pdf"));
        if (files != null) {
            for (File file : files) {
                File destFile = new File(destinationDir, file.getName());
                if (!destFile.exists()) {
                    copyFile(file, destFile);
                } else {
                    System.out.println("File " + file.getName() + " already exists in the destination. Skipping.");
                }
            }
        }
    }

    private static void copyFile(File sourceFile, File destinationFile) {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destinationFile)) {

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            System.out.println("Copied file: " + sourceFile.getName());
        } catch (IOException e) {
            System.err.println("Failed to copy file: " + sourceFile.getName());
            e.printStackTrace();
        }
    }
}
