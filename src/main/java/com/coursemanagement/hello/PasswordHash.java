package com.coursemanagement.hello;

import org.mindrot.jbcrypt.BCrypt;

public class PasswordHash {
	public static void main(String[] args) {
		// String password = "your_admin_password_here";
		String password = "admin";
		String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
		System.out.println(hashedPassword);

		System.out.println(checkPassword(password, hashedPassword));

	}

	public String getHashedPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	// Hash a password for the first time
	public static String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	// Check that a plaintext password matches a previously hashed one
	public static boolean checkPassword(String plaintext, String hashed) {
		return BCrypt.checkpw(plaintext, hashed);
	}

}