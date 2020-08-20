package com.revature.services;

import java.util.Scanner;
import org.apache.log4j.Logger;

public class AuthenticationService {

	private static Logger log = Logger.getLogger(AuthenticationService.class);
	
	public boolean login(String role) {
		Scanner scan = new Scanner(System.in);
		
		System.out.print("Enter Username: ");
		String username = scan.nextLine();
		
		System.out.print("Enter Password: ");
		String password = scan.nextLine();
		
		scan.close();
		
		return authenticate(username, password, role);
	}
	
	public boolean authenticate(String username, String password, String role) {
		if (checkUser(username) && checkPass(username, password))
			return true;
		log.info("Failed login attempt.");
		return false;
	}
	
	public boolean checkUser(String username) {
		log.info("Checking username.");
		if (/* username found */ username.equals(username)) {
			return true;
		} else {
			log.info("User not found.");
			return false;
		}
		
	}
	
	public boolean checkPass(String username, String password) {
		log.info("Checking password.");
		if (/* password is correct */ password.equals(username)) {
			return true;
		} else {
			log.info("Incorrect password.");
			return false;
		}
	}
}
