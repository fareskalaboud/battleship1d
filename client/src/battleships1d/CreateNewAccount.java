package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

	public CreateNewAccount(JFrame mainFrame, JTextField userNameField,
			AppManager appManager) {
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
		accountFrame.setMinimumSize(new Dimension(220, 80));
		accountPanel = new JPanel(new GridLayout(4, 1));
		userNamePanel = new JPanel();
		passwordPanel = new JPanel();
		confirmPasswordPanel = new JPanel();
		buttonsPanel = new JPanel();
		userNameLabel = new JLabel("User Name:");
		passwordLabel = new JLabel("Password:");
		confirmPasswordLabel = new JLabel("Confirm Pass:");
		userNameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		confirmPasswordField = new JPasswordField(10);
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");

		// user can press enter to create the account
		accountFrame.getRootPane().setDefaultButton(okButton);

		accountFrame.setLocation(810, 200);
		accountFrame.setTitle("Create Account");
		accountPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

		userNamePanel.setLayout(new GridLayout(1, 4, 0, 5));
		userNamePanel.setBorder(new EmptyBorder(0, 0, 4, 0));
		passwordPanel.setLayout(new GridLayout(1, 4));
		passwordPanel.setBorder(new EmptyBorder(0, 0, 4, 0));
		confirmPasswordPanel.setLayout(new GridLayout(1, 4));
		confirmPasswordPanel.setBorder(new EmptyBorder(0, 0, 4, 0));
		buttonsPanel.setBorder(new EmptyBorder(5, 0, 0, 0));
		buttonsPanel.setLayout(new GridLayout(1, 4, 5, 0));

		userNamePanel.add(new JLabel());
		userNamePanel.add(userNameLabel);
		userNamePanel.add(userNameField);
		userNamePanel.add(new JLabel());

		passwordPanel.add(new JLabel());
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordField);
		passwordPanel.add(new JLabel());

		confirmPasswordPanel.add(new JLabel());
		confirmPasswordPanel.add(confirmPasswordLabel);
		confirmPasswordPanel.add(confirmPasswordField);
		confirmPasswordPanel.add(new JLabel());

		buttonsPanel.add(new JLabel());
		buttonsPanel.add(okButton);
		buttonsPanel.add(cancelButton);
		buttonsPanel.add(new JLabel());

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

		setUpColourTheme();

		setActionListeners();
	}

	private void setUpColourTheme() {
		accountPanel.setBackground(new Color(50, 50, 50));
		userNamePanel.setBackground(new Color(90, 90, 90));
		passwordPanel.setBackground(new Color(90, 90, 90));
		confirmPasswordPanel.setBackground(new Color(90, 90, 90));
		buttonsPanel.setBackground(new Color(90, 90, 90));

		userNameLabel.setForeground(new Color(255, 255, 255));
		userNameLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
		passwordLabel.setForeground(new Color(255, 255, 255));
		passwordLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
		confirmPasswordLabel.setForeground(new Color(255, 255, 255));
		confirmPasswordLabel.setFont(new Font("Monospaced", Font.BOLD, 14));

		okButton.setBackground(new Color(160, 160, 160));
		cancelButton.setBackground(new Color(160, 160, 160));
		okButton.setForeground(new Color(255, 255, 255));
		cancelButton.setForeground(new Color(255, 255, 255));

		okButton.setFont(new Font("Garamond", Font.BOLD, 15));
		cancelButton.setFont(new Font("Garamond", Font.BOLD, 15));
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
				String verification = appManager.createAccount(userName,
						password);

				if (!userName.equals("")
						&& password.equals(confirmPass)
						&& !password.equals("")
						&& verification
								.equals("Login::Create::Successful::Username")) {
					userNameFromLogIn.setText(userName);

					// Check with the server is is ok

					enableLogInFrame.enableLogInFrame(testFrame);
					accountFrame.dispose();
				} else if (userName.equals("") || verification.equals("Login::Create::Error::Username")) {
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
											+ "Please supply matching passwords entries.",
									"Error", 0);
				} else if (verification.equals("Login::Create::Error")) {
					JOptionPane.showMessageDialog(accountFrame,
							"Error while trying to create a new account.\nPlease try again", "Error", 0);
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
