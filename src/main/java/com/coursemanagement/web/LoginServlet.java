package com.coursemanagement.web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.coursemanagement.dao.UserDAO;
import com.coursemanagement.model.User;
import com.coursemanagement.utilities.PasswordUtil;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        // For demonstration purposes, print the parameters to the console
        System.out.println("Username: " + username);
        System.out.println(password);
        System.out.println("Role: " + role);

        //
        User user = userDAO.getUserByUsername(username);

        boolean flag1= PasswordUtil.checkPassword(password, user.getPassword()) ;
        boolean flag2 = role.equals(user.getRole());
     
        System.out.println(user != null && flag1 && flag2) ;
        
        if (user != null && flag1 && flag2) {
            if ("admin".equals(role)) {
                response.sendRedirect("adminPage.html");
            } else if ("student".equals(role)) {
                response.sendRedirect("student.html");
            } else {
                response.sendRedirect("Error.jsp");
            }
        } else {
            response.sendRedirect("Error.jsp");
        }
    }
}
