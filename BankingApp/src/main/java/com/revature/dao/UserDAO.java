package com.revature.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.utilities.ConnectionUtilities;

public class UserDAO implements IUserDAO {

	// We will follow a Data Access Object Design Pattern
	// This class will have instance methods whose responsibility is to 
	// perform common CRUD operations against the database as needed.
	
	// Common operations:
	//   - findAll
	//   - findByUsername
	//   - update
	//   - insert
	//   - delete
	
	// We will declare a method for each of the above operations
	// Because of this consistency in needed CRUD operations
	
	@Override
	public List<User> findAll() {
		List<User> allUsers = new ArrayList<>();
		
		try (Connection conn = ConnectionUtilities.getConnection()){
			
			Statement stmt = conn.createStatement();
			
			String sql = "SELECT * FROM project0.users";
			
			ResultSet rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				int id = rs.getInt("id");
				String username = rs.getString("username");
				String password = rs.getString("password");
				String firstname = rs.getString("firstname");
				String lastname = rs.getString("lastname");
				Role role = Role.valueOf(rs.getString("role"));
				
				User u = new User(id, username, password, firstname, lastname, role);
				allUsers.add(u);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO RETRIEVE ALL USERS");
			return null;
		}
		
		return allUsers;
	}

	@Override
	public User findByUsername(String username) {
		User u = null;
		
		try (Connection conn = ConnectionUtilities.getConnection()){
			
			String sql = "SELECT * FROM project0.users WHERE project0.users.username = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, username);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				u = new User();
				
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setFirstname(rs.getString("firstname"));
				u.setLastname(rs.getString("lastname"));
				u.setRole(Role.valueOf(rs.getString("role")));				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO RETRIEVE USER");
			return null;
		}
		
		return u;
		
	}

	@Override
	public int insert(User u) {
		try (Connection conn = ConnectionUtilities.getConnection()) {
			
			String sql = "INSERT INTO project0.users (username, password, firstname, lastname, role) VALUES (?, ?, ?, ?, ?) RETURNING project0.users.id";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getFirstname());
			stmt.setString(4, u.getLastname());
			stmt.setObject(5, u.getRole(), Types.OTHER);
			
			ResultSet rs;
			if ((rs = stmt.executeQuery()) != null) {
				rs.next();
				
				int id = rs.getInt(1);
				
				return id;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO INSERT USER");
		}
		return 0;	// invalid primary key
		
	}

	@Override
	public boolean update(User u) {
		try (Connection conn = ConnectionUtilities.getConnection()) {
			
			String sql = "UPDATE project0.users SET username = ?, password = ?, firstname = ?, lastname = ?, role = ? WHERE project0.users.id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, u.getUsername());
			stmt.setString(2, u.getPassword());
			stmt.setString(3, u.getFirstname());
			stmt.setString(4, u.getLastname());
			stmt.setObject(5, u.getRole(), Types.OTHER);
			stmt.setInt(6, u.getId());
			
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
	public boolean delete(int userId) {
		try (Connection conn = ConnectionUtilities.getConnection()) {
			
			String sql = "DELETE project0.users WHERE project0.users.id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, userId);
			
			if (stmt.executeUpdate(sql) != 0) {
				return true;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO DELETE USER");
		}
		return false;
	}

	@Override
	public User findByID(int id) {
		User u = null;
		
		try (Connection conn = ConnectionUtilities.getConnection()){
			
			String sql = "SELECT * FROM project0.users WHERE project0.users.id = ?";
			
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, id);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				u = new User();
				
				u.setId(rs.getInt("id"));
				u.setUsername(rs.getString("username"));
				u.setPassword(rs.getString("password"));
				u.setFirstname(rs.getString("firstname"));
				u.setLastname(rs.getString("lastname"));
				u.setRole(Role.valueOf(rs.getString("role")));				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO RETRIEVE USER");
			return null;
		}
		
		return u;
	}
	
}
