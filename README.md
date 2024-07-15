# Course Module Management System

Web application with Maven + Java + Servlets + Tomcat

## Project Description

This is a simple web application that provides access to course modules in Java, JavaScript, and Python. Each module contains five PDF files with course topics that students can download.

## Project Structure

CourseModuleProject/
├── WebContent/
│ ├── META-INF/
│ ├── WEB-INF/
│ │ ├── lib/
│ │ ├── web.xml
│ ├── modules/
│ │ ├── java.html
│ │ ├── javascript.html
│ │ ├── python.html
│ ├── index.html
├── src/
│ ├── com.yourpackage.servlet/
│ │ ├── ModuleServlet.java
├── pom.xml



## Prerequisites

- Java JDK 8 or later
- Maven 3.6.0 or later
- Apache Tomcat 9
- Docker (optional for containerized deployment)

## Setup

1. **Clone the repository:**
   ```bash
   git clone <repository-url>
   cd CourseModuleProject

2. **Build the project:**

mvn clean install


The compiled file will be stored in the target folder as CourseModuleProject.war.

3. **Run the application with Tomcat**
mvn tomcat7:run

The servlet can be accessed in the browser at http://localhost:8080/CourseModuleProject.

Deployment
Running in Docker
Build the Docker image:
docker build -t course-module-project .

Run the Docker container:
docker run -i --rm --name course-module-project -p 8081:8080 \
  -v ${PWD}/target/CourseModuleProject.war:/usr/local/tomcat/webapps/CourseModuleProject.war \
  tomcat:9.0-jre8-alpine

3.Access the application:
Open your browser and go to http://localhost:8081/CourseModuleProject.

File Descriptions
index.html: Main page listing the course modules.
java.html, javascript.html, python.html: Module pages with links to PDF topics.
ModuleServlet.java: Servlet to handle file downloads.
web.xml: Servlet configuration file.
pom.xml: Maven configuration file.
Troubleshooting
404 Error: Ensure the project is properly deployed to Tomcat. Check the deployment directory and URL.
Maven Build Issues: Ensure all dependencies are correctly specified in pom.xml.
Author
Mohamed Abdelhamid

License
This project is licensed under the MIT License - see the LICENSE.md file for details.
