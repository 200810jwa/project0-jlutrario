package com.revature;

import java.util.List;
import java.util.Scanner;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Account;
import com.revature.models.Application;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.ApplicationService;
import com.revature.services.UserService;

public class AdminMenu {

	private Scanner scan = new Scanner(System.in);
	private User u;

	public AdminMenu(User u) {
		super();
		this.u = u;
	}
	
	public void home() {
		boolean menu = true;
		
		while(menu) {
			System.out.println("|-------------- ADMIN MENU ---------------|");
			System.out.println("| Hi, " + u.getFirstname() + "!");
			System.out.println("|                                         |");
			System.out.println("| [1] View My Account                     |");
			System.out.println("| [2] View Customer Account               |");
			System.out.println("| [3] Approve/Deny Applications           |");
			System.out.println("| [4] Change User Role                    |");
			System.out.println("| [5] Change My Password                  |");
			System.out.println("| [6] Logout                              |");
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
				changeUserRole();
				break;
			
			case "5":
				editMenu();
				break;
			
			case "6":
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
	
	private void changeUserRole() {
		UserDAO userDAO = new UserDAO();
		
		System.out.println("|----------- CHANGE USER ROLE ------------|");
		System.out.println("|                                         |");
		System.out.print  ("| Enter User's Username: ");
		String username = scan.nextLine();
		System.out.println("|                                         |");
		
		User user = userDAO.findByUsername(username);
		if (user != null) {
			switch (user.getRole()) {
			
			case Customer:
				System.out.println("| Change " + user.getFirstname() + " " + user.getLastname());
				System.out.println("| from 'Customer' to:                     |");
				System.out.println("|   [1] Admin                             |");
				System.out.println("|   [2] Employee                          |");
				System.out.print  ("| Choose an option: ");
				String cOption = scan.nextLine();
				System.out.println("|                                         |");
				
				switch (cOption) {
				case "1":
					user.setRole(Role.Admin);
					userDAO.update(user);
					System.out.println("|------- ROLE SUCCESSFULLY CHANGED -------|");
					System.out.println("|                                         |");
					break;
					
				case "2":
					user.setRole(Role.Employee);
					userDAO.update(user);
					System.out.println("|------- ROLE SUCCESSFULLY CHANGED -------|");
					System.out.println("|                                         |");
					break;
					
				default:
					System.out.println("|------------- NOT AN OPTION -------------|");
					System.out.println("|                                         |");
				}
				break;
			
			case Employee:
				System.out.println("| Change " + user.getFirstname() + " " + user.getLastname());
				System.out.println("| from 'Employee' to:                     |");
				System.out.println("|   [1] Admin                             |");
				System.out.println("|   [2] Customer                          |");
				System.out.print  ("| Choose an option: ");
				String eOption = scan.nextLine();
				System.out.println("|                                         |");
				
				switch (eOption) {
				case "1":
					user.setRole(Role.Admin);
					userDAO.update(user);
					System.out.println("|------- ROLE SUCCESSFULLY CHANGED -------|");
					System.out.println("|                                         |");
					break;
					
				case "2":
					user.setRole(Role.Customer);
					userDAO.update(user);
					System.out.println("|------- ROLE SUCCESSFULLY CHANGED -------|");
					System.out.println("|                                         |");
					
				default:
					System.out.println("|------------- NOT AN OPTION -------------|");
					System.out.println("|                                         |");
				}
				break;
			
			case Admin:
				System.out.println("| Change " + user.getFirstname() + " " + user.getLastname());
				System.out.println("| from 'Admin' to:                        |");
				System.out.println("|   [1] Employee                             |");
				System.out.println("|   [2] Customer                          |");
				System.out.print  ("| Choose an option: ");
				String aOption = scan.nextLine();
				System.out.println("|                                         |");
				
				switch (aOption) {
				case "1":
					user.setRole(Role.Employee);
					userDAO.update(user);
					System.out.println("|------- ROLE SUCCESSFULLY CHANGED -------|");
					System.out.println("|                                         |");
					break;
					
				case "2":
					user.setRole(Role.Customer);
					userDAO.update(user);
					System.out.println("|------- ROLE SUCCESSFULLY CHANGED -------|");
					System.out.println("|                                         |");
					
				default:
					System.out.println("|------------- NOT AN OPTION -------------|");
					System.out.println("|                                         |");
				}
				break;
			
			default:
				break;
			}
		} else {
			System.out.println("|------------ USER NOT FOUND -------------|");
			System.out.println("|                                         |");
		}
	}

	private void myAccountMenu() {
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

	private void customerAccountMenu() {
		UserDAO userDAO = new UserDAO();
		
		System.out.println("|--------- VIEW CUSTOMER ACCOUNT ---------|");
		System.out.println("|                                         |");
		System.out.print  ("| Enter Customer Username: ");
		String username = scan.nextLine();
		System.out.println("|                                         |");
		
		User customer = userDAO.findByUsername(username);
		if (customer != null) {
			System.out.println("| " + customer.getFirstname() + " " + customer.getLastname());
			
			AccountService accountService = new AccountService();
			ApplicationService appService = new ApplicationService();
			Account a = accountService.findAccount(customer);
			Application app = appService.findApplication(customer);
			
			if (a == null) {
				System.out.println("| No account was found.                   |");
				System.out.println("|                                         |");
				
				if (app == null) {
					System.out.println("| No open applications.                   |");
					System.out.println("|                                         |");
					
				} else {	// application found
					System.out.println("| Application Status:                     |");
					System.out.println("| id: " + app.getId());
					
					if (app.isActive()) {
						System.out.println("| status: PENDING APPROVAL                |");
						System.out.println("|                                         |");
						
					} else {
						System.out.println("| status: DENIED                          |");
						System.out.println("|                                         |");
					}
				}
			} else {
				System.out.println("|                                         |");
				System.out.println("| id: " + a.getId());
				System.out.println("| balance: $" + a.getBalance());
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
					System.out.println("| Enter target's username: ");
					String tUsername = scan.nextLine();
					User targetUser = userDAO.findByUsername(tUsername);
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
		} else {
			System.out.println("|---------- CUSTOMER NOT FOUND -----------|");
			System.out.println("|                                         |");
		}
		
	}

	private void approveDenyApps() {
		ApplicationService appService = new ApplicationService();
		
		List<Application> activeApps = appService.viewActiveApplications();
		
		System.out.println("|------- APPROVE/DENY APPLICATIONS -------|");
		System.out.println("|                                         |");
		
		if (activeApps.size() > 0) {
			for (Application a : activeApps) {
				System.out.println("| application id: " + a.getId());
				System.out.println("| " + a.getOwner().getFirstname() + " " + a.getOwner().getLastname());
				System.out.println("| username: " + a.getOwner().getUsername());
				System.out.println("| [1] Approve                             |");
				System.out.println("| [2] Deny                                |");
				System.out.println("| [3] Skip                                |");
				System.out.print  ("| Choose an action: ");
				String action = scan.nextLine();
				System.out.println("|                                         |");
				
				switch (action) {
				
				case "1":
					if (appService.approveApplication(a)) {
						System.out.println("|--------- APPLICATION APPROVED! ---------|");
						System.out.println("|                                         |");
					} else {
						System.out.println("|----- COULD NOT APPROVE APPLICATION -----|");
						System.out.println("|                                         |");
					}
					break;
				
				case "2":
					appService.denyApplication(a);
					System.out.println("|----------- APPLICATION DENIED ----------|");
					System.out.println("|                                         |");
					break;
				
				case "3":
					break;
					
				default:
					System.out.println("|------------- NOT AN OPTION -------------|");
					System.out.println("|                                         |");
				}
			}
		} else {
			System.out.println("| No active applications.                 |");
			System.out.println("|                                         |");
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
				System.out.println("|                                         |");
				String pw = scan.nextLine();
				if (u.getPassword().equals(pw)) {
					if (a.getBalance() != 0) {
						System.out.println("|--------- ACCOUNT MUST BE EMPTY ---------|");
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
