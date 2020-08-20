package com.revature.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * The Connection utility class that is designed to obtain a connection to the database
 * is often structured as a Singleton
 */

public class ConnectionUtilities {

	private static Connection conn = null;
	
	private ConnectionUtilities() {
		super();
	}
	
	public static Connection getConnection() {
		try {
			
			if (conn != null && !conn.isClosed()) {
				return conn;
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO REUSE A CONNECTION");
			return null;
		}
		
		// In order to obtain a Connection, we will need the credentials to our DB
		// as well as the location of our DB.
		// The location of the DB is represented as a "Connection String"
		// This includes information such as what SQL driver we are using, what the
		// ip address is, what the port is, and what the database name is
		
		// The driver is going to be based on postgres, and it will specifically be
		// "jdbc:postgresql:"
		
		// jdbc:postgresql://host_name:port/DB_name
		String url = "jdbc:postgresql://training.cub6v59od0nw.us-east-1.rds.amazonaws.com:5432/jwa200810";
		
		// We directly have our username and password of our database written directly into
		// our sourcecode. When pushed to GitHub, credentials are exposed to the world.
		String username = "root";
		String password = "password";
		
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("FAILED TO GET CONNECTION");
			return null;
		}
		
		return conn;
	}
}
