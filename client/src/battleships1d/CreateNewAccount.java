package battleships1d;

import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * 
 * @author GEORGE RADUTA
 * 
 */
public class CreateNewAccount {
	JFrame testFrame;

	public CreateNewAccount(JFrame mainFrame) {
		this.testFrame = mainFrame;
	}

	public void setUpUI() {
		createAccount();
	}

	JFrame accountFrame;
	JPanel accountPanel;
	JPanel userNamePanel, passwordPanel, confirmPasswordPanel;
	JLabel userNameLabel, passwordLabel, confirmPasswordLabel;
	JTextField userNameField;
	JPasswordField passwordField, confirmPasswordField;
	JButton okButton;

	public void createAccount() {
		accountFrame = new JFrame();
		
		accountPanel = new JPanel();
		userNamePanel = new JPanel();
		passwordPanel = new JPanel();
		confirmPasswordPanel = new JPanel();
		userNameLabel = new JLabel("User Name : ");
		passwordLabel = new JLabel("Password : ");
		confirmPasswordLabel = new JLabel("Confirm Password : ");
		userNameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		confirmPasswordField = new JPasswordField(10);
		okButton = new JButton("OK");

		accountFrame.setLocation(810, 200);
		accountFrame.setTitle("Create Account");


		accountPanel.setLayout(new GridLayout(4, 1));
		userNamePanel.setLayout(new FlowLayout());
		passwordPanel.setLayout(new FlowLayout());
		confirmPasswordPanel.setLayout(new FlowLayout());
		confirmPasswordPanel.setLayout(new FlowLayout());

		userNamePanel.add(userNameLabel);
		userNamePanel.add(userNameField);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		confirmPasswordPanel.add(confirmPasswordLabel);
		confirmPasswordPanel.add(confirmPasswordField);

		accountPanel.add(userNamePanel);
		accountPanel.add(passwordPanel);
		accountPanel.add(confirmPasswordPanel);
		accountPanel.add(okButton);

		accountFrame.add(accountPanel);
		accountFrame.pack();
		accountFrame.setVisible(true);
		accountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		accountFrame.setResizable(false);
		accountFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		final LogIn enableLogInFrame = new LogIn();
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				enableLogInFrame.enableLogInFrame(testFrame);
				accountFrame.dispose();

			}
		});
	}
}
