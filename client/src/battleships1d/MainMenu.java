package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author GEORGE RADUTA
 * 
 */
public class MainMenu {
	private String userName;
	private AppManager appManager;
	public MainMenu(String userName, AppManager appManager) {
		this.userName = userName;
		this.appManager = appManager;
		setUpUI();
	}

	private JFrame mainWindow;
	private JPanel mainPanel;
	private JPanel northPanel;
	private JLabel titleLabel;
	private JPanel centerPanel;
	private JPanel firstGridPanel, secondGridPanel;
	private JPanel userNameGridPanel;
	private JPanel matchesGridPanel;
	private JLabel welcomeLabel;
	private JLabel userSelectedNameLabel;
	private JButton watchTutorialButton, enterLobbyButton, quickMatchButton;
	private JPanel userNameLabelPanel;
	private JPanel setUserNameButtonPanel;
	private JPanel enterLobbyButtonPanel;
	private JPanel quickMatchButtonPanel;
	
	/**
	 * Creating the MainWindow (More like MainPanel as Fares wanted it so that
	 * we could just switch the panels)
	 */
	public void setUpUI() {

		mainWindow = new JFrame();
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
		
		mainWindow.setTitle("BattleShip - 1 D");
		mainWindow.setBounds(400, 100, 600, 400);

		mainPanel.setLayout(new BorderLayout());

		createNorthComponent();
		createCenterComponent();

		mainWindow.add(mainPanel);
		// mainWindow.pack();
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setColourTheme();
		
		mainWindow.setVisible(true);

	}

	private void setColourTheme() {
		mainPanel.setBackground(new Color(50, 50, 50));
		northPanel.setBackground(new Color(90, 90, 90));
		centerPanel.setBackground(new Color(90, 90, 90));
		firstGridPanel.setBackground(new Color(90, 90, 90));
		secondGridPanel.setBackground(new Color(90, 90, 90));
		userNameGridPanel.setBackground(new Color(90, 90, 90));
		matchesGridPanel.setBackground(new Color(90, 90, 90));
		userNameLabelPanel.setBackground(new Color(90, 90, 90));
		setUserNameButtonPanel.setBackground(new Color(90, 90, 90));
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
	 * 
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

	//TO DO :  QuickMatch button
	/**
	 * createCenterComponent() - creates the Center Component of the mainPanel;
	 * 
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
		userSelectedNameLabel = new JLabel(userName + " ! ");
		watchTutorialButton = new JButton("Watch a tutorial");
		enterLobbyButton = new JButton("Enter Lobby");
		quickMatchButton = new JButton("Quick Match");

		welcomeLabel.setFont(new Font("Arial", Font.ITALIC, 16));
		userSelectedNameLabel.setFont(new Font("Arial", Font.BOLD, 16));

		centerPanel.setLayout(new GridLayout(3, 1));
		firstGridPanel.setLayout(new BorderLayout());
		secondGridPanel.setLayout(new BorderLayout());
		userNameGridPanel.setLayout(new GridLayout(2, 1));
		matchesGridPanel.setLayout(new GridLayout(2, 1));

		// centerPanel.add(new JPanel());

		firstGridPanel.add(userNameGridPanel, BorderLayout.NORTH);
		secondGridPanel.add(matchesGridPanel, BorderLayout.NORTH);
		centerPanel.add(firstGridPanel);
		centerPanel.add(secondGridPanel);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		
		userNameLabelPanel = new JPanel();
		userNameLabelPanel.setLayout(new FlowLayout());
		userNameLabelPanel.add(welcomeLabel);
		userNameLabelPanel.add(userSelectedNameLabel);

		setUserNameButtonPanel = new JPanel();
		setUserNameButtonPanel.setLayout(new FlowLayout());
		setUserNameButtonPanel.add(watchTutorialButton);

		userNameGridPanel.add(userNameLabelPanel);
		userNameGridPanel.add(setUserNameButtonPanel);

		enterLobbyButtonPanel = new JPanel();
		enterLobbyButtonPanel.setLayout(new FlowLayout());
		enterLobbyButtonPanel.add(enterLobbyButton);

		quickMatchButtonPanel = new JPanel();
		quickMatchButtonPanel.setLayout(new FlowLayout());
		quickMatchButtonPanel.add(quickMatchButton);

		matchesGridPanel.add(enterLobbyButtonPanel);
		matchesGridPanel.add(quickMatchButtonPanel);
		setActionListeners();
	}
	private Lobby lobby;
	
	private void setActionListeners() {
		
		enterLobbyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				lobby = new Lobby(appManager);
				mainWindow.dispose();
			}
		});
	}
}
