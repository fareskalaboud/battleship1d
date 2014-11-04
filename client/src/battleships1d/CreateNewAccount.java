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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * CreateNewAccount represents the frame where the player can create a new
 * Account
 * 
 * @author GEORGE RADUTA
 * 
 */
public class CreateNewAccount {
	/**
	 * Constructor which will get the frame from LogIn and the userName
	 * TextField in order to fill it after the account is created;
	 * 
	 * @param mainFrame
	 * @param userNameField
	 * @author GEORGE RADUTA
	 */
	private AppManager appManager;
	public CreateNewAccount(JFrame mainFrame, JTextField userNameField, AppManager appManager) {
		this.testFrame = mainFrame;
		this.userNameFromLogIn = userNameField;
		this.appManager = appManager;
	}

	private JFrame testFrame;
	private JFrame accountFrame;
	private JPanel accountPanel;
	private JPanel userNamePanel, passwordPanel, confirmPasswordPanel,
			buttonsPanel;
	private JLabel userNameLabel, passwordLabel, confirmPasswordLabel;
	private JTextField userNameField, userNameFromLogIn;
	private JPasswordField passwordField, confirmPasswordField;
	private JButton okButton, cancelButton;


	/**
	 * @author GEORGE RADUTA
	 */
	public void setUpUI() {
		accountFrame = new JFrame();

		accountPanel = new JPanel();
		userNamePanel = new JPanel();
		passwordPanel = new JPanel();
		confirmPasswordPanel = new JPanel();
		buttonsPanel = new JPanel();
		userNameLabel = new JLabel("User Name : ");
		passwordLabel = new JLabel("Password : ");
		confirmPasswordLabel = new JLabel("Confirm Password : ");
		userNameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		confirmPasswordField = new JPasswordField(10);
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		// user can press enter to create the account
		accountFrame.getRootPane().setDefaultButton(okButton);

		accountFrame.setLocation(810, 200);
		accountFrame.setTitle("Create Account");

		accountPanel.setLayout(new GridLayout(4, 1));
		userNamePanel.setLayout(new FlowLayout());
		passwordPanel.setLayout(new FlowLayout());
		confirmPasswordPanel.setLayout(new FlowLayout());
		confirmPasswordPanel.setLayout(new FlowLayout());
		buttonsPanel.setLayout(new GridLayout(1, 2));

		userNamePanel.add(userNameLabel);
		userNamePanel.add(userNameField);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		confirmPasswordPanel.add(confirmPasswordLabel);
		confirmPasswordPanel.add(confirmPasswordField);
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		accountPanel.add(userNamePanel);
		accountPanel.add(passwordPanel);
		accountPanel.add(confirmPasswordPanel);
		accountPanel.add(buttonsPanel);

		accountFrame.add(accountPanel);
		accountFrame.pack();
		accountFrame.setVisible(true);
		accountFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		accountFrame.setResizable(false);
		accountFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		setActionListeners();
	}

	/**
	 * @author GEORGE RADUTA
	 */	
	public void setActionListeners() {
		final LogIn enableLogInFrame = new LogIn();

		// OK
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				String userName = userNameField.getText().trim();
				String password = passwordField.getText().trim();
				String confirmPass = confirmPasswordField.getText().trim();

				// Check for userName to be OK in DataBase;

				if (!userName.equals("") && password.equals(confirmPass)
						&& !password.equals("")) {
					userNameFromLogIn.setText(userName);
					enableLogInFrame.enableLogInFrame(testFrame);
					accountFrame.dispose();
				} else if (userName.equals("")) {
					JOptionPane.showMessageDialog(accountFrame,
							"User Name is incorrect", "Error", 0);
				} else if (password.equals("")) {
					JOptionPane.showMessageDialog(accountFrame,
							"Password field is empty", "Error", 0);
				} else if (!password.equals(confirmPass)) {
					JOptionPane
							.showMessageDialog(
									accountFrame,
									"Password entries do not match.\n"
											+ "Please supply matching passwords entries.", "Error", 0);
				}
			}
		});

		// CANCEL
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				enableLogInFrame.enableLogInFrame(testFrame);
				accountFrame.dispose();

			}
		});
	}

	/**
	 * Should send data to the database and create the account
	 */
	public void createAccount() {

	}
}
