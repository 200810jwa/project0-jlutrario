package com.revature.services;

import org.apache.log4j.Logger;

import com.revature.models.Account;
import com.revature.models.User;

public class AccountService {
	
	private static Logger log = Logger.getLogger(AccountService.class);
	
	public void withdraw(Account account) {
		account.getBalance();
	}
	
	public void deposit(Account account) {
		account.getBalance();
	}
	
	public Account applyForAccount(User u) {
		return null;
	}
}
