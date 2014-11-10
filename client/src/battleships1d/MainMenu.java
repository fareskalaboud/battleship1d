package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

/**
 * 
 * @author GEORGE RADUTA
 * 
 */
public class MainMenu {
	private String userName;
	private AppManager appManager;
	/**
	 * Constructor of the MainMenu JFrame
	 * @param userName
	 * @param appManager
	 */
	public MainMenu(String userName, AppManager appManager) {
		this.userName = userName;
		this.appManager = appManager;
		setUpUI();
	}

	private JFrame mainWindow;
	private JPanel mainPanel;
	private JPanel northPanel, barPanel;
	private JLabel titleLabel;
	private JPanel centerPanel;
	private JPanel firstGridPanel, secondGridPanel;
	private JPanel userNameGridPanel, mainMainMenuPanel;
	private JPanel matchesGridPanel;
	private JLabel welcomeLabel;
	private JLabel userSelectedNameLabel;
	private JButton watchTutorialButton, enterLobbyButton, quickMatchButton;
	private JPanel userNameLabelPanel;
	private JPanel watchTutorialButtonPanel;
	private JPanel enterLobbyButtonPanel;
	private JPanel quickMatchButtonPanel;
	
	/**
	 * Creating the MainWindow (More like MainPanel as Fares wanted it so that
	 * we could just switch the panels)
	 * @author GEORGE RADUTA
	 */
	public void setUpUI() {

		mainWindow = new JFrame();
		mainPanel = new JPanel();
		mainMainMenuPanel = new JPanel();
		mainWindow.setUndecorated(true);
		
		mainPanel.setBorder(new EmptyBorder(0, 10, 10, 10));
		
		mainWindow.setTitle("BattleShip - 1 D");
		mainWindow.setBounds(400, 100, 550, 520);
		mainWindow.setResizable(false);
		
		mainPanel.setLayout(new BorderLayout());
		
		// BarPanel
		BarPanel test = new BarPanel(mainWindow);
		barPanel = test.getPanel();
		DragFrame testDrag = new DragFrame(barPanel);
		barPanel.addMouseListener(testDrag);
		barPanel.addMouseMotionListener(testDrag);
		//
		mainMainMenuPanel.setLayout(new BorderLayout());
		mainMainMenuPanel.add(mainPanel, BorderLayout.CENTER);
		mainMainMenuPanel.add(barPanel, BorderLayout.NORTH);
		
		createNorthComponent();
		createCenterComponent();

		mainWindow.add(mainMainMenuPanel);
		// mainWindow.pack();
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setColourTheme();
		
		mainWindow.setVisible(true);

	}

	/**
	 * Applies the colour theme to the UI.
	 */
	private void setColourTheme() {
		mainPanel.setBackground(new Color(50, 50, 50));
		northPanel.setBackground(new Color(90, 90, 90));
		centerPanel.setBackground(new Color(90, 90, 90));
		firstGridPanel.setBackground(new Color(90, 90, 90));
		secondGridPanel.setBackground(new Color(90, 90, 90));
		userNameGridPanel.setBackground(new Color(90, 90, 90));
		matchesGridPanel.setBackground(new Color(90, 90, 90));
		userNameLabelPanel.setBackground(new Color(90, 90, 90));
		watchTutorialButtonPanel.setBackground(new Color(90, 90, 90));
		enterLobbyButtonPanel.setBackground(new Color(90, 90, 90));
		quickMatchButtonPanel.setBackground(new Color(90, 90, 90));
		
		userSelectedNameLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
		welcomeLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
		userSelectedNameLabel.setForeground(new Color(255, 255, 255));
		welcomeLabel.setForeground(new Color(255, 255, 255));
		
		watchTutorialButton.setBackground(new Color(160, 160, 160));
		enterLobbyButton.setBackground(new Color(160, 160, 160));
		quickMatchButton.setBackground(new Color(160, 160, 160));
		
		watchTutorialButton.setForeground(new Color(255, 255, 255));
		enterLobbyButton.setForeground(new Color(255, 255, 255));
		quickMatchButton.setForeground(new Color(255, 255, 255));
		
		watchTutorialButton.setFont(new Font("Garamond", Font.BOLD, 15));
		enterLobbyButton.setFont(new Font("Garamond", Font.BOLD, 15));
		quickMatchButton.setFont(new Font("Garamond", Font.BOLD, 15));
	}

