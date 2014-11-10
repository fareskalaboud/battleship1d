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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 
 * @author GEORGE RADUTA
 * 
 */
public class LogIn {

	private AppManager appManager;

	/**
	 * Constructor
	 * @param appManager
	 */
	public LogIn(AppManager appManager) {
		this.appManager = appManager;
		setUpUI();
	}

	public LogIn() {

	}

	private JFrame mainFrame;
	private JPanel logInPanel;

	private JPanel northPanel;
	private JLabel titleLabel;

	private JPanel logInCenterPanel, mainLogInPanel, barPanel;
	private JPanel userNamePanel, passwordPanel;
	private JPanel logInButtonsPanel, centerPassPanel, logInAsGuestPanel;
	private JLabel userNameLabel;
	private JLabel passwordLabel;
	private JTextField userNameTextField;
	private JPasswordField passwordTextField;
	private JButton logInAsPlayerButton, logInAsGuestButton,
			createAccountButton;

	/**
	 * 
	 * @param testFrame
	 * @author GEORGE RADUTA
	 */
	public void enableLogInFrame(JFrame testFrame) {
		this.mainFrame = testFrame;
		mainFrame.setEnabled(true);
	}

	/**
	 * @author GEORGE RADUTA
	 */
	public void setUpUI() {
		mainFrame = new JFrame();
		mainFrame.setUndecorated(true);

		// TOP BAR WITH EXIT AND MIN
		mainLogInPanel = new JPanel();
		logInPanel = new JPanel();
		BarPanel test = new BarPanel(mainFrame);
		barPanel = test.getPanel();
		DragFrame testDrag = new DragFrame(barPanel);
		barPanel.addMouseListener(testDrag);
		barPanel.addMouseMotionListener(testDrag);
		//

		mainLogInPanel.setLayout(new BorderLayout());
		mainLogInPanel.add(logInPanel, BorderLayout.CENTER);
		mainLogInPanel.add(barPanel, BorderLayout.NORTH);

		logInPanel.setLayout(new BorderLayout());
		logInPanel.setBorder(new EmptyBorder(0, 10, 10, 10));

		mainFrame.setLocation(350, 200);
		mainFrame.setResizable(false);

		// NORTH COMPONENT
		northPanel = new JPanel();
		titleLabel = new JLabel("Battleship - 1D");
		// titleLabel.setText("test");
		// goGreenLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		titleLabel.setFont(new Font("Monospaced", Font.BOLD, 48));
		titleLabel.setForeground(new Color(255, 255, 255));
		northPanel.setLayout(new FlowLayout());
		northPanel.add(titleLabel);
		logInPanel.add(northPanel, BorderLayout.NORTH);

		// CENTER COMPONENT
		logInCenterPanel = new JPanel();
		userNamePanel = new JPanel();
		passwordPanel = new JPanel();
		logInButtonsPanel = new JPanel();
		centerPassPanel = new JPanel();
		logInAsGuestPanel = new JPanel();

		userNameLabel = new JLabel("User Name : ");
		passwordLabel = new JLabel("Password :  ");

		userNameTextField = new JTextField(10);
		passwordTextField = new JPasswordField(10);

		logInAsPlayerButton = new JButton("Log In");
		logInAsGuestButton = new JButton("Log in as Guest");
		createAccountButton = new JButton("Create Account");

		logInCenterPanel.setLayout(new BorderLayout());

		userNamePanel.setLayout(new GridLayout(1, 4));
		userNamePanel.setBorder(new EmptyBorder(0, 0, 4, 0));

		passwordPanel.setLayout(new GridLayout(1, 4));
		passwordPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

		logInButtonsPanel.setLayout(new GridLayout(1, 4, 15, 0));
		logInButtonsPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

		logInAsGuestPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

		logInAsGuestPanel.setLayout(new BorderLayout());

		centerPassPanel.setLayout(new BorderLayout());

		userNamePanel.add(new JLabel());
		userNamePanel.add(userNameLabel);
		userNamePanel.add(userNameTextField);
		userNamePanel.add(new JLabel());
		passwordPanel.add(new JLabel());
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordTextField);
		passwordPanel.add(new JLabel());
		logInButtonsPanel.add(new JLabel());
		logInButtonsPanel.add(logInAsPlayerButton);
		logInButtonsPanel.add(createAccountButton);
		logInButtonsPanel.add(new JLabel());

