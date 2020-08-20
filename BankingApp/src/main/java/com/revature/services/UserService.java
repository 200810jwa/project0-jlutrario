package com.revature.services;

import org.apache.log4j.Logger;

import com.revature.dao.IUserDAO;
import com.revature.dao.UserDAO;
import com.revature.models.Role;
import com.revature.models.User;

public class UserService {

	private IUserDAO userDAO;
	private static Logger log = Logger.getLogger(UserService.class);
	
	public UserService() {
		super();
		this.userDAO = new UserDAO();
	}
	
	public UserService(IUserDAO userDAO) {
		super();
		this.userDAO = userDAO;
	}

	public User login(String username, String password) {
		User u = userDAO.findByUsername(username);
		
		if (u.getPassword().equals(password)) {
			return u;
		}
		
		log.info("Login attempt failed.");
		return null;
	}
	
	public User register(String username, String password, String firstname, String lastname, Role role) {
		User u = new User(0, username, password, firstname, lastname, role);
		
		int new_id = userDAO.insert(u);
		
		if (new_id == 0) {
			log.info("Failed to register new user.");
			return null;
			// maybe throw custom exception here
		}
		u.setId(new_id);
		return u;
	}

	public boolean changePassword(int id, String newPassword) {
		User u = userDAO.findByID(id);
		
		u.setPassword(newPassword);
		
		return userDAO.update(u);
	}
	
	public boolean changeRole(int id, Role newRole) {
		User u = userDAO.findByID(id);
		
		u.setRole(newRole);
		
		return userDAO.update(u);
	}
	
}
