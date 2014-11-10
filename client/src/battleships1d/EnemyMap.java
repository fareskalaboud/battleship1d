package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 * 
 * @author faresalaboud
 */
public class EnemyMap extends Map {

	EnemyButton[][] enemyButtons;
	boolean[][] hasBeenClicked;
	JPanel topPanel;
	JPanel bottomPanel;
	JButton shootButton;

	int playedRow;
	int playedColumn;
	boolean hasChosen;

	public EnemyMap(Room room) {
		super();
		this.room = room;
		enemyButtons = new EnemyButton[10][10];
		hasBeenClicked = new boolean[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				hasBeenClicked[i][j] = false;
			}
		}

		this.setUpUI();
		disableAllButtons();

	}

	public void setUpHealthBar() {

	}

	private JLabel timer;

	public void setUpUI() {
		this.setLayout(new BorderLayout());
		this.setSize(300, 300);

		// Initialise UI objects
		mapPanel = new JPanel(new GridLayout(10, 10));

		addButtons();

		bottomPanel = new JPanel(new FlowLayout());
		shootButton = new JButton("Shoot");

		shootButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				clickButtons();

			}

		});
		disableReady();
		timer = new JLabel("g");
		bottomPanel.add(shootButton);
		bottomPanel.add(timer);

		Border borderPanel = BorderFactory.createEmptyBorder(6, 0, 5, 0);

		topPanel = new JPanel();
		topPanel.setBorder(borderPanel);

		healthPanel = new JPanel();
		healthBar = new JProgressBar(0, 17);

		// Set up health bar
		healthBar.setForeground(new Color(169, 107, 39));
		healthBar.setValue(healthBar.getMaximum());
		// healthBar.setMaximumSize();

		// Start adding everything to the panels
		healthPanel.add(healthBar);

		topPanel.add(healthPanel);

		// Add the panels to the map
		this.add(mapPanel, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);

		setColourTheme();

	}

	private void setColourTheme() {
		topPanel.setBackground(new Color(90, 90, 90));
		mapPanel.setBackground(new Color(90, 90, 90));
		healthPanel.setBackground(new Color(90, 90, 90));
		bottomPanel.setBackground(new Color(90, 90, 90));

		shootButton.setBackground(new Color(160, 160, 160));

		shootButton.setForeground(new Color(255, 255, 255));

		shootButton.setFont(new Font("Garamond", Font.BOLD, 15));

	}

	// only add buttons when room is established, "ready"
	// could also be in the Room class
	public void addButtons() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				final int row = i;
				final int column = j;
				enemyButtons[i][j] = new EnemyButton(i, j, room);
				enemyButtons[i][j].setBackground(Color.gray);
				enemyButtons[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						if (!hasChosen) {
							enemyButtons[row][column]
									.setBackground(Color.black);
							playedRow = row;
							playedColumn = column;
							hasChosen = true;
						} else {
							enemyButtons[playedRow][playedColumn]
									.setBackground(Color.gray);
							enemyButtons[row][column]
									.setBackground(Color.black);
							playedRow = row;
							playedColumn = column;
							hasChosen = true;
						}
					}

				});
				mapPanel.add(enemyButtons[i][j]);

			}
		}
	}
	public void updateHealthBars() {
		int counter = healthBar.getMaximum();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (enemyButtons[i][j].getState() == EnemyButtonState.HIT) {
					counter--;
				}
			}
		}
		healthBar.setValue(counter);
		if (counter <= 0) {
			JOptionPane.showMessageDialog(new JFrame(), "You win!");
			disableAllButtons();
			AppManager am = room.getAppManager();
			
			am.getMainMenu().setVisible(true);
			
			am.closeRoom(room.getRoomID());
			room.dispose();
			
		}
	}

	public void disableAllButtons() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				enemyButtons[i][j].setEnabled(false);
			}
		}

	}

	public void enableAllButtons() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (!hasBeenClicked[i][j]) {
					enemyButtons[i][j].setEnabled(true);
				}
			}
		}
	}

	public void clickButtons() {
		if (!hasBeenClicked[playedRow][playedColumn]) {
			hasBeenClicked[playedRow][playedColumn] = true;
			Result result = room.getAM().playButton(playedColumn, playedRow);// Server
																			// uses
																			// x
																			// and
																			// y
																			// scheme
			
			if (result == Result.MISS) {
				enemyButtons[playedRow][playedColumn].setBackground(Color.blue);
				new SplashGif();
				enemyButtons[playedRow][playedColumn]
						.setState(EnemyButtonState.MISS);
				disableAllButtons();
			}
			if (result == Result.HIT) {
				BufferedImage buttonIcon = null;
				try {
					buttonIcon = ImageIO.read(new File("bomb.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				enemyButtons[playedRow][playedColumn]
						.setState(EnemyButtonState.HIT);
				enemyButtons[playedRow][playedColumn].setBackground(Color.red);
				enemyButtons[playedRow][playedColumn].setIcon(new ImageIcon(
						buttonIcon));
				enableAllButtons();
				new BoomGif();
			}
			if (result == Result.SUNK) {
				BufferedImage buttonIcon = null;
				try {
					buttonIcon = ImageIO.read(new File("bomb.png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				enemyButtons[playedRow][playedColumn]
						.setState(EnemyButtonState.HIT);
				enemyButtons[playedRow][playedColumn].setBackground(Color.red);
				enemyButtons[playedRow][playedColumn].setIcon(new ImageIcon(
						buttonIcon));
				new SinkGif();
				enableAllButtons();
			}
			enemyButtons[playedRow][playedColumn].setEnabled(false);
			hasChosen = false;
			room.checkIfYourTurn();
			room.updateFiredShip();
			updateHealthBars();

		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Invalid Move");
		}
	}

	public void setTimer() {
		timer = new TestTimer().getLabel();
		// bottomPanel.add(timer);
	}

	public void disableReady() {
		shootButton.setEnabled(false);
	}

	public void enableReady() {
		shootButton.setEnabled(true);
	}

	/**
	 * JLabel with the Timer for the user
	 * 
	 * @author GEORGE RADUTA
	 * 
	 */
	class TestTimer extends JLabel {

		private Timer secondsTimer;
		private final JLabel secondsRemainingLabel;
		private int timeRemaining;

		/**
		 * Constructor which will create the JLabel
		 */
		public TestTimer() {

			timeRemaining = 5;
			secondsRemainingLabel = new JLabel(String.valueOf(timeRemaining),
					JLabel.CENTER);

			secondsTimer = new Timer(1000, new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					timeRemaining -= 1;

					if (timeRemaining > 0) {
						secondsRemainingLabel.setText(String
								.valueOf(timeRemaining));
					} else {
						// enableAllButtons();
						// clickRandomButton();
						secondsTimer.stop();
					}

				}
			});

			secondsTimer.start();
		}

		/**
		 * Returning the JLabel
		 * 
		 * @return
		 */
		public JLabel getLabel() {
			return secondsRemainingLabel;
		}
	}

	@Override
	public void setActionListeners() {
		// TODO Auto-generated method stub
		
	}

}
