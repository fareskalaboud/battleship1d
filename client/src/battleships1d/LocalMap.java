package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * 
 * @author Chamuel Uy
 */
public class LocalMap extends Map {

	private Room room;

	private JPanel topPanel, infoPanel, bottomPanel;
	private JLabel sizeOfShip, currentOrientation;
	private JButton undoMove, flipOrientation;

	private boolean[][] hasShip;
	private LocalButton[][] localButtons;
	private Ship[][] ships;
	private Orientation orientationOfShip;
	private Ship[][] clickedShips;

	private Stack lastPlacedShipAndOrientation;

	// for testing purposes
	private int shipSize;
	private int shipArrayCounter = 0;
	private static String[] shipNames = { "AircraftCarrier", "Battleship",
			"Submarine", "Destroyer", "PatrolBoat" };

	JTextField sizeOfShipText;
	JTextField currentOrientationText;

	JButton finished;

	final JComboBox listOfNames = new JComboBox(shipNames);

	/**
	 * Creates a local map for the current user in the (parameter) room
	 * 
	 * @param room
	 */
	public LocalMap(Room room) {
		super();

		this.room = room;

		// Initialise the whole map as not having any ship
		hasShip = new boolean[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				hasShip[i][j] = false;
			}
		}

		localButtons = new LocalButton[10][10];

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				localButtons[i][j] = new LocalButton(i, j);
				localButtons[i][j].setBackground(Color.GRAY);
			}
		}

		ships = new Ship[10][10];
		clickedShips = new Ship[10][10];

		orientationOfShip = Orientation.Horizontal;

		lastPlacedShipAndOrientation = new Stack();
		// pop order: 1st = orientation, 2nd = name
		// 3rd = size, 4th = column, 5th = row

		this.setUpUI();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see battleships1d.Map#setUpUI()
	 */
	public void setUpUI() {
		this.setLayout(new BorderLayout());
		this.setSize(300, 300);

		// Initialise UI objects
		mapPanel = new JPanel(new GridLayout(10, 10));

		topPanel = new JPanel();

		// @Cham TODO: finish this
		infoPanel = new JPanel();
		sizeOfShip = new JLabel("Size: ");
		sizeOfShipText = new JTextField();
		sizeOfShipText.setEditable(false);

		currentOrientation = new JLabel("Current Orientation: ");
		currentOrientationText = new JTextField();
		currentOrientationText.setEditable(false);

		infoPanel.setLayout(new FlowLayout());
		infoPanel.add(sizeOfShip);
		infoPanel.add(sizeOfShipText);
		infoPanel.add(currentOrientation);
		infoPanel.add(currentOrientationText);

		listOfNames.setSelectedIndex(0);
		Dimension staticSize = listOfNames.getSize();
		listOfNames.setMinimumSize(staticSize);
		updateSize((String) listOfNames.getSelectedItem());
		updateTexts();

		listOfNames.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (listOfNames.getSelectedItem() != null) {
					updateSize((String) listOfNames.getSelectedItem());
					updateTexts();
				}
			}

		});

		topPanel.add(infoPanel, BorderLayout.CENTER);
		topPanel.add(listOfNames, BorderLayout.WEST);

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				final int row = i;
				final int column = j;
				localButtons[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {

						if (shipArrayCounter > 4) {

						} else {
							Ship genericShip = new Ship(shipSize,
									orientationOfShip, (String) listOfNames
											.getSelectedItem());
							int size = genericShip.getSize();
							Orientation orientation = genericShip
									.getOrientation();
							if (checkIfCanAddShip(size, row, column,
									orientation)) {
								if (orientation == Orientation.Horizontal) {
									for (int i = column; i < column + size; i++) {
										hasShip[row][i] = true;
										ships[row][i] = genericShip;
										localButtons[row][i]
												.setBackground(Color.green);

									}
									clickedShips[row][column] = genericShip;
								} else {
									for (int i = row; i < row + size; i++) {
										hasShip[i][column] = true;
										ships[i][column] = genericShip;
										localButtons[i][column]
												.setBackground(Color.green);

									}
									clickedShips[row][column] = genericShip;
								}

								updateTexts();
								updateHealth();

								lastPlacedShipAndOrientation.add(row);
								lastPlacedShipAndOrientation.add(column);
								lastPlacedShipAndOrientation.add(shipSize);
								lastPlacedShipAndOrientation.add(orientation);
								lastPlacedShipAndOrientation.add(listOfNames
										.getSelectedItem());

								listOfNames.removeItem(listOfNames
										.getSelectedItem());
								shipArrayCounter++;

							}
						}

					}

				});

				mapPanel.add(localButtons[i][j]);
			}
		}

		setUpHealthBar();

		topPanel.add(healthBar, BorderLayout.NORTH);

		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());

		finished = new JButton("Finished");
		finished.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				updateLocalButtons();
			}

		});

		flipOrientation = new JButton("Flip Orientation");
		flipOrientation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				flipOrientation();
				updateTexts();
			}

		});

		undoMove = new JButton("Undo Move");
		undoMove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				undoMove();
				updateTexts();
			}

		});

		bottomPanel.add(undoMove);
		bottomPanel.add(flipOrientation);
		bottomPanel.add(finished);

		// Add the panels to the map
		add(topPanel, BorderLayout.NORTH);
		add(mapPanel, BorderLayout.CENTER);
		// add(healthPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);

		setColourTheme();

	}

	private void setColourTheme() {
		topPanel.setBackground(new Color(90, 90, 90));
		mapPanel.setBackground(new Color(90, 90, 90));
		bottomPanel.setBackground(new Color(90, 90, 90));
		infoPanel.setBackground(new Color(90, 90, 90));

		sizeOfShip.setForeground(new Color(255, 255, 255));
		currentOrientation.setForeground(new Color(255, 255, 255));
		sizeOfShipText.setForeground(new Color(255, 255, 255));
		currentOrientationText.setForeground(new Color(255, 255, 255));

		sizeOfShip.setFont(new Font("Monospaced", Font.BOLD, 15));
		currentOrientation.setFont(new Font("Monospaced", Font.BOLD, 15));
		sizeOfShipText.setFont(new Font("Monospaced", Font.BOLD, 15));
		currentOrientationText.setFont(new Font("Monospaced", Font.BOLD, 15));

		undoMove.setBackground(new Color(160, 160, 160));
		finished.setBackground(new Color(160, 160, 160));
		flipOrientation.setBackground(new Color(160, 160, 160));

		sizeOfShipText.setForeground(new Color(0, 0, 0));
		currentOrientationText.setForeground(new Color(0, 0, 0));

		undoMove.setForeground(new Color(255, 255, 255));
		finished.setForeground(new Color(255, 255, 255));
		flipOrientation.setForeground(new Color(255, 255, 255));

		undoMove.setFont(new Font("Garamond", Font.BOLD, 15));
		finished.setFont(new Font("Garamond", Font.BOLD, 15));
		flipOrientation.setFont(new Font("Garamond", Font.BOLD, 15));
	}

	public void setActionListeners() {

	}

	public LocalButton getClikedButton(int row, int col) {
		// TO DO : Fares, return the button at given coordinates;
		return new LocalButton(0, 0);

	}

	/**
	 * Checks whether the ship with a certain size and orientation can fit in
	 * the following row and column
	 * 
	 * @param size
	 *            - the size of the ship
	 * @param row
	 *            - which row are you trying to add it on
	 * @param column
	 *            - which column are you tryin to add it on
	 * @param orientation
	 *            - is it Horizontal or Vertical
	 * @return - true if it can fit
	 */
	public boolean checkIfCanAddShip(int size, int row, int column,
			Orientation orientation) {
		boolean canAddShip = true;
		try {
			if (orientation == Orientation.Horizontal) {
				for (int i = column; i < column + size; i++) {
					if (hasShip[row][i] == false) {

					} else {
						canAddShip = false;
					}
				}
			} else {
				for (int i = row; i < row + size; i++) {
					if (hasShip[i][column] == false) {

					} else {
						canAddShip = false;
					}
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			canAddShip = false;
		}

		return canAddShip;
	}

	// VERY IMPORTANT - MUST DO IT AFTER SETTING EVERYTHING UP
	/**
	 * updates the buttons itself of all the ships that has been added. This is
	 * the way it communicates with the server
	 */
	public void updateLocalButtons() {
		if (numberOfShips() == 17) {
			for (int i = 0; i < 10; i++) {
				for (int j = 0; j < 10; j++) {
					if (hasShip[i][j]) {
						localButtons[i][j].addShip(ships[i][j]);
						System.out.println(ships[i][j].getName() + " " + i
								+ " " + j);
						localButtons[i][j].setEnabled(false);
					}
				}
			}

			finished.setEnabled(false);
			undoMove.setEnabled(false);
			flipOrientation.setEnabled(false);

			room.getAM().setShips(clickedShips);
			room.getAM().playerReady();
			room.checkIfYourTurn();
			room.updateFiredShip();

		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Not Enough Ships");
		}

	}

	/**
	 * Flips the orientation of the ship to vertical or horizontal depending on
	 * the pervious orientation
	 */
	public void flipOrientation() {
		if (orientationOfShip == Orientation.Horizontal) {
			orientationOfShip = Orientation.Vertical;
		} else {
			orientationOfShip = Orientation.Horizontal;
		}
	}

	/**
	 * Updates the size of the ship depending on what type of ship it is
	 * 
	 * @param battleShipName
	 *            - the name of the battleship
	 */
	public void updateSize(String battleShipName) {
		if (battleShipName.equals("AircraftCarrier")) {
			shipSize = 5;
		} else if (battleShipName.equals("Battleship")) {
			shipSize = 4;
		} else if (battleShipName.equals("Submarine")) {
			shipSize = 3;
		} else if (battleShipName.equals("Destroyer")) {
			shipSize = 3;
		} else if (battleShipName.equals("PatrolBoat")) {
			shipSize = 2;
		}
	}

	/**
	 * Updates the Orientation and Name that's presented to the user
	 */
	public void updateTexts() {
		if (orientationOfShip == Orientation.Horizontal) {
			currentOrientationText.setText("Horizontal");
		} else {
			currentOrientationText.setText("Vertical");
		}
		sizeOfShipText.setText(shipSize + "");
	}

	/**
	 * Undo previous move
	 */
	public void undoMove() {
		try {

			Object jComboBoxItem = lastPlacedShipAndOrientation.pop();
			Orientation orientation = (Orientation) lastPlacedShipAndOrientation
					.pop();
			int lastSize = (int) lastPlacedShipAndOrientation.pop();
			int column = (int) lastPlacedShipAndOrientation.pop();
			int row = (int) lastPlacedShipAndOrientation.pop();

			if (orientation == Orientation.Horizontal) {
				for (int i = column; i < column + lastSize; i++) {
					hasShip[row][i] = false;
					ships[row][i] = null;
					localButtons[row][i].setBackground(Color.gray);
				}
			} else {
				for (int i = row; i < row + lastSize; i++) {
					hasShip[i][column] = false;
					ships[i][column] = null;
					localButtons[i][column].setBackground(Color.gray);
				}
			}

			clickedShips[row][column] = null;

			listOfNames.addItem(jComboBoxItem);
			listOfNames.setSelectedItem(jComboBoxItem);
			updateHealth();
			shipArrayCounter--;

		} catch (EmptyStackException e) {

		}
	}

	/**
	 * Updates the health bar
	 */
	public void updateHealth() {
		int counter = numberOfShips();
		healthBar.setValue(counter);

	}

	/**
	 * @return The number of current ships.
	 */
	public int numberOfShips() {
		int counter = 0;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (hasShip[i][j]) {
					counter++;
				}
			}
		}
		return counter;
	}

	/**
	 * Sets up the health bar
	 */
	public void setUpHealthBar() {

		healthPanel = new JPanel();
		healthBar = new JProgressBar(0, 17);

		// Set up health bar
		healthBar.setForeground(new Color(0, 169, 43));
		updateHealth();

		// Start adding everything to the panels
		healthPanel.add(healthBar);

	}

	/**
	 * Checks whether already button has a ship, and colours it accordingly;
	 * then updates health bar.
	 * 
	 * @param i
	 *            Row
	 * @param j
	 *            Column
	 */
	public void setShipsHit(int i, int j) {

		if (hasShip[j][i]) {
			hasShip[j][i] = false;
			localButtons[j][i].setBackground(Color.red);
		} else {
			localButtons[j][i].setBackground(Color.blue);
		}
		if (numberOfShips() <= 0) {
			JOptionPane.showMessageDialog(new JFrame(), "You lose!");
			AppManager am = room.getAppManager();
			am.closeRoom(room.getRoomID());
			room.dispose();

		}
		updateHealth();
		disableAllButtons();
	}

	/**
	 * Disables the map.
	 */
	public void disableAllButtons() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				localButtons[i][j].setEnabled(false);
			}
		}

	}

	/**
	 * Enables the map.s
	 */
	public void enableAllButtons() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				localButtons[i][j].setEnabled(true);
			}
		}
	}

	/**
	 * Checks if the button with the column and row in the parameters have a
	 * ship.
	 * 
	 * @param column
	 * @param row
	 * @return whether it has a ship
	 */
	public boolean hasAShip(int column, int row) {
		if (hasShip[column][row]) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 
	 * @return a two-dimensional array of ships.
	 */
	public Ship[][] getShips() {
		return ships;
	}

}
