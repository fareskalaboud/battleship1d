package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

/**
 * 
 * @author GEORGE RADUTA
 * 
 */
public class Room extends JFrame {

	private JPanel mainPanel, borderPanel;
	private String enemyPlayer;
	private LocalMap localMap;
	private EnemyMap enemyMap;
	private String roomID;
	private String userName;

	private boolean isPrivate;
	private String password;

	private boolean isLocalsMove;

	private AppManager am;
	private Lobby lobby;
	private JLabel jlHeaderText;

	/**
	 * A constructor that takes only the room ID.
	 * 
	 * @param roomID
	 *            the room name/id
	 * @author faresalaboud
	 */
	public Room(String roomID, AppManager am) {
		super("Battleships (Room ID: " + roomID + ")");
		this.roomID = roomID;
		this.isPrivate = false;
		this.setMaximumSize(new Dimension(550, 550));
		this.password = "";
		localMap = new LocalMap(this);
		enemyMap = new EnemyMap(this);
		this.am = am;
		setUpUI();
	}

	public void setVisibleLobby() {
		lobby.setVisible(true);
	}

	private JPanel mainPlusBarPanel, barPanel;

	/**
	 * Sets up room perspective for user
	 * 
	 * @author Alexander Hanbury-Botherway
	 */
	public void setUpUI() {
		mainPlusBarPanel = new JPanel(new BorderLayout());
		BarPanel test = new BarPanel(this, roomID, am);
		this.setUndecorated(true);
		barPanel = test.getPanel();
		DragFrame testDrag = new DragFrame(barPanel);
		barPanel.addMouseListener(testDrag);
		barPanel.addMouseMotionListener(testDrag);

		//

		borderPanel = new JPanel(new GridLayout(1, 1));
		mainPanel = new JPanel(new GridLayout(1, 2));
		jlHeaderText = new JLabel("Header Text");

		Border emptyBoarder = BorderFactory.createEmptyBorder(20, 20, 20, 20);

		localMap.setBorder(BorderFactory.createTitledBorder(emptyBoarder,
				"Local Map", TitledBorder.LEFT, TitledBorder.TOP, new Font(
						"monospaced", Font.BOLD, 24), Color.WHITE));
		mainPanel.add(localMap);

		enemyMap.setBorder(BorderFactory.createTitledBorder(emptyBoarder,
				"Enemy Map", TitledBorder.LEFT, TitledBorder.TOP, new Font(
						"monospaced", Font.BOLD, 24), Color.WHITE));
		mainPanel.add(enemyMap);

		borderPanel.add(mainPanel);
		mainPlusBarPanel.add(barPanel, BorderLayout.NORTH);
		mainPlusBarPanel.add(borderPanel, BorderLayout.CENTER);
		this.add(mainPlusBarPanel);

		setMinimumSize(new Dimension(600, 550));
		setLocationRelativeTo(null);
		setUpColourTheme();

		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	/**
	 * Applies the game's colour theme
	 */
	private void setUpColourTheme() {
		borderPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		borderPanel.setBackground(new Color(50, 50, 50));

		mainPanel.setBackground(new Color(90, 90, 90));
		localMap.setBackground(new Color(90, 90, 90));
		enemyMap.setBackground(new Color(90, 90, 90));
	}
	
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
	 * Called by AppManager in order to alert user that it is now the players
	 * move
	 */
	public void setPlayersTurn() {
		isLocalsMove = true;
	}

	/**
	 * Called from app manager to access maps
	 * 
	 * @author Alexander Hanbury-Botherway
	 */
	public LocalMap getLocalMap() {
		return localMap;
	}

	/**
	 * 
	 * @return AppManager
	 * @author Alexander Hanbury-Botherway
	 */
	public AppManager getAM() {
		return am;
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

	public void checkIfYourTurn() {

		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				System.out.println("PLEASE");
				boolean isYourTurn = am.isYourTurn();
				enemyMap.enableFireButton();
				System.out.println("PLS");
				enemyMap.enableAllButtons();
			}
		}, 1000, 1000);

	}

	/**
	 * 
	 * @return the enemy map
	 */
	public EnemyMap getEnemyMap() {
		return enemyMap;
	}

	/**
	 * 
	 * @return the app manager
	 */
	public AppManager getAppManager() {
		return this.am;
	}
}
