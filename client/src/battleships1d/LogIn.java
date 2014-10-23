package battleships1d;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author GEORGE RADUTA
 *
 */
public class LogIn {

    // CHANGE: Declared global variables as private.
    private JFrame mainFrame;
    private JPanel logInPanel;

    private JPanel northPanel;
    private JLabel titleLabel;

    private JPanel logInCenterPanel;
    private JPanel userNamePanel, passwordPanel;
    private JPanel logInButtonsPanel, centerPassPanel;
    private JLabel userNameLabel;
    private JLabel passwordLabel;
    private JTextField userNameTextField;
    private JTextField passwordTextField;
    private JButton logInAsPlayerButton, logInAsGuestButton, createAccountButton;

    // CHANGE: No constructor needed

    public static void main(String[] args) {
        new LogIn().start();
    }

    public void start() {
        setUpUI();
    }

    public void enableLogInFrame(JFrame testFrame) {
        this.mainFrame = testFrame;
        mainFrame.setEnabled(true);
    }

    // CHANGE: Put all UI building in one method: setUpUI()
    public void setUpUI() {
        mainFrame = new JFrame();
        logInPanel = new JPanel();

        logInPanel.setLayout(new BorderLayout());

        mainFrame.setLocation(450, 200);
        mainFrame.setResizable(false);

        // NORTH COMPONENT
        northPanel = new JPanel();
        titleLabel = new JLabel("BattleShip - 1 D");
        // titleLabel.setText("test");
        // goGreenLabel.setFont(new Font("Arial", Font.PLAIN, 24));
        titleLabel.setFont(new Font("Arial", Font.ITALIC, 48));
        northPanel.setLayout(new FlowLayout());
        northPanel.add(titleLabel);
        logInPanel.add(northPanel, BorderLayout.NORTH);

        // CENTER COMPONENT
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
                CreateNewAccount test = new CreateNewAccount(mainFrame);
                test.setUpUI();
                mainFrame.setEnabled(false);
            }
        });

        logInAsGuestButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                MainMenu test = new MainMenu();
                test.setUpUI();
                mainFrame.dispose();
            }
        });;

        mainFrame.setTitle("BattleShip - 1 D");

        mainFrame.add(logInPanel);
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


}
