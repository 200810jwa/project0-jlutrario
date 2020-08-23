package com.revature;

import java.util.Scanner;
import org.apache.log4j.Logger;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.UserService;

public class BankDriver {

	private static Logger log = Logger.getLogger(BankDriver.class);
	private static Scanner scan = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		log.info("Application has started.");
		System.out.println("\n=========== WELCOME TO THE BANK ===========");
		System.out.println("|                                         |");
		boolean menu = true;
		
		while (menu) {
			System.out.println("|--------------- MAIN MENU ---------------|");
			System.out.println("| [1] Login                               |");
			System.out.println("| [2] Register                            |");
			System.out.println("| [3] Exit                                |");
			System.out.print  ("| Choose an action: ");
			String action = scan.nextLine();
			System.out.println("|                                         |");
			switch(action) {
			case "1":
				loginMenu();
				break;
			case "2":
				registerMenu();
				break;
			case "3":
				menu = false;
				break;
			default:
				System.out.println("|------------- NOT AN OPTION -------------|");
				System.out.println("|                                         |");
			}
		}
		System.out.println("|================ GOODBYE ================|");
		
		log.info("Application has ended.");
	}

	private static void loginMenu() {
		UserService userService = new UserService();
		
		int attempt = 0;
		while (attempt < 3) {
			System.out.println("|----------------- LOGIN -----------------|");
			System.out.println("|                                         |");
			System.out.print  ("| Enter Username: ");
			String username = scan.nextLine();
			System.out.print  ("| Enter Password: ");
			String password = scan.nextLine();
			System.out.println("|                                         |");
			
			User u = userService.login(username, password);
			
			if (u != null) {
				System.out.println("|----------- LOGIN SUCCESSFUL! -----------|");
				System.out.println("|                                         |");
				roleSwitch(u);
				attempt = 5;
			} else {
				System.out.println("|---------- INVALID CREDENTIALS ----------|");
				if (attempt < 2) {
					System.out.println("|--------------- TRY AGAIN ---------------|");
					System.out.println("|                                         |");
				}
				attempt++;
			}
		}
		
		if (attempt == 3) {
			System.out.println("|----------- TOO MANY ATTEMPTS -----------|");
			System.out.println("|------------ TRY AGAIN LATER ------------|");
			System.out.println("|                                         |");
		}
		
	}

	private static void registerMenu() {		
		UserService userService = new UserService();
		IUserDAO userDAO = new UserDAO();
		
		System.out.println("|--------------- REGISTER ----------------|");
		System.out.println("|                                         |");
		System.out.print  ("| Create Username: ");
		String username = scan.nextLine();
		if (userDAO.findByUsername(username) == null) {
			System.out.print  ("| Create Password: ");
			String password = scan.nextLine();
			System.out.print  ("| Enter First Name: ");
			String firstname = scan.nextLine();
			System.out.print  ("| Enter Last Name: ");
			String lastname = scan.nextLine();
			System.out.println("|                                         |");
			
			User u = userService.register(username, password, firstname, lastname, Role.Customer);
			
			System.out.println("|-------- REGISRATION SUCCESSFUL! --------|");
			System.out.println("|                                         |");
			roleSwitch(u);
		} else {
			System.out.println("|                                         |");
			System.out.println("|------------ USERNAME TAKEN -------------|");
			System.out.println("|---------- LOG IN OR TRY AGAIN ----------|");
			System.out.println("|                                         |");
		}
		
	}
	
	private static void roleSwitch(User u) {
		switch (u.getRole()) {
		
		case Customer:
			CustomerMenu customerMenu = new CustomerMenu(u);
			customerMenu.home();
			break;
		
		case Employee:
			EmployeeMenu employeeMenu = new EmployeeMenu(u);
			employeeMenu.home();
			break;
		
		case Admin:
			AdminMenu adminMenu = new AdminMenu(u);
			adminMenu.home();
			break;
		
		default:
			break;
		}
		
	}

}
