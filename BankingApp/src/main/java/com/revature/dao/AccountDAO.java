package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.utilities.ConnectionUtilities;

public class AccountDAO implements IAccountDAO {

	private IUserDAO userDAO = new UserDAO();

	@Override
	public List<Account> findAll() {
		List<User> allUsers = userDAO.findAll();	// Potentially unsorted
		
		List<Account> allAccounts = new ArrayList<>();
		
		try (Connection conn = ConnectionUtilities.getConnection()){
			
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM project0.accounts";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				double balance = rs.getDouble("balance");
				int ownerID = rs.getInt("owner");
				
				// find the User object in allUsers that matches the ownerID
				User owner = null;
				//If there is no owner, then the Account object will have a null value for the owner
				for (int i = 0; i < allUsers.size(); i++) {
					if (allUsers.get(i).getId() == ownerID)  {
						owner = allUsers.get(i);
					}
				}
				
				Account a = new Account(id, balance, owner);
				
				allAccounts.add(a);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO RETRIEVE ALL ACCOUNTS");
			return null;
		}
		
		return allAccounts;
	}

	@Override
	public Account findById(int id) {
		Account a = null;
		
		try (Connection conn = ConnectionUtilities.getConnection()){
			
			String sql = "SELECT * FROM project0.accounts WHERE project0.accounts.id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				a = new Account();
				
				a.setId(rs.getInt("id"));
				a.setBalance(rs.getDouble("balance"));
				a.setOwner(userDAO.findByID(rs.getInt("owner")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO RETRIEVE ACCOUNT");
			return null;
		}
		
		return a;
	}

	@Override
	public int insert(Account a) {
		String sql = "INSERT INTO project0.accounts (balance, owner) VALUES (?, ?) RETURNING project0.accounts.id";
		
		try(Connection conn = ConnectionUtilities.getConnection()) {
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, a.getBalance()); // PreparedStatement will prevent any content from
			// being executed as SQL
			stmt.setInt(2, a.getOwner().getId());
			
			ResultSet rs;
			if((rs = stmt.executeQuery()) != null) {
				rs.next();
				
				int id = rs.getInt(1);
				
				return id;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("WE FAILED TO INSERT ACCOUNT");
		}
	
		return 0; // Invalid primary key
	}

	@Override
	public boolean update(Account a) {
		try (Connection conn = ConnectionUtilities.getConnection()) {
			
			String sql = "UPDATE project0.accounts SET balance = ?, owner = ? WHERE project0.accounts.id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setDouble(1, a.getBalance());
			stmt.setInt(2, a.getOwner().getId());
			stmt.setInt(3, a.getId());
			
			if (stmt.executeUpdate(sql) != 0) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO UPDATE USER");
		}
		return false;
	}

	@Override
	public boolean delete(int id) {
		try (Connection conn = ConnectionUtilities.getConnection()) {
			
			String sql = "DELETE project0.accounts WHERE project0.accounts.id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			if (stmt.executeUpdate(sql) != 0) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO DELETE USER");
		}
		return false;
	}
	
}
