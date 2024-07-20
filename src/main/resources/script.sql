CREATE DATABASE IF NOT EXISTS course_module_db;
USE course_module_db;

-- Create Courses Table
CREATE TABLE IF NOT EXISTS courses (
    courseID INT NOT NULL AUTO_INCREMENT, 
    courseName VARCHAR(255) NOT NULL, 
    courseDescription VARCHAR(255), 
    PRIMARY KEY (courseID)
);

-- Insert Dummy Courses
INSERT INTO courses (courseName, courseDescription) VALUES 
('Java', 'Java Learning Course for intalio Task'), 
('JavaScript', 'JavaScript Learning Course for intalio Task'), 
('Python', 'Python Learning Course for intalio Task'), 
('C#', 'C# Learning Course for intalio Task');

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

-- Insert Dummy Modules
INSERT INTO modules (moduleName, courseID, pdfFileName, moduleDescription, fileGuid) VALUES 
('Introduction to Object-Oriented Programming', 1, 'java_intro.pdf', 'Java Learning Course for intalio Task', 'guid1'), 
('Advanced Java Programming', 1, 'java_advanced.pdf', 'Java Learning Course for intalio Task', 'guid2'), 
('JavaScript Basics', 2, 'javascript_basics.pdf', 'JavaScript Learning Course for intalio Task', 'guid3'), 
('Frontend Development with React', 2, 'react_frontend.pdf', 'JavaScript Learning Course for intalio Task', 'guid4'), 
('Python Fundamentals', 3, 'python_fundamentals.pdf', 'Python Learning Course for intalio Task', 'guid5'), 
('Data Analysis with Python', 3, 'python_data_analysis.pdf', 'Python Learning Course for intalio Task', 'guid6'), 
('C# Basics', 4, 'csharp_basics.pdf', 'C# Learning Course for intalio Task', 'guid7'), 
('.NET Framework Overview', 4, 'dotnet_overview.pdf', 'C# Learning Course for intalio Task', 'guid8');

-- Create Users Table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY, 
    username VARCHAR(50) NOT NULL UNIQUE, 
    password VARCHAR(255) NOT NULL, 
    role VARCHAR(10) NOT NULL
);

-- Insert Dummy Users
INSERT INTO users (username, password, role) VALUES 
('admin', SHA2('admin', 256), 'admin'), 
('student', SHA2('student', 256), 'student'), 
('student1', SHA2('student1', 256), 'student'), 
('student2', SHA2('student2', 256), 'student'), 
('instructor', SHA2('instructor', 256), 'student');
