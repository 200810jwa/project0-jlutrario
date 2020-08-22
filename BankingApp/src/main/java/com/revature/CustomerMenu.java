package com.revature;

import java.util.Scanner;

import com.revature.models.Account;
import com.revature.models.Application;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.ApplicationService;
import com.revature.services.UserService;

public class CustomerMenu {

	private User u;

	public CustomerMenu(User u) {
		super();
		this.u = u;
	}

	public void home() {
		Scanner scan = new Scanner(System.in);
		boolean menu = true;
		
		while(menu) {
			System.out.println("|------------- CUSTOMER MENU -------------|");
			System.out.println("| [1] View Account                        |");
			System.out.println("| [2] Change Password                     |");
			System.out.println("| [3] Logout                              |");
			System.out.print  ("| Choose an action: ");
			String action = scan.nextLine();
			System.out.println("|                                         |");
			
			switch(action) {
			case "1":
				accountMenu();
				break;
			case "2":
				editMenu();
				break;
			case "3":
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

	private void accountMenu() {
		Scanner scan = new Scanner(System.in);
		AccountService accountService = new AccountService();
		ApplicationService appService = new ApplicationService();
		
		Account a = accountService.findAccount(u);
		Application app = appService.findApplication(u);
		System.out.println("|---------------- ACCOUNT ----------------|");
		System.out.println("|                                         |");
		if (a == null) {
			System.out.println("| No account was found.                   |");
			System.out.println("|                                         |");
			if (app == null) {
				System.out.println("| Want to apply for a new account?        |");
				System.out.println("| [1] Yes                                 |");
				System.out.println("| [2] No                                  |");
				System.out.print  ("| Choose an option: ");
				String option = scan.nextLine();
				
				switch (option) {
				case "1":
					if (appService.apply(u) != null) {
						System.out.println("|--------- APPLICATION SUBMITTED ---------|");
						System.out.println("|                                         |");
					} else {
						System.out.println("|----- APPLICATION SUBMISSION FAILED -----|");
						System.out.println("|------------ TRY AGAIN LATER ------------|");
						System.out.println("|                                         |");
					}
					break;
				case "2":
					break;
				default:
					System.out.println("|------------- NOT AN OPTION -------------|");
					System.out.println("|                                         |");
				}
			} else {	// application found
				System.out.println("| Application Status:                     |");
				System.out.print  ("| id: " + app.getId());
				if (app.isActive()) {
					System.out.println("| status: PENDING APPROVAL                |");
				} else {
					System.out.println("| status: DENIED                          |");
				}
			}
		} else {	// account found
			System.out.print  ("| id: " + a.getId());
			System.out.print  ("| balance: $" + a.getBalance());
			System.out.print  ("| owner: " + a.getOwner().getFirstname() + " " + a.getOwner().getLastname());
			System.out.println("|   [1] Withdraw                          |");
			System.out.println("|   [2] Deposit                           |");
			System.out.println("|   [3] Transfer                          |");
			System.out.println("|   [4] Close Account                     |");
			System.out.println("|   [5] Back                              |");
		}
		
		scan.close();
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
