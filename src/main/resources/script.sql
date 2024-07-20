CREATE DATABASE IF NOT EXISTS course_module_db;
USE course_module_db;

-- Create Courses Table
CREATE TABLE IF NOT EXISTS courses (
    courseID INT NOT NULL AUTO_INCREMENT, 
    courseName VARCHAR(255) NOT NULL, 
    courseDescription VARCHAR(255), 
    PRIMARY KEY (courseID)
);


-- Create Modules Table
CREATE TABLE IF NOT EXISTS modules (
    moduleID INT AUTO_INCREMENT PRIMARY KEY, 
    moduleName VARCHAR(255) NOT NULL, 
    courseID INT, 
    pdfFileName VARCHAR(255), 
    moduleDescription TEXT NOT NULL, 
    fileGuid VARCHAR(255),
    FOREIGN KEY (courseID) REFERENCES courses(courseID)
);

-- Create Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    username VARCHAR(50) NOT NULL UNIQUE, 
    password VARCHAR(255) NOT NULL, 
    role VARCHAR(10) NOT NULL
);
