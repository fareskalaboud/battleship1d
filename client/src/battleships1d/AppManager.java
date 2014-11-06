package battleships1d;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * 
 * @author faresalaboud
 * 
 */
public class AppManager {
	// UI Variables
	private static GameFrame frame;

	// Player Variables
	private static String mainPlayer;

	// Lobby Variables
	private static Vector<RoomData> publicRooms;
	private static Vector<RoomData> privateRooms;

	// Game Variables
	private boolean isLocalMove;
	private Room openRoom;

	// UI Management

	/**
	 * Set up the main GameFrame (subclass of JFrame)
	 * 
	 * @see battleships1d.GameFrame
	 * @author faresalaboud
	 */
	public static void setUpUI() {
		frame = new GameFrame("Battleships 1D");
	}

	// Player Management

	/**
	 * Returns the local player.
	 * 
	 * @return the mainPlayer object
	 * @author faresalaboud
	 */
	public static String getMainPlayer() {
		return mainPlayer;
	}

	/**
	 * Checks the server if the player's login details are correct or not.
	 * 
	 * @param username
	 *            Player's username
	 * @param password
	 *            Player's password
	 * 
	 * @return the server response
	 */
	public static String checkPlayerLoginDetails(String username,
			String password) {

		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Login::User::Successful::");
		commands.add("Login::User::Error::Username");
		commands.add("Login::User::Error::Password");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		Server.writeLineToServer("Login::User::" + username + "::" + password);
		waitOnThread(rv);

		return rv.getCommand();

		// TODO: Send to server, and fix line below
		// String response = "hello";
		//
		// String[] serverResponse = response.split("::");
		// if(serverResponse[2].equals("Successful")) {
		// return "Successful";
		// } else if (serverResponse[2].equals("Error")){
		// if (serverResponse.length == 3) {
		// return "Error";
		// } else {
		// return "Error::" + username;
		// }
		// }
		// return "Error";
	}

	public static String createAccount(String username, String password) {

		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Login::Create::Successful::");
		commands.add("Login::Create::Error::Username");
		commands.add("Login::Create::Error");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		Server.writeLineToServer("Login::Create::" + username + "::" + password);
		waitOnThread(rv);

		// TODO: Send to server, and fix line below
		String response = rv.getCommand();

		String[] serverResponse = response.split("::");
		if (serverResponse[2].equals("Successful")) {
			return "Successful";
		} else if (serverResponse[2].equals("Error")) {
			if (serverResponse.length == 3) {
				return "Error";
			}
		} else {
			return "ErrorU";
		}
		return "Error";
	}

	/**
	 * Sets the main player according to mainPlayer's current value (if it's
	 * null, assign it a Guest value; else assign it the logged in user.)
	 * 
	 * @author faresalaboud
	 */
	public static void setMainPlayer() {
		if (mainPlayer == null) {
			// If mainPlayer is null, then the game has only just booted up.
			// TODO: Obtain guest player object from server and assign it to
			// mainPlayer
		} else {
			// If mainPlayer isn't null, it is most likely a Guest user.
			// TODO: Assign logged-in player to mainPlayer
		}
	}

	// Server Management

	/**
	 * Obtains a list of the rooms from the server and adds them to the public
	 * and private room Vectors.
	 * 
	 * @author faresalaboud
	 */

	public static void getRoomsFromServer() {
		// TODO: Remove initialisation, obtain rooms from server
		Vector<RoomData> allRooms = new Vector<RoomData>();

		// TODO: Command.getParameters
		String[] parameters = new String[0];
		RoomData r;

		int noOfRooms = Integer.parseInt(parameters[0]);
		String roomID = new String();
		String userName = new String();
		String password = new String();

		for (int i = 1; i < parameters.length; i++) {
			if (i % 3 == 1) {
				roomID = parameters[i];
			} else if (i % 3 == 2) {
				userName = parameters[i];
			} else {
				password = parameters[i];
				if (password.equals("")) {
					r = new RoomData(roomID, userName, "");
					allRooms.add(r);
					publicRooms.add(r);
				} else {
					r = new RoomData(roomID, userName, password);
					allRooms.add(r);
					privateRooms.add(r);
				}
			}
		}
	}