	/**
	 * createNorthComponent() - creates the North Component of the mainPanel;
	 * @author GEORGE RADUTA
	 * @value It contains the title of the Project : "BattleShip - 1 D"
	 */
	public void createNorthComponent() {
		northPanel = new JPanel();
		northPanel.setBorder(new EmptyBorder(5, 5, 60, 5));
		titleLabel = new JLabel("Battleships - 1D");
		titleLabel.setFont(new Font("Monospaced", Font.BOLD, 48));
		titleLabel.setForeground(new Color(255, 255, 255));
		northPanel.setLayout(new FlowLayout());
		northPanel.add(titleLabel);
		mainPanel.add(northPanel, BorderLayout.NORTH);
	}

	/**
	 * createCenterComponent() - creates the Center Component of the mainPanel;
	 * @author GEORGE RADUTA
	 * @value It contains the UserNameLabel, SetUserNameButton, EnterLobbyButton
	 *        and QuickMathcButton;
	 */
	public void createCenterComponent() {
		centerPanel = new JPanel();
		firstGridPanel = new JPanel();
		secondGridPanel = new JPanel();
		userNameGridPanel = new JPanel();
		matchesGridPanel = new JPanel();
	       
		welcomeLabel = new JLabel("Welcome, ");
		userSelectedNameLabel = new JLabel(userName + " !");
		watchTutorialButton = new JButton("Watch a Tutorial");
		enterLobbyButton = new JButton("Enter Lobby");
		quickMatchButton = new JButton("Quick Match");
		
		centerPanel.setLayout(new GridLayout(3, 1));
		firstGridPanel.setLayout(new BorderLayout());
		secondGridPanel.setLayout(new BorderLayout());
		userNameGridPanel.setLayout(new GridLayout(2, 1));
		matchesGridPanel.setLayout(new GridLayout(3, 1));

		// centerPanel.add(new JPanel());

		firstGridPanel.add(userNameGridPanel, BorderLayout.NORTH);
		secondGridPanel.add(matchesGridPanel, BorderLayout.NORTH);
		centerPanel.add(firstGridPanel);
		centerPanel.add(secondGridPanel);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		userNameLabelPanel = new JPanel();
		userNameLabelPanel.setLayout(new FlowLayout());
		userNameLabelPanel.setBorder(BorderFactory.createCompoundBorder(new EmptyBorder(10, 10, 10, 10), new EtchedBorder()));
		userNameLabelPanel.add(welcomeLabel);
		userNameLabelPanel.add(userSelectedNameLabel);

		watchTutorialButtonPanel = new JPanel();
		watchTutorialButtonPanel.setLayout(new GridLayout(1, 3));
		watchTutorialButtonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		watchTutorialButtonPanel.add(new JLabel());
		watchTutorialButtonPanel.add(watchTutorialButton);
		watchTutorialButtonPanel.add(new JLabel());

		userNameGridPanel.add(userNameLabelPanel);
		matchesGridPanel.add(watchTutorialButtonPanel);

		enterLobbyButtonPanel = new JPanel();
		enterLobbyButtonPanel.setLayout(new GridLayout(1, 3));
		enterLobbyButtonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		enterLobbyButtonPanel.add(new JLabel());
		enterLobbyButtonPanel.add(enterLobbyButton);
		enterLobbyButtonPanel.add(new JLabel());

		quickMatchButtonPanel = new JPanel();
		quickMatchButtonPanel.setLayout(new GridLayout(1, 3));
		quickMatchButtonPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		quickMatchButtonPanel.add(new JLabel());
		//quickMatchButtonPanel.add(quickMatchButton);
		quickMatchButtonPanel.add(new JLabel());

		matchesGridPanel.add(enterLobbyButtonPanel);
		matchesGridPanel.add(quickMatchButtonPanel);
		
		setActionListeners();
	}
	
	private Lobby lobby;
	/**
	 * Adding functionality to JButtons
	 * @author GEORGE RADUTA
	 */
	private void setActionListeners() {
		
		enterLobbyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lobby = new Lobby(appManager);
				mainWindow.dispose();
			}
		});
		
		watchTutorialButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URI("https://dl.dropboxusercontent.com/u/106321121/SEG%201%20Tutorial.pdf"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
	}
}