		centerPassPanel.add(passwordPanel, BorderLayout.NORTH);
		centerPassPanel.add(logInButtonsPanel, BorderLayout.CENTER);
		logInAsGuestPanel.add(new JLabel(
				"                                                 "),
				BorderLayout.WEST);
		logInAsGuestPanel.add(logInAsGuestButton, BorderLayout.CENTER);
		logInAsGuestPanel.add(new JLabel(
				"                                                 "),
				BorderLayout.EAST);
		logInCenterPanel.add(userNamePanel, BorderLayout.NORTH);
		logInCenterPanel.add(centerPassPanel, BorderLayout.CENTER);
		logInCenterPanel.add(logInAsGuestPanel, BorderLayout.SOUTH);
		logInPanel.add(logInCenterPanel, BorderLayout.CENTER);

		mainFrame.setTitle("Battleships - 1D");

		setColourTheme();

		mainFrame.add(mainLogInPanel);
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setActionListeners();
	}

	/**
	 * Applies the colour theme to the UI.
	 * 
	 * @author fareskalaboud
	 */
	private void setColourTheme() {

		logInPanel.setBackground(new Color(50, 50, 50));
		northPanel.setBackground(new Color(90, 90, 90));
		logInCenterPanel.setBackground(new Color(90, 90, 90));
		userNamePanel.setBackground(new Color(90, 90, 90));
		passwordPanel.setBackground(new Color(90, 90, 90));
		logInButtonsPanel.setBackground(new Color(90, 90, 90));
		logInAsGuestPanel.setBackground(new Color(90, 90, 90));
		centerPassPanel.setBackground(new Color(90, 90, 90));

		userNameLabel.setForeground(new Color(255, 255, 255));
		userNameLabel.setFont(new Font("Monospaced", Font.BOLD, 15));

		passwordLabel.setForeground(new Color(255, 255, 255));
		passwordLabel.setFont(new Font("Monospaced", Font.BOLD, 15));

		logInAsPlayerButton.setBackground(new Color(160, 160, 160));
		logInAsGuestButton.setBackground(new Color(160, 160, 160));
		createAccountButton.setBackground(new Color(160, 160, 160));
		logInAsPlayerButton.setForeground(new Color(255, 255, 255));
		logInAsGuestButton.setForeground(new Color(255, 255, 255));
		createAccountButton.setForeground(new Color(255, 255, 255));

		logInAsPlayerButton.setFont(new Font("Garamond", Font.BOLD, 15));
		logInAsGuestButton.setFont(new Font("Garamond", Font.BOLD, 15));
		createAccountButton.setFont(new Font("Garamond", Font.BOLD, 15));
	}

	/**
	 * @author GEORGE RADUTA
	 */
	public void setActionListeners() {
		createAccountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CreateNewAccount createAccount = new CreateNewAccount(
						mainFrame, userNameTextField, appManager);
				createAccount.setUpUI();

				mainFrame.setEnabled(false);
			}
		});

		logInAsGuestButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int randomNumber = 10000 + (int)(Math.random() * ((99999 - 10000) + 1));
				
				System.out.println("Start verification");
				String verification = appManager.checkGuest();
				
				System.out.println(verification);
				System.out.println("Verification ends now");
				MainMenu mainMenu = new MainMenu("GUEST" + randomNumber, appManager);
				mainFrame.dispose();
			}
		});

		logInAsPlayerButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TO DO check for user in data base;

				boolean ok = true;
				String userNameString = userNameTextField.getText().trim();
				String passwordString = passwordTextField.getText().trim();
				String verification = appManager.checkPlayerLoginDetails(userNameString,
						passwordString);
				System.out.println(verification);
				if (verification.equals("Login::User::Successful::" + userNameString)) {
					MainMenu mainMenu = new MainMenu(userNameTextField
							.getText().trim(), appManager);
					mainFrame.dispose();
				} else {
					JOptionPane.showMessageDialog(mainFrame,
							"UserName or Password is incorrect", "Error", 0);
				}
			}
		});
	}
}
