package battleships1d;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * 
 * @author GEORGE RADUTA
 * 
 */
public class MainMenu {
	String userName;
	public MainMenu(String userName) {
		this.userName = userName;
	}

	JFrame mainWindow;
	JPanel mainPanel;
	/**
	 * Creating the MainWindow (More like MainPanel as Fares wanted it so that
	 * we could just switch the panels)
	 */
	public void setUpUI() {

		mainWindow = new JFrame();
		mainPanel = new JPanel();

		mainWindow.setTitle("BattleShip - 1 D");
		mainWindow.setBounds(400, 100, 500, 400);

		mainPanel.setLayout(new BorderLayout());

		createNorthComponent();
		createCenterComponent();

		mainWindow.add(mainPanel);
		// mainWindow.pack();
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);

	}

	JPanel northPanel;
	JLabel titleLabel;

	/**
	 * createNorthComponent() - creates the North Component of the mainPanel;
	 * 
	 * @value It contains the title of the Project : "BattleShip - 1 D"
	 */
	public void createNorthComponent() {
		northPanel = new JPanel();
		titleLabel = new JLabel("BattleShip - 1 D");
		titleLabel.setFont(new Font("Arial", Font.ITALIC, 48));
		northPanel.setLayout(new FlowLayout());
		northPanel.add(titleLabel);
		mainPanel.add(northPanel, BorderLayout.NORTH);
	}

	JPanel centerPanel;
	JPanel firstGridPanel, secondGridPanel;
	JPanel userNameGridPanel;
	JPanel matchesGridPanel;
	JLabel welcomeLabel;
	JLabel userSelectedNameLabel;
	JButton watchTutorialButton, enterLobbyButton, quickMatchButton;

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
		centerPanel.add(new JPanel());
		centerPanel.add(firstGridPanel);
		centerPanel.add(secondGridPanel);
		mainPanel.add(centerPanel, BorderLayout.CENTER);

		JPanel userNameLabelPanel = new JPanel();
		userNameLabelPanel.setLayout(new FlowLayout());
		userNameLabelPanel.add(welcomeLabel);
		userNameLabelPanel.add(userSelectedNameLabel);

		JPanel setUserNameButtonPanel = new JPanel();
		setUserNameButtonPanel.setLayout(new FlowLayout());
		setUserNameButtonPanel.add(watchTutorialButton);

		userNameGridPanel.add(userNameLabelPanel);
		userNameGridPanel.add(setUserNameButtonPanel);

		JPanel enterLobbyButtonPanel = new JPanel();
		enterLobbyButtonPanel.setLayout(new FlowLayout());
		enterLobbyButtonPanel.add(enterLobbyButton);

		JPanel quickMatchButtonPanel = new JPanel();
		quickMatchButtonPanel.setLayout(new FlowLayout());
		quickMatchButtonPanel.add(quickMatchButton);

		matchesGridPanel.add(enterLobbyButtonPanel);
		matchesGridPanel.add(quickMatchButtonPanel);
		setActionListeners();
	}
	
	private Lobby lobby = new Lobby();
	private void setActionListeners() {
		enterLobbyButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {

				lobby.setUpUI();
				mainWindow.dispose();
			}
		});
	}
}
