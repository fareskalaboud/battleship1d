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
    // CHANGE: Declared global variables as private.
	private JFrame testFrame;
    private JFrame accountFrame;
    private JPanel accountPanel;
    private JPanel userNamePanel, passwordPanel, confirmPasswordPanel, buttonsPanel;
    private JLabel userNameLabel, passwordLabel, confirmPasswordLabel;
    private JTextField userNameField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton okButton, cancelButton;

	public CreateNewAccount(JFrame mainFrame) {
		this.testFrame = mainFrame;
	}

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

        accountFrame.setLocation(810, 200);
        accountFrame.setTitle("Create Account");


        accountPanel.setLayout(new GridLayout(4, 1));
        userNamePanel.setLayout(new FlowLayout());
        passwordPanel.setLayout(new FlowLayout());
        confirmPasswordPanel.setLayout(new FlowLayout());
        confirmPasswordPanel.setLayout(new FlowLayout());
        buttonsPanel.setLayout(new GridLayout(1,2));

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

        final LogIn enableLogInFrame = new LogIn();
        okButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                enableLogInFrame.enableLogInFrame(testFrame);
                accountFrame.dispose();

            }
        });
        cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				 enableLogInFrame.enableLogInFrame(testFrame);
				 accountFrame.dispose();
				
			}
		});
	}

	public void createAccount() {

	}
}
