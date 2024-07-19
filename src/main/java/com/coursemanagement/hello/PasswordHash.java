package com.coursemanagement.hello;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {
    public static void main(String[] args) {
        //String password = "your_admin_password_here";
    	String password = "intalio";
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        System.out.println(hashedPassword);
    }
}