<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8d7da;
            color: #721c24;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }
        .error-container {
            background-color: #fff;
            padding: 20px;
            border: 1px solid #f5c6cb;
            border-radius: 5px;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
            text-align: center;
            max-width: 600px;
            width: 100%;
        }
        .error-container h1 {
            font-size: 2.5rem;
            margin-bottom: 10px;
        }
        .error-container h2 {
            font-size: 1.5rem;
            margin-bottom: 20px;
        }
        .error-container p {
            font-size: 1rem;
            margin-bottom: 10px;
        }
        .error-container .error-details {
            background-color: #f8d7da;
            padding: 10px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .error-container .suggestions {
            font-size: 0.9rem;
            color: #721c24;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <h1>Oops! Something went wrong.</h1>
        <h2><%= exception != null ? exception.getMessage() : "Error Login Credential" %></h2>
        <div class="error-details">
            <p>Please enter a valid username and password.</p>
            <p>For testing purposes,For Loggining as Admin (Instructor)you can use:</p>
            <p>Username: <strong>intalio</strong></p>
            <p>Password: <strong>intalio</strong></p>
        </div>
        <div class="suggestions">
            <p>If the problem persists, please contact our support team.</p>
            <p>Go back to the <a href="index.html">home page</a>.</p>
        </div>
    </div>
</body>
</html>
