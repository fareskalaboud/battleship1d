package battleships1d;

/**
 * 
 * @author GEORGE RADUTA
 *
 */
public class Room {
	Player owner;
	Map ownerMap;
	Player guest;
	Player guestMap;
	
	String roomID;
	
	// TO DO  :  get/set owner/guest?
// type of room? String?  
	// what are up.ui?
	/**
	 * 
	 * @param roomID - roomID to be set;
	 */
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
	/**
	 * String method
	 * @return the ID of the Room;
	 */
	public String getRoomID() {
		return this.roomID;
	}
}
