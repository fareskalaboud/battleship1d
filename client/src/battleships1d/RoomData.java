package battleships1d;

/**
 * 
 * @author Alexander Hanbury-Botherway
 *
 */
public class RoomData {
	private String roomID;
	private String roomName;
	private boolean isPrivate;
	private String password;
	
	public RoomData(String roomName, boolean isPrivate, String password){
		this.roomName = roomName;
		this.isPrivate = isPrivate;
		if (isPrivate){
		this.password = password;
		} else {
			password = "";
		}
		String serverResponse = AppManager.createRoom(password);
		
		if (serverResponse.equals("Error")){
			System.err.println("Error when creating room: room already in use?");
			roomName += " (FAILED TO SYNC WITH SERVER)";
		} else {
			roomID = serverResponse;
		}
	}
	
	public String toString(){
		return roomName + " (" + roomID + ")" ;
	}
	
	
	
}
