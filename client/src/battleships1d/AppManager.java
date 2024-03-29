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

	// Main menu
	private MainMenu mainMenu;

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
	 * Getting a random number for the guest from the server
	 * 
	 * @author GEORGE RADUTA
	 */
	public static String checkGuest() {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Login::Guest::");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		Server.writeLineToServer("Login::Guest");
		waitOnThread(rv);
		return rv.getCommand();
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
	}

	/**
	 * Creating a new Account
	 * 
	 * @param username
	 *            - String;
	 * @param password
	 *            - String;
	 * @return - String from the server with the answer ( Error/Valid);
	 */
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

	// Server Management

	/**
	 * Obtains a list of the rooms from the server and adds them to the public
	 * and private room Vectors.
	 * 
	 * @author faresalaboud
	 */
	public static Vector<RoomData> getRoomsFromServer() {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Room::");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		Server.writeLineToServer("Room::List");
		waitOnThread(rv);

		String returnedResult = rv.getCommand();

		System.out.println(returnedResult);

		Vector<RoomData> allRooms = new Vector<RoomData>();

		String[] returnedArray = returnedResult.split("::");

		System.out.println(returnedArray[0] + "0"); // Room
		System.out.println(returnedArray[1] + "1"); // Count

		int roomCount = Integer.parseInt(returnedArray[1]);

		for (int i = 0; i < roomCount; i++) {
			String roomID = returnedArray[2 + (i * 3)];
			String host = returnedArray[3 + (i * 3)];
			String password = returnedArray[4 + (i * 3)];
			System.out.println(roomID + host + password);
			allRooms.add(new RoomData(roomID, host, password));
		}

		return allRooms;
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
		String shipHasBeenSunked = returnedResult.substring(0, 15);

		if (returnedResult.equals("Game::Fire::Miss")) {
			isLocalMove = false;
			System.out.println("You missed");
			return Result.MISS;
		}
		if (returnedResult.equals("Game::Fire::Hit")) {
			isLocalMove = false;
			new JDialog(new JFrame(), "You hit an enemy ship");
			System.out.println("You actually hit something");
			return Result.HIT;
		}
		if (shipHasBeenSunked.equals("Game::Fire::Sun")) {
			isLocalMove = false;
			String shipName = returnedResult.substring(18,
					returnedResult.length());
			new JDialog(new JFrame(), "You sunk the enemie's " + shipName + "!");
			System.out.println(shipName);
			return Result.SUNK;
		}
		if (returnedResult.equals("Game::Win")) {
			isLocalMove = false;
			new JDialog(new JFrame(), "You win mate");
			System.out.println("u win");
		}

		if (returnedResult.equals("Game::Loss")) {
			isLocalMove = false;
			new JDialog(new JFrame(), "lel u lost");
			System.out.println("u lost");
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

	/**
	 * Called when a host creates a room, waits for guest to join the room
	 * 
	 * @author Alexander Hanbury-Botherway
	 * @return
	 */
	public String waitForGuest() {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Room::Player::Joined");

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

		returnedResult += "::";

		String guestUserName = returnedResult.split(":")[3];

		return guestUserName;
	}

	/**
	 * test this method after they established creation of rooms
	 * 
	 * @param ships
	 */
	public void setShips(Ship[][] ships) {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Game::Setup::Ship::Success");
		commands.add("Game::Setup::Ship::Error::InvalidY");
		commands.add("Game::Setup::Ship::Error::InvalidX");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (ships[i][j] != null) {
					Server.writeLineToServer("Game::Setup::Ship::"
							+ ships[i][j].getName() + "::" + j + "::" + i
							+ "::" + ships[i][j].getOrientation());
				}

			}
		}

		waitOnThread(rv);

		String returnedResults = rv.getCommand();
		System.out.println(returnedResults);

	}

	/**
	 * Confirms that the player is ready to begin the game.
	 * 
	 */
	public void playerReady() {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Game::Setup::Ready::Success");
		commands.add("Game::Setup::Ready::Error::InsufficientShips");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		Server.writeLineToServer("Game::Setup::Ready");
		waitOnThread(rv);

		String returnedResult = rv.getCommand();
		System.out.println(returnedResult);

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
			new LogIn(new AppManager());

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

		System.err.println(returnedResult + " createroom method");
		if (returnedResult.equals("Room::Create:Error:UserInARoom")) {

			return "Error: User In Room";
		}
		if (returnedResult.substring(0, 12).equals("Room::Create")) {
			// TestLine
			System.err.println(returnedResult);
			return returnedResult.substring(13);
		}
		return "Error";
	}

	/**
	 * Closes a room in the server so it doesn't appear in a user's list
	 * 
	 * @param roomID
	 * @return
	 */
	public static String closeRoom(String roomID) {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Room::Close::Success");
		commands.add("Room::Close::Error::UserNotHost");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		if (roomID.substring(0, 1).equals(":")) {
			roomID = roomID.substring(1, roomID.length());
		}

		Server.writeLineToServer("Room::Close::" + roomID);

		waitOnThread(rv);

		String returnedResult = rv.getCommand();

		return returnedResult;
	}

	/**
	 * 
	 * 
	 * @param roomID
	 * @return
	 */
	public boolean joinRoom(String roomID) {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Room::Join::Success");
		commands.add("Room::Join::Error::Full");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		Server.writeLineToServer("Room::Join::" + roomID);

		waitOnThread(rv);

		String returnedResult = rv.getCommand();

		if (returnedResult.equals("Room::Join::Success")) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @return whether it is the local player's turn
	 */
	public boolean isYourTurn() {
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Game::Turn");

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
		System.out.println(returnedResult);

		if (returnedResult.equals("Game::Turn")) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Sends a command to the server to signal that the player fired at a ship.
	 * 
	 * @return an array with the results.
	 * 
	 */
	public String[] enemyFiredAt() {
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
		System.out.println(returnedResult);
		String[] array = new String[2];
		array[0] = returnedResult.substring(13, 14);
		array[1] = returnedResult.substring(16, 17);
		return array;
	}

}