package com.revature;

import java.util.Scanner;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Account;
import com.revature.models.Application;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.ApplicationService;
import com.revature.services.UserService;

public class CustomerMenu {

	private User u;
	private Scanner scan = new Scanner(System.in);

	public CustomerMenu(User u) {
		super();
		this.u = u;
	}

	public void home() {
		boolean menu = true;
		
		while(menu) {
			System.out.println("|------------- CUSTOMER MENU -------------|");
			System.out.println("| Hi, " + u.getFirstname() + "!");
			System.out.println("|                                         |");
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
	}

	private void accountMenu() {
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
					System.out.println("|------------ TRY AGAIN LATER ------------|");
					System.out.println("|                                         |");
				}
				
			} else {	// application found
				System.out.println("| Application Status:                     |");
				System.out.println("| id: " + app.getId());
				
				if (app.isActive()) {
					System.out.println("| status: PENDING APPROVAL                |");
					System.out.println("|                                         |");
				
				} else {
					System.out.println("| status: DENIED                          |");
					System.out.println("|                                         |");
					System.out.println("| Want to remove this application?        |");
					System.out.println("| [1] Yes                                 |");
					System.out.println("| [2] No                                  |");
					System.out.print  ("| Choose an option: ");
					String option = scan.nextLine();
					
					switch (option) {
					
					case "1":
						if (appService.removeApplication(app)) {
							System.out.println("|---------- APPLICATION REMOVED ----------|");
							System.out.println("|                                         |");
						} else {
							System.out.println("|------ COULD NOT REMOVE APPLICATION -----|");
							System.out.println("|                                         |");
						}
						break;
					
					case "2":
						break;
					
					default:
						System.out.println("|------------- NOT AN OPTION -------------|");
						System.out.println("|                                         |");
					}
				}
			}
		
		} else {	// account found
			transactionMenu(a);
		}
		
	}

	private void editMenu() {
		UserService userService = new UserService();
		
		int attempt = 0;
		while (attempt < 3) {
			
			System.out.println("|------------ CHANGE PASSWORD ------------|");
			System.out.println("|                                         |");
			System.out.print  ("| Enter Current Password: ");
			String curr_pass = scan.nextLine();
			attempt++;
			
			if (u.getPassword().equals(curr_pass)) {
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
		
	}
	
	private void transactionMenu(Account a) {
		AccountService accountService = new AccountService();
		IUserDAO userDAO = new UserDAO();
		
		System.out.println("|                                         |");
		System.out.println("| id: " + a.getId());
		System.out.println("| balance: $" + a.getBalance());
		System.out.println("| owner: " + a.getOwner().getFirstname() + " " + a.getOwner().getLastname());
		System.out.println("|   [1] Withdraw                          |");
		System.out.println("|   [2] Deposit                           |");
		System.out.println("|   [3] Transfer                          |");
		System.out.println("|   [4] Close Account                     |");
		System.out.println("|   [5] Back                              |");
		System.out.print  ("|   Choose an action: ");
		String action = scan.nextLine();
		System.out.println("|                                         |");
		
		switch (action) {
		
		case "1":
			System.out.println("|--------------- WITHDRAW ----------------|");
			System.out.println("|                                         |");
			System.out.println("| current balance: $" + a.getBalance());
			System.out.print  ("| Enter withdraw amount: ");
			double wAmount = Double.parseDouble(scan.nextLine());
			if (accountService.withdraw(a, wAmount)) {
				System.out.println("|--------- WITHDRAW SUCCESSFUL! ----------|");
				System.out.println("|                                         |");
			} else {
				System.out.println("|------- COULD NOT WITHDRAW FUNDS --------|");
				System.out.println("|                                         |");
			}
			break;
		
		case "2":
			System.out.println("|---------------- DEPOSIT ----------------|");
			System.out.println("|                                         |");
			System.out.println("| current balance: $" + a.getBalance());
			System.out.print  ("| Enter deposit amount: ");
			double dAmount = Double.parseDouble(scan.nextLine());
			System.out.println("|                                         |");
			
			if (accountService.deposit(a, dAmount)) {
				System.out.println("|---------- DEPOSIT SUCCESSFUL! ----------|");
				System.out.println("|                                         |");
			} else {
				System.out.println("|-------- COULD NOT DEPOSIT FUNDS --------|");
				System.out.println("|                                         |");
			}
			break;
		
		case "3":
			System.out.println("|---------------- TRANSFER ---------------|");
			System.out.println("|                                         |");
			System.out.println("| current balance: $" + a.getBalance());
			System.out.print  ("| Enter amount to transfer: ");
			double tAmount = Double.parseDouble(scan.nextLine());
			System.out.print  ("| Enter target's username: ");
			String username = scan.nextLine();
			User targetUser = userDAO.findByUsername(username);
			if (targetUser != null) {
				Account target = accountService.findAccount(targetUser);
				if (target != null) {
					if (accountService.transfer(a, target, tAmount)) {
						System.out.println("|---------- TRANSFER SUCCESSFUL! ---------|");
						System.out.println("|                                         |");
					} else {
						System.out.println("|------- COULD NOT TRANSFER FUNDS --------|");
						System.out.println("|                                         |");
					}
				} else {
					System.out.println("|--------- COULD NOT FIND ACCOUNT --------|");
					System.out.println("|                                         |");
				}
			} else {
				System.out.println("|---------- COULD NOT FIND USER ----------|");
				System.out.println("|                                         |");
			}
			break;
		
		case "4":
			System.out.println("|------------- CLOSE ACCOUNT -------------|");
			System.out.println("|                                         |");
			System.out.println("| Are you sure?                           |");
			System.out.println("| [1] Yes                                 |");
			System.out.println("| [2] No                                  |");
			System.out.print  ("| Enter choice: ");
			String choice = scan.nextLine();
			
			switch (choice) {
			
			case "1":
				System.out.print  ("| Enter password: ");
				String pw = scan.nextLine();
				System.out.println("|                                         |");
				
				if (u.getPassword().equals(pw)) {
					if (a.getBalance() != 0) {
						System.out.println("|-------- MUST EMPTY ACCOUNT FIRST -------|");
						System.out.println("|                                         |");
					} else if (accountService.closeAccount(a)) {
						System.out.println("|------ ACCOUNT SUCCESSFULLY CLOSED ------|");
						System.out.println("|                                         |");
					} else {
						System.out.println("|-------- COULD NOT CLOSE ACCOUNT --------|");
						System.out.println("|                                         |");
					}
				} else {
					System.out.println("|----------- INVALID PASSWORD ------------|");
					System.out.println("|                                         |");
				}
				break;
			
			case "2":
				break;
			
			default:
				System.out.println("|------------- NOT AN OPTION -------------|");
				System.out.println("|                                         |");
			}
			break;
		
		case "5":
			break;
		
		default:
			System.out.println("|------------- NOT AN OPTION -------------|");
			System.out.println("|                                         |");
		}
		
	}
}
