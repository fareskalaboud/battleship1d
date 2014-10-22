package battleships1d;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import sun.org.mozilla.javascript.internal.ast.ThrowStatement;

public class LogIn {
	public LogIn() {
		
	}
	
	JFrame mainFrame;
	JPanel logInPanel;
	public void start() {
		mainFrame = new JFrame();
		logInPanel = new JPanel();
		
		logInPanel.setLayout(new BorderLayout());
		
		mainFrame.setLocation(450, 200);
		mainFrame.setResizable(false);
		
		createNorthComponent();
		createCenterComponent();
		
		mainFrame.setTitle("BattleShip - 1 D");
		
		
		
		
		mainFrame.add(logInPanel);
		mainFrame.pack();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	JPanel northPanel;
	JLabel titleLabel;
	public void createNorthComponent() {
		northPanel = new JPanel();
		titleLabel = new JLabel("BattleShip - 1 D");
		// titleLabel.setText("test");
		// goGreenLabel.setFont(new Font("Arial", Font.PLAIN, 24));
		titleLabel.setFont(new Font("Arial", Font.ITALIC, 48));
		northPanel.setLayout(new FlowLayout());
		northPanel.add(titleLabel);
		logInPanel.add(northPanel, BorderLayout.NORTH);
	}
	
	JPanel logInCenterPanel;
	JPanel userNamePanel, passwordPanel;
	JPanel logInButtonsPanel, centerPassPanel;
	JLabel userNameLabel;
	JLabel passwordLabel;
	JTextField userNameTextField;
	JTextField passwordTextField;
	JButton logInAsPlayerButton, logInAsGuestButton, createAccountButton;
	public void createCenterComponent() {
		logInCenterPanel = new JPanel();
		userNamePanel = new JPanel();
		passwordPanel = new JPanel();
		logInButtonsPanel = new JPanel();
		centerPassPanel = new JPanel();
		
		userNameLabel = new JLabel("User Name : ");
		passwordLabel = new JLabel("Password :  ");
		
		userNameTextField = new JTextField(10);
		passwordTextField = new JTextField(10);
		
		logInAsPlayerButton = new JButton("Log in as a player ! ");
		logInAsGuestButton = new JButton("Log in as a guest !");
		createAccountButton = new JButton("Create a new account ! ");
		
		logInCenterPanel.setLayout(new BorderLayout());
		userNamePanel.setLayout(new FlowLayout());
		passwordPanel.setLayout(new FlowLayout());
		logInButtonsPanel.setLayout(new FlowLayout());
		centerPassPanel.setLayout(new BorderLayout());
		
		userNamePanel.add(userNameLabel);
		userNamePanel.add(userNameTextField);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordTextField);
		logInButtonsPanel.add(logInAsPlayerButton);
		logInButtonsPanel.add(createAccountButton);
		
		centerPassPanel.add(passwordPanel, BorderLayout.NORTH);
		centerPassPanel.add(logInButtonsPanel, BorderLayout.CENTER);
		logInCenterPanel.add(userNamePanel, BorderLayout.NORTH);
		logInCenterPanel.add(centerPassPanel, BorderLayout.CENTER);
		logInCenterPanel.add(logInAsGuestButton, BorderLayout.SOUTH);
		logInPanel.add(logInCenterPanel, BorderLayout.CENTER);
		
		createAccountButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				CreateNewAccount test = new CreateNewAccount();
				test.createAccount();
			}
		});
	}
	public static void main(String[] args) {
		new LogIn().start();
	}

}
