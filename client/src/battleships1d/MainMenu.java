package battleships1d;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

/**
 * 
 * @author GEORGE RADUTA
 * 
 */
public class MainMenu {

	public MainMenu() {

	}

	JFrame mainWindow;
	JPanel mainPanel;

	/**
	 * Creating the MainWindow
	 */
	public void createMainWindow() {

		mainWindow = new JFrame();
		mainPanel = new JPanel();

		mainWindow.setTitle("BattleShip - 1 D");
		mainWindow.setBounds(300, 100, 700, 600);

		mainPanel.setLayout(new BorderLayout());

		mainWindow.add(mainPanel);

		createNorthComponent();
		createCenterComponent();

		// mainWindow.pack();
		mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWindow.setVisible(true);

	}

	JPanel northPanel;
	JLabel titleLabel;

	/**
	 * createNorthComponent() - creates the North Component of the maineFrame;
	 * 
	 * @value It contains the title of the Project : "BattleShip - 1 D"
	 */
	public void createNorthComponent() {
		northPanel = new JPanel();
		titleLabel = new JLabel("BattleShip - 1 D");
		// titleLabel.setText("test");
		// goGreenLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		titleLabel.setFont(new Font("Arial", Font.ITALIC, 48));
		northPanel.setLayout(new FlowLayout());
		northPanel.add(titleLabel);
		mainPanel.add(northPanel, BorderLayout.NORTH);
	}

	JPanel centerPanel;
	JPanel firstGridPanel, secondGridPanel;
	JPanel userNamePanel;
	JPanel matchesPanel;
	JLabel userNameLabel;
	JButton setUserNameButton, enterLobbyButton, quickMatchButton;
	/**
	 * 
	 */
	public void createCenterComponent() {
		centerPanel = new JPanel();
		firstGridPanel = new JPanel();
		secondGridPanel = new JPanel();
		userNamePanel = new JPanel();
		matchesPanel = new JPanel();
		
		userNameLabel = new JLabel("UserName : ");
		setUserNameButton = new JButton("Set UserName");
		enterLobbyButton  = new JButton("Enter Lobby");
		quickMatchButton  = new JButton("Quick Match");
		
		userNameLabel.setFont(new Font("Arial", Font.ITALIC, 16));
		//setUserNameButton.setText("Set UserName");
		

		centerPanel.setLayout(new GridLayout(3,1));
		firstGridPanel.setLayout(new BorderLayout());
		secondGridPanel.setLayout(new BorderLayout());
		userNamePanel.setLayout(new GridLayout(2,1));
		matchesPanel.setLayout(new GridLayout(2,1));
		
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		centerPanel.add(new JPanel());
		centerPanel.add(firstGridPanel);
		//centerPanel.add(new JPanel());
		centerPanel.add(secondGridPanel);
		firstGridPanel.add(userNamePanel,BorderLayout.NORTH);
		secondGridPanel.add(matchesPanel,BorderLayout.NORTH);
		
		JPanel moment = new JPanel();
		moment.setLayout(new FlowLayout());
		moment.add(userNameLabel);
		JPanel moment2 = new JPanel();
		moment2.setLayout(new FlowLayout());
		moment2.add(setUserNameButton);
		
		userNamePanel.add(moment);
		userNamePanel.add(moment2);
		
		JPanel moment3 = new JPanel();
		moment3.setLayout(new FlowLayout());
		moment3.add(enterLobbyButton);
		JPanel moment4 = new JPanel();
		moment4.setLayout(new FlowLayout());
		moment4.add(quickMatchButton);
		
		matchesPanel.add(moment3);
		matchesPanel.add(moment4);
		
		setUserNameButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
		});
	}

	public static void main(String[] args) {
		new MainMenu().createMainWindow();
	}

}
