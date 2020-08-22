package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.revature.dao.ApplicationDAO;
import com.revature.dao.IApplicationDAO;
import com.revature.models.Account;
import com.revature.models.Application;
import com.revature.models.User;

public class ApplicationService {

	private IApplicationDAO applicationDAO;
	private static Logger log = Logger.getLogger(ApplicationService.class);
	
	public ApplicationService() {
		super();
		this.applicationDAO = new ApplicationDAO();
	}
	
	public ApplicationService(IApplicationDAO applicationDAO) {
		super();
		this.applicationDAO = applicationDAO;
	}
	
	public Application findApplication(User u) {
		List <Application> applications = applicationDAO.findAll();
		
		for (Application a : applications) {
			if (a.getOwner().equals(u)) {
				return a;
			}
		}
		return null;
	}
	
	public List<Application> viewActiveApplications() {
		List<Application> activeApps = new ArrayList<>();
		
		List <Application> applications = applicationDAO.findAll();
		for (Application a : applications) {
			if (a.isActive()) {
				activeApps.add(a);
			}
		}
		return activeApps;
	}
	
	public boolean approveApplication(Application app) {
		AccountService accountService = new AccountService();
		Account acc = accountService.openAccount(app.getOwner());
		return applicationDAO.delete(app.getId());
	}
	
	public boolean denyApplication(Application app) {
		app.setActive(false);
		return applicationDAO.update(app);
	}
	
	public Application apply(User u) {
		Application a = new Application(0, u, true);
		
		int new_id = applicationDAO.insert(a);
		
		if (new_id == 0) {
			log.info("Failed to submit application.");
			return null;
		}
		a.setId(new_id);
		
		return a;
	}
}
