package com.revature.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.AccountDAO;
import com.revature.dao.IAccountDAO;
import com.revature.models.Account;
import com.revature.models.User;

public class AccountService {
	
	private IAccountDAO accountDAO;
	private static Logger log = Logger.getLogger(AccountService.class);
	
	public AccountService() {
		super();
		this.accountDAO = new AccountDAO();
	}
	
	public AccountService(IAccountDAO accountDAO) {
		super();
		this.accountDAO = accountDAO;
	}
	
	public Account openAccount(User user) {
		Account a = new Account(0, 0, user);
		
		int new_id = accountDAO.insert(a);
		
		if (new_id == 0) {
			log.info("Failed to open new account.");
			return null;
			// maybe throw custom exception here
		}
		a.setId(new_id);
		return a;
	}
	
	public boolean closeAccount(Account a) {
		if (a.getBalance() != 0) {
			return false;
		} else {
			return accountDAO.delete(a.getId());
		}
	}
	
	public boolean withdraw(Account account, double amount) {
		if (amount > 0 && account.getBalance()-amount >= 0) {
			account.setBalance(account.getBalance()-amount);
			
			accountDAO.update(account);
			
			log.info("Successful withdraw from account.");
			return true;
		} else {
			log.info("Failed to withdraw from account.");
			return false;
		}
	}
	
	public boolean deposit(Account account, double amount) {
		if (amount > 0) {
			account.setBalance(account.getBalance()+amount);
			
			accountDAO.update(account);
			
			log.info("Successful deposit to account.");
			return true;
		} else {
			log.info("Failed to deposit to account.");
			return false;
		}
	}
	
	public boolean transfer(Account source, Account target, double amount) {
		if (amount > 0 && source.getBalance()-amount >= 0) {
			withdraw(source, amount);
			deposit(target, amount);
			
			log.info("Successful transfer of funds.");
			return true;
		} else {
			log.info("Failed to transfer funds.");
			return false;
		}
	}
	
	public Account findAccount(User u) {
		List <Account> accounts = accountDAO.findAll();
		
		for (Account a : accounts) {
			if (a.getOwner().equals(u)) {
				return a;
			}
		}
		return null;
	}
}
