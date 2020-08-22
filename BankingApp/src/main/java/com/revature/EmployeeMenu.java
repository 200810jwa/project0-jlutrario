package com.revature;

import java.util.Scanner;

import com.revature.models.User;
import com.revature.services.UserService;

public class EmployeeMenu {

	private User u;

	public EmployeeMenu(User u) {
		super();
		this.u = u;
	}
	
	public void home() {
		Scanner scan = new Scanner(System.in);
		boolean menu = true;
		
		while(menu) {
			System.out.println("|------------- EMPLOYEE MENU -------------|");
			System.out.println("| [1] View My Account                     |");
			System.out.println("| [2] View Customer Account               |");
			System.out.println("| [3] Approve/Deny Applications           |");
			System.out.println("| [4] Change Password                     |");
			System.out.println("| [5] Logout                              |");
			System.out.print  ("| Choose an action: ");
			String action = scan.nextLine();
			System.out.println("|                                         |");
			
			switch(action) {
			case "1":
				myAccountMenu();
				break;
			case "2":
				customerAccountMenu();
				break;
			case "3":
				approveDenyApps();
				break;
			case "4":
				editMenu();
				break;
			case "5":
				System.out.println("|-------------- LOGGING OUT --------------|");
				System.out.println("|                                         |");
				menu = false;
				break;
			default:
				System.out.println("|------------- NOT AN OPTION -------------|");
				System.out.println("|                                         |");
			}
		}
		scan.close();
	}
	
	private void myAccountMenu() {
		// TODO Auto-generated method stub
		
	}

	private void customerAccountMenu() {
		// TODO Auto-generated method stub
		
	}

	private void approveDenyApps() {
		// TODO Auto-generated method stub
		
	}

	private void editMenu() {
		Scanner scan = new Scanner(System.in);
		UserService userService = new UserService();
		
		int attempt = 0;
		while (attempt < 3) {
			System.out.println("|------------ CHANGE PASSWORD ------------|");
			System.out.println("|                                         |");
			System.out.print  ("| Enter Current Password: ");
			String curr_pass = scan.nextLine();
			attempt++;
			if (curr_pass.equals(u.getPassword())) {
				System.out.print  ("| Enter New Password: ");
				String new_pass = scan.nextLine();
				userService.changePassword(u.getId(), new_pass);
				System.out.println("|----- PASSWORD SUCCESSFULLY CHANGED -----|");
				System.out.println("|                                         |");
				attempt = 5;
			} else {
				System.out.println("|----------- INVALID PASSWORD ------------|");
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
		
		scan.close();
	}
	
}