	/**
	 * Returns a Vector of public rooms.
	 * 
	 * @return a Vector of public rooms
	 * @author faresalaboud
	 */
	public static Vector<RoomData> getPublicRooms() {
		return publicRooms;
	}

	/**
	 * Returns a Vector of private rooms.
	 * 
	 * @return a Vector of private rooms
	 * @author faresalaboud
	 */
	public static Vector<RoomData> getPrivateRooms() {
		return privateRooms;
	}

	/**
	 * 
	 * @param row
	 * @param col
	 * @author Alexander Hanbury-Botherway
	 */
	public Result playButton(int row, int col) {

		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Game::Fire::Hit");
		commands.add("Game::Fire::Miss");
		commands.add("Game::Fire::Sunk::");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		Server.writeLineToServer("Game::Fire::" + row + "::" + col);
		waitOnThread(rv);

		isLocalMove = false;

		String returnedResult = rv.getCommand();

		if (returnedResult.equals("Game::Miss")) {
			isLocalMove = false;
			return Result.MISS;
		}
		if (returnedResult.equals("Game::Hit")) {
			isLocalMove = false;
			new JDialog(new JFrame(), "You hit an enemy ship");
			return Result.HIT;
		}
		if (returnedResult.equals("Game::Sunk")) {
			isLocalMove = false;
			String shipName = returnedResult.substring(19);
			new JDialog(new JFrame(), "You sunk the enemie's " + shipName + "!");
			return Result.SUNK;
		}
		System.err.println("Result from button: " + row + " " + col
				+ " is not valid");
		return null;
	}

	/**
	 * Called if server sends a players move
	 * 
	 * @author Alexander Hanbury-Botherway
	 * @param rv
	 * @return
	 */
	public void sendMove() {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Game::Fired::");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		waitOnThread(rv);

		String returnedResult = rv.getCommand();

		int row = Integer.parseInt(returnedResult.substring(14, 14));
		int col = Integer.parseInt(returnedResult.substring(17, 17));

		LocalButton targetButton = openRoom.getLocalMap().getClikedButton(row,
				col);

		Result result = targetButton.playButton();

		if (result.equals(Result.HIT)) {
			new JDialog(new JFrame(), "You've been hit!");
			isLocalMove = true;
			return;
		}
		if (result.equals(Result.SUNK)) {
			String shipName = targetButton.getShip().getName();
			new JDialog(new JFrame(), "The enemy has sunk your " + shipName
					+ "!");
			isLocalMove = true;
			return;
		}
		if (result.equals(Result.MISS)) {
			new JDialog(new JFrame(), "Enemy failed to hit a ship: Your move!");
			isLocalMove = true;
			return;
		} else {
			System.err
					.println("Unexpected response from local button after being hit");
			return;
		}
	}

	private static Boolean waitOnThread(Server.RequestVariables rv) {
		while (!rv.getContinueThread()) {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public static void main(String args[]) {
		if (Server.connectToServer()) {
			setUpUI();
			setMainPlayer();
			// getRoomsFromServer();
			// frame.setVisible(true);
			new LogIn().setUpUI();

		} else {

			JOptionPane.showMessageDialog(new JFrame(),
					"Please ensure you have a working internet connection.",
					"Error: Unable to connect to server",
					JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}

	}

	/**
	 * @author Alexander Hanbury-Botherway
	 * @param password
	 * @return
	 */
	public static String createRoom(String password) {

		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Room::Create::");
		commands.add("Room::Create::Error::UserInARoom");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		Server.writeLineToServer("Room::Create::" + password);
		waitOnThread(rv);

		String returnedResult = rv.getCommand();

		if (returnedResult.equals("Room::Create::")) {
			return returnedResult.substring(13);
		}
		if (returnedResult.equals("Room::Create:Error:UserInARoom")) {
			return "Error: User In Room";
		}
		return "Error";
	}

}