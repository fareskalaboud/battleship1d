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
	Map guestMap;
	
	String roomID;
	
	Boolean roomType;
	
	// TO DO  :  MAP ?
	
	// setUpUI?
	
	/**
	 * setOwner() 
	 * @param owner of the Room of type Player;
	 */
	public void setOwner(Player owner) {
		this.owner = owner;
	}
	
	/**
	 * getOwner()
	 * @return the owner of the Room of type Player;
	 */
	public Player getOwner() {
		return this.owner;
	}
	
	/**
	 * setGuest()
	 * @param guest the guest of the Room of type Player;
	 */
	public void setGuest(Player guest) {
		this.guest = guest;
	}
	
	/**
	 * setPlayer()
	 * @return the guest of the Room of type Player;
	 */
	public Player setPlayer() {
		return this.guest;
	}
	
	/**
	 * roomType will be set as Private = TRUE and Public = FALSE
	 * @param type - boolean which signify the type of the room
	 */
	public void setRoomType(boolean type) {
		this.roomType = type;
	}
	
	/**
	 * Private = TRUE and Public = FALSE;
	 * @return - boolean which signify the type of the room;
	 */
	public boolean getRoomType() {
		return this.roomType;
	}
	
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
	
	/**
	 * Overrides the toString() method
	 * @return the ID(Name) of the room;
	 * 
	 */
	@Override
	public String toString() {
		return this.roomID;
	}
}
