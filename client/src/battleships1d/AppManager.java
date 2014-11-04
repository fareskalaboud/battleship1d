package battleships1d;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;

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
    private static Vector<Room> publicRooms;
    private static Vector<Room> privateRooms;

    // Game Variables
    private boolean isLocalMove;

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
     * @param username Player's username
     * @param password Player's password
     *
     * @return the server response
     */
    public static String checkPlayerLoginDetails(String username, String password) {

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

        //TODO: Send to server, and fix line below
//        String response = "hello";
//
//        String[] serverResponse = response.split("::");
//        if(serverResponse[2].equals("Successful")) {
//            return "Successful";
//        } else if (serverResponse[2].equals("Error")){
//            if (serverResponse.length == 3) {
//                return "Error";
//            } else {
//                return "Error::" + username;
//            }
//        }
//        return "Error";
    }

    public static String createAccount(String username, String password) {
        //TODO: Send to server, and fix line below
        String response = "hello";

        String[] serverResponse = response.split("::");
        if(serverResponse[2].equals("Successful")) {
            return "Successful";
        } else if (serverResponse[2].equals("Error")){
            if (serverResponse.length == 3) {
                return "Error";
            } else {
                return "Error::" + username;
            }
        }
        return "Error";
    }

    /**
     * Sets the main player according to mainPlayer's current value
     * (if it's null, assign it a Guest value; else assign it the logged
     * in user.)
     *
     * @author faresalaboud
     */
    public static void setMainPlayer() {
        if (mainPlayer == null) {
            // If mainPlayer is null, then the game has only just booted up.
            // TODO: Obtain guest player object from server and assign it to mainPlayer
        } else {
            // If mainPlayer isn't null, it is most likely a Guest user.
            // TODO: Assign logged-in player to mainPlayer
        }
    }

    // Server Management

    /**
     * Obtains a list of the rooms from the server and adds them to
     * the public and private room Vectors.
     *
     * @author faresalaboud
     */

    public static void getRoomsFromServer() {
        // TODO: Remove initialisation, obtain rooms from server
        Vector<Room> allRooms = new Vector<Room>();

        // TODO: Command.getParameters
        String[] parameters = new String[0];
        Room r;

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
                    r = new Room(roomID, userName);
                    allRooms.add(r);
                    publicRooms.add(r);
                } else {
                    r = new Room(roomID, password, userName);
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
    public static Vector<Room> getPublicRooms() {
        return publicRooms;
    }

    /**
     * Returns a Vector of private rooms.
     *
     * @return a Vector of private rooms
     * @author faresalaboud
     */
    public static Vector<Room> getPrivateRooms() {
        return privateRooms;
    }

    
    /**
	 * 
	 * @param row
	 * @param col
	 * @author Alexander Hanbury-Botherway
	 */
	public Result playButton(int row, int col) {
//		try {
//			out.write("Game::Fire::" + row + "::" + col + "\n");
//			out.flush();
//		} catch (IOException e1) {
//			System.err.println("Output issuess");
//		}
//
//		isLocalMove = false;
//
//		String returnedResult;
//		try {
//			returnedResult = in.readLine();
//		} catch (IOException e) {
//			System.err.println("IOException");
//			return null;
//		}
//		if (returnedResult.equals("Game::Fire")){
//			return Result.MISS;
//		} if (returnedResult.equals("Game::Hit")){
//			return Result.HIT;
//		} if (returnedResult.equals("Game::Sunk")){
//			return Result.SUNK;
//		}
//		System.err.println("Result from button: " + row + " " + col + " is not valid");
		return null; 
	}

    private static Boolean waitOnThread(Server.RequestVariables rv) {
        while(!rv.getContinueThread()) {
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
     * 
     * @author Alexander Hanbury-Botherway
     *
     */
    public class RecieverThread extends Thread{
    	public void run(){
    		try {
				while(true){
					String input = in.readLine();
					
				}
			} catch (IOException e) {
				System.err.println("Fatal Error: Communications are no longer being recieved from the server due to IOException");
			}
    	}
    }
}