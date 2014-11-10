package battleships1d;

import java.util.Random;

/**
 * Created by Thomas on 21/10/2014.
 */
public class User {

	private Connection connection;
	private String username;
	private String password;
	private boolean loggedIn;

	/**
	 * Check if there is any connection
	 * 
	 * @return - boolean;
	 */
	public Boolean hasConnection() {
		return connection == null;
	}

	/**
	 * 
	 * @return - the connection;
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Set the connection;
	 * 
	 * @param connection
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	/**
	 * 
	 * @return a String with the UserName;
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Set the userName;
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return the password of the user;
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Return the password of the user;
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Return a boolean if is logged in or nat;
	 * 
	 * @return
	 */
	public boolean getLoggedIn() {
		return loggedIn;
	}

	/**
	 * Set the Log in;
	 * 
	 * @param loggedIn
	 */
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}
}
