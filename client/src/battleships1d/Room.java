package battleships1d;

import javax.swing.JFrame;

/**
 * 
 * @author GEORGE RADUTA
 * 
 */
public class Room extends JFrame{
    // CHANGE: variable names to make it more consistent with the Lobby class
//	private Player localPlayer;
    private String enemyPlayer;
	private LocalMap localMap;
    private EnemyMap enemyMap;
	private String roomID;
    private String userName;

	private boolean isPrivate;
    private String password;


    /**
     * A constructor that takes only the room ID.
     * This automatically creates a public room.
     *
     * @param roomID the room name/id
     * @author faresalaboud
     */
	public Room(String roomID, String userName) {
        this.roomID = roomID;
        this.isPrivate = false;
        this.password = "";
        this.userName = userName;
    }

    /**
     * An overloaded constructor that takes both
     * the room ID and password.
     * This automatically creates a private room.
     *
     * @param roomID the room name/id
     * @param password the password to the private room
     * @author faresalaboud
     */
    public Room(String roomID, String password, String userName) {
        this.roomID = roomID;
        this.isPrivate = true;
        this.password = password;
        this.userName = userName;
        setUpUI();
    }
    
    //Alexander: Setting up this method so that it adds local map to west, and enemy map to east
    public void setUpUI(){
    	JFrame.
    }

//	/**
//	 * setLocalPlayer()
//	 *
//	 * @param localPlayer
//	 *            of the Room of type Player;
//	 */
//	public void setLocalPlayer(Player localPlayer) {
//		this.localPlayer = localPlayer;
//	}

//	/**
//	 * getLocalPlayer()
//	 *
//	 * @return the localPlayer of the Room of type Player;
//	 */
//	public Player getLocalPlayer() {
//		return this.localPlayer;
//	}

	/**
	 * setEnemyPlayer()
	 * 
	 * @param enemyPlayer
	 *            the enemyPlayer of the Room of type Player;
	 */
	public void setEnemyPlayer(String enemyPlayer) {
		this.enemyPlayer = enemyPlayer;
	}

	/**
	 * setPlayer()
	 * 
	 * @return the enemyPlayer of the Room of type Player;
	 */
	public String setPlayer() {
		return this.enemyPlayer;
	}

	/**
	 * roomType will be set as Private = TRUE and Public = FALSE
	 * 
	 * @param isPrivate
	 *            - boolean which signify the type of the room
	 */
	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}

	/**
	 * Private = TRUE and Public = FALSE;
	 * 
	 * @return - boolean which signify the type of the room;
	 */
	public boolean getIsPrivate() {
		return this.isPrivate;
	}

	/**
	 * 
	 * @param roomID
	 *            - roomID to be set;
	 */
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	/**
	 * String method
	 * 
	 * @return the ID of the Room;
	 */
	public String getRoomID() {
		return this.roomID;
	}

	/**
	 * Overrides the toString() method
	 * 
	 * @return the ID(Name) of the room;
	 * 
	 */
	@Override
	public String toString() {
		return this.roomID;
	}
	/**
	 * 
	 * @param row
	 * @param col
	 */
	public Result playButton(int row, int col) {
		
		return localMap.getClikedButton(row, col).playButton();
	}
}
