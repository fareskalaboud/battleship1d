package battleships1d;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Thomas on 21/10/2014.
 */
public class UserManager {

	private static HashMap<String, User> users;

	/**
	 * Return the userPath;
	 * 
	 * @return
	 */
	private static String getUserPath() {
		try {
			return URLDecoder.decode(
					UserManager.class.getProtectionDomain().getCodeSource()
							.getLocation().getPath(), "UTF-8").substring(1)
					+ "users/";
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * Load the users from the file;
	 * 
	 * @throws IOException
	 */
	private static void loadUsers() throws IOException {
		users = new HashMap<String, User>();
		File[] userFiles = new File(getUserPath()).listFiles();
		for (File f : userFiles) {
			BufferedReader reader = new BufferedReader(new FileReader(
					f.getPath()));
			User user = new User();
			user.setUsername(reader.readLine());
			user.setPassword(reader.readLine());
			users.put(user.getUsername(), user);
		}
		System.out.println(users.size() + " users are loaded");
	}

	/**
	 * Initialize the users;
	 */
	public static void init() {
		try {
			loadUsers();
		} catch (IOException e) {
		}
	}

	/**
	 * Create a user and return if possible or not;
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static Boolean createUser(String username, String password) {
		if (userExists(username))
			return false;
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		users.put(username, user);

		try {
			File file = new File(getUserPath() + username + ".txt");
			file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(
					file.getPath()));
			writer.write(username + "\r\n" + password);
			writer.close();
		} catch (IOException e) {
		}
		return true;
	}

	/**
	 * Check if the password is ok;
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean checkPassword(String username, String password) {
		if (users.containsKey(username))
			return users.get(username).getPassword().equals(password);
		else
			return false;
	}

	/**
	 * 
	 * @param username
	 * @return a boolean if the user already exists;
	 */
	public static boolean userExists(String username) {
		return users.containsKey(username);
	}

	/**
	 * 
	 * @return a random number for the guestUserName;
	 */
	public static String generateGuestUsername() {
		Random random = new Random();
		return "guest-" + (random.nextInt(8999) + 1000);
	}

	/**
	 * Return the user Object;
	 * 
	 * @param username
	 * @return
	 */
	public static User getUserObject(String username) {
		if (!userExists(username))
			return null;
		return users.get(username);
	}
}
