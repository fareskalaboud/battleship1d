package battleships1d;

/**
 * 
 * @author Alexander Hanbury-Botherway
 * 
 */
public class RoomData {
	private String roomID;
	private boolean isPrivate;
	private String password;
	private String host;

	/**
	 * Constructor for user creating room
	 * 
	 * @param roomName
	 * @param isPrivate
	 * @param password
	 */
	public RoomData(String host, String password) {
		this.host = host;
		if (password.equals("") || password.equals(" ")) {
			isPrivate = false;
			password = " "; // to aid in communication with server
		} else {
			isPrivate = true;
		}
		this.password = password;

		String serverResponse = AppManager.createRoom(password);


		roomID = serverResponse;
	}

	/**
	 * Constructor for creating room based on server data
	 */
	public RoomData(String roomID, String host, String password) {
		this.roomID = roomID;
		this.host = host;
		this.password = password;

		if (password.equals(" ")) {
			isPrivate = false;
		} else {
			isPrivate = true;
		}
	}

	public boolean isPrivate() {
		return isPrivate;
	}

	public String toString() {
		return roomID + " (" + host + ")";
	}

	public String getRoomID() {
		return roomID;
	}
	
	public String getPassword() {
		return password;
	}
	
}
