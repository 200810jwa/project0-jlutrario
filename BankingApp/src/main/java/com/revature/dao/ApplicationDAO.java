package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.Application;
import com.revature.models.User;
import com.revature.utilities.ConnectionUtilities;

public class ApplicationDAO implements IApplicationDAO {

	private IUserDAO userDAO = new UserDAO();
	
	@Override
	public List<Application> findAll() {
		List<User> allUsers = userDAO.findAll();	// Potentially unsorted
		
		List<Application> allApplications = new ArrayList<>();
		
		try (Connection conn = ConnectionUtilities.getConnection()){
			
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM project0.applications";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				int ownerID = rs.getInt("owner");
				boolean active = rs.getBoolean("active");
				
				// find the User object in allUsers that matches the ownerID
				User owner = null;
				//If there is no owner, then the Application object will have a null value for the owner
				for (int i = 0; i < allUsers.size(); i++) {
					if (allUsers.get(i).getId() == ownerID)  {
						owner = allUsers.get(i);
					}
				}
				
				Application a = new Application(id, owner, active);
				
				allApplications.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO RETRIEVE ALL APPLICATIONS");
			return null;
		}
		
		return allApplications;
	}

	@Override
	public Application findById(int id) {
		Application a = null;
		
		try (Connection conn = ConnectionUtilities.getConnection()){
			
			String sql = "SELECT * FROM project0.application WHERE project0.application.id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				a = new Application();
				
				a.setId(rs.getInt("id"));
				a.setOwner(userDAO.findByID(rs.getInt("owner")));
				a.setActive(rs.getBoolean("active"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO RETRIEVE APPLICATION");
			return null;
		}
		
		return a;
	}

	@Override
	public int insert(Application a) {
		String sql = "INSERT INTO project0.applications (owner, active) VALUES (?, ?) RETURNING project0.applications.id";
		
		try(Connection conn = ConnectionUtilities.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, a.getOwner().getId());
			stmt.setBoolean(2, a.isActive());
			
			ResultSet rs;
			if((rs = stmt.executeQuery()) != null) {
				rs.next();
				
				int id = rs.getInt(1);
				
				return id;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO INSERT APPLICATION");
		}
	
		return 0; // Invalid primary key
	}

	@Override
	public boolean update(Application a) {
		try (Connection conn = ConnectionUtilities.getConnection()) {
			
			String sql = "UPDATE project0.applications SET owner = ?, active = ? WHERE project0.application.id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, a.getOwner().getId());
			stmt.setBoolean(2, a.isActive());
			stmt.setInt(3, a.getId());
			
			if (stmt.executeUpdate(sql) != 0) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO UPDATE APPLICATION");
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try (Connection conn = ConnectionUtilities.getConnection()) {
			
			String sql = "DELETE project0.applications WHERE project0.applications.id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			if (stmt.executeUpdate(sql) != 0) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO DELETE APPLICATION");
		}
		return false;
	}

	
}
