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
	 * @param roomName
	 * @param isPrivate
	 * @param password
	 */
	public RoomData(String host, String password){
		this.host = host;
		if (password.equals("")){
			isPrivate = false;
		} else {
			isPrivate = true;
		}
		this.password = password;
		
		String serverResponse = AppManager.createRoom(password);
		
		if (serverResponse.equals("Error: User In Room")){
			System.err.println("Error when creating room: room already in use");
			roomID += " (FAILED TO SYNC WITH SERVER)";
		} else if (serverResponse.equals("Error")){
			System.err.println("Error when creating room: Server Communication fault");
			roomID += " (FAILED TO SYNC WITH SERVER)";
		} else {
			roomID = serverResponse;
		}
	}
	
	/**
	 * Constructor for creating room based on server data
	 */
	public RoomData(String roomID, String host, String password){
		this.roomID = roomID;
		this.host = host;
		this.password = password;
		
		if (password.equals("")){
			isPrivate = false;
		} else {
			isPrivate = true;
		}
	}
	
	public boolean isPrivate(){
		return isPrivate;
	}
	
	public String toString(){
		return roomID + " (" + host + ")";
	}
	
	
	
}
