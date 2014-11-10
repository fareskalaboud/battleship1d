package battleships1d;

/**
 * Created by Thomas on 24/10/2014.
 */
public class Room {
	private String roomID;
	private boolean privateRoom;
	private String password;
	private User host;
	private User guest;
	private Game game;

	/**
	 * The constructor of the Room;
	 * 
	 * @param host
	 */
	public Room(User host) {
		this("", host);
	}

	/**
	 * Another constructor with different parameters;
	 * 
	 * @param password
	 * @param host
	 */
	public Room(String password, User host) {
		if (password.equals(""))
			this.privateRoom = false;
		else
			this.privateRoom = true;
		this.password = password;
		this.roomID = RoomManager.generateRoomName();
		this.host = host;
		this.game = new Game(this);
	}

	/**
	 * 
	 * @return the ID of the room;
	 */
	public String getRoomID() {
		return roomID;
	}

	/**
	 * Set the ID of the room;
	 * 
	 * @param roomID
	 */
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	/**
	 * Check if the room is private or not;
	 * 
	 * @return - boolean;
	 */
	public boolean isPrivateRoom() {
		return privateRoom;
	}

	/**
	 * Set the room to be private/public;
	 * 
	 * @param privateRoom
	 */
	public void setPrivateRoom(boolean privateRoom) {
		this.privateRoom = privateRoom;
	}

	/**
	 * Return the host of the game;
	 * 
	 * @return
	 */
	public User getHost() {
		return host;
	}

	/**
	 * Set the host of the game;
	 * 
	 * @param host
	 *            - type USER;
	 */
	public void setHost(User host) {
		this.host = host;
	}

	/**
	 * Return the guest of the game;
	 * 
	 * @return
	 */
	public User getGuest() {
		return guest;
	}

	/**
	 * Setting the guest of the game;
	 * 
	 * @param guest
	 */
	public void setGuest(User guest) {
		this.guest = guest;
	}

	/**
	 * 
	 * @return - string : the password of the room;
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Setting the password of the room;
	 * 
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return the game;
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * Set the game;
	 * 
	 * @param game
	 */
	public void setGame(Game game) {
		this.game = game;
	}
}
