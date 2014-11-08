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
		
		System.out.println(returnedArray[0] + "0"); //Room 
		System.out.println(returnedArray[1] + "1"); //Count


		
		int roomCount = Integer.parseInt(returnedArray[1]);
		
		for (int i = 0; i<roomCount; i++){
			String roomID = returnedArray[2+(i*3)];
			String host = returnedArray[3+(i*3)];
			String password = returnedArray[4+(i*3)];
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
		if (returnedResult.equals("Game::Fire::Sunk")) {
			isLocalMove = false;
			String shipName = returnedResult.substring(19);
			new JDialog(new JFrame(), "You sunk the enemie's " + shipName + "!");
			System.out.println(shipName);
			return Result.SUNK;
		}
		if (returnedResult.equals("Game::Win")){
			isLocalMove = false;
			new JDialog(new JFrame(), "You win mate");
			System.out.println("u win");
		}
		
		if(returnedResult.equals("Game::Loss")){
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

	//@Cham TODO: test this method after they established creation of rooms
	public void setShips(Ship[][] ships){
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

		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if(ships[i][j] != null){
					Server.writeLineToServer("Game::Setup::Ship::"+ships[i][j].getName()+"::"+j+"::"+i+"::"+ships[i][j].getOrientation());				
				}
				
			}
		}
		
		waitOnThread(rv);
		
		
		String returnedResults = rv.getCommand();
		System.out.println(returnedResults);
		
		
		
		


	}
	
	public void playerReady(){
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
			setMainPlayer();
			// getRoomsFromServer();
			// frame.setVisible(true);
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
			//TestLine
			System.err.println(returnedResult);
			return returnedResult.substring(13);
		}
		return "Error";
	}
	
	private static String closeRoom(String roomID){
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

		Server.writeLineToServer("Room::Close::" + roomID);

		waitOnThread(rv);
		
		String returnedResult = rv.getCommand();
		
		return returnedResult;
	}
	
	private static String leaveRoom(String roomID){
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("Room::Leave::Success");
		commands.add("Room::Leave::UserIsHost");
		commands.add("Room::Leave::UserIsNotGuest");

		final Server.RequestVariables rv = new Server.RequestVariables();

		Server.registerCommands(commands, new Server.RequestFunction() {
			@Override
			public void Response(String command) {
				rv.setCommand(command);
				rv.setContinueThread(true);
			}
		});

		Server.writeLineToServer("Room::Leave::" + roomID);

		waitOnThread(rv);
		
		String returnedResult = rv.getCommand();
		
		return returnedResult;
	}

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

		Server.writeLineToServer("Room::Join::"+roomID);

		waitOnThread(rv);
		
		String returnedResult = rv.getCommand();
		
		if (returnedResult.equals("Room::Join::Success")){
			return true;
		}
		
		return false;
	}
	//Chamuel's joinRoom method
	public void joinRoom(RoomData roomID){
		
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
		
		Server.writeLineToServer("Room::Join::"+roomID.getRoomID());
		
		String returnedResult = rv.getCommand();
		
		System.out.println(returnedResult);
		
		waitOnThread(rv);
		
	}

}