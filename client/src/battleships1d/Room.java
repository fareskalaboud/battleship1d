package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

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
    
    private boolean isLocalsMove;
    

    
    private JLabel jlHeaderText;


    /**
     * A constructor that takes only the room ID.
     * This automatically creates a public room.
     *
     * @param roomID the room name/id
     * @author faresalaboud
     */
	public Room(String roomID, String userName) {
		super("Battleships (Room ID: " + roomID +")");
        this.roomID = roomID;
        this.isPrivate = false;
        this.password = "";
        this.userName = userName;
        localMap = new LocalMap(this);
        enemyMap = new EnemyMap(this);
        setUpUI();
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
        localMap = new LocalMap(this);
        enemyMap = new EnemyMap(this);
        setUpUI();
    }
    
    
    /**
     * Sets up room perspective for user
     * @author Alexander Hanbury-Botherway
     */
    public void setUpUI(){
    	
    	jlHeaderText = new JLabel("Header Text");

    	setLayout(new BorderLayout());
    	Border emptyBoarder = BorderFactory.createEmptyBorder(20, 20, 20, 20);
    	int textJustivaction = TitledBorder.LEFT;
    	int textPosistion = TitledBorder.TOP;
    	Font font = new Font("monospaced", Font.BOLD, 24);
 
    	localMap.setBorder(BorderFactory.createTitledBorder(emptyBoarder, "Local Map", textJustivaction, textPosistion, font, Color.BLACK));
    	add(localMap, BorderLayout.WEST);

    	enemyMap.setBorder(BorderFactory.createTitledBorder(emptyBoarder, "Enemy Map", textJustivaction, textPosistion, font, Color.BLACK));
		add(enemyMap, BorderLayout.EAST);


    	add(localMap, BorderLayout.WEST);
    	add(enemyMap, BorderLayout.EAST);
    	add(jlHeaderText, BorderLayout.NORTH);

 
    	setResizable(false);
    	setLocationRelativeTo(null);
    	setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
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
	
	public static void main(String args[]){
		new Room("Room name", "User name");
	}
	
}
