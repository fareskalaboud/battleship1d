package battleships1d;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.Stack;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;

/**
 * 
 * @author faresalaboud
 */
public class LocalMap extends Map {

	private boolean[][] hasShip;
	private LocalButton[][] localButtons;
	private Ship[][] ships;
	private Orientation orientationOfShip;

	private Stack lastPlacedShipAndOrientation;

	// for testing purposes
	private int shipSize;
	private int shipArrayCounter = 0;
	private static String[] shipNames = { "Aircraft Carrier", "Battleship",
			"Submarine", "Cruiser", "Patrol boat" };

	JTextField sizeOfShipText;
	JTextField currentOrientationText;
	final JComboBox listOfNames = new JComboBox(shipNames);

	public LocalMap(Room room) {
		super();
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

		orientationOfShip = Orientation.HORIZONTAL;

		lastPlacedShipAndOrientation = new Stack();
		// pop order: 1st = orientation, 2nd = name
		// 3rd = size, 4th = column, 5th = row

		this.setUpUI();

	}


	public void setUpUI() {
		this.setLayout(new BorderLayout());
		this.setSize(300, 300);

		// Initialise UI objects
		mapPanel = new JPanel(new GridLayout(10, 10));

		JPanel topPanel = new JPanel();

		// @Cham TODO: finish this
		JPanel infoPanel = new JPanel();
		JLabel sizeOfShip = new JLabel("Size: ");
		sizeOfShipText = new JTextField();

		JLabel currentOrientation = new JLabel("Current Orientation: ");
		currentOrientationText = new JTextField();

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
				// TODO Auto-generated method stub
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
						// TODO Auto-generated method stub
						if (shipArrayCounter > 4) {

						} else {
							Ship genericShip = new Ship(shipSize,
									orientationOfShip);
							int size = genericShip.getSize();
							Orientation orientation = genericShip
									.getOrientation();
							if (checkIfCanAddShip(size, row, column,
									orientation)) {
								if (orientation == Orientation.HORIZONTAL) {
									for (int i = column; i < column + size; i++) {
										hasShip[row][i] = true;
										ships[row][i] = genericShip;
										localButtons[row][i]
												.setBackground(Color.green);
									}
								} else {
									for (int i = row; i < row + size; i++) {
										hasShip[i][column] = true;
										ships[i][column] = genericShip;
										localButtons[i][column]
												.setBackground(Color.green);
									}
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

		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout());

		JButton finished = new JButton("Finished");
		finished.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateLocalButtons();
			}

		});
		bottomPanel.add(finished);

		JButton flipOrientation = new JButton("Flip Orientation");
		flipOrientation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				flipOrientation();
				updateTexts();
			}

		});

		JButton undoMove = new JButton("Undo Move");
		undoMove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				undoMove();
				updateTexts();
			}

		});

		bottomPanel.add(undoMove);
		bottomPanel.add(flipOrientation);

		// Add the panels to the map
		add(topPanel, BorderLayout.NORTH);
		add(mapPanel, BorderLayout.CENTER);
		// add(healthPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);

	}

	public void setActionListeners() {

	}

	public LocalButton getClikedButton(int row, int col) {
		// TO DO : Fares, return the button at given coordinates;
		return new LocalButton(0, 0);

	}
	
	public void setUpHealthBar(){

		healthPanel = new JPanel();
		healthBar = new JProgressBar(0, 17);

		// Set up health bar
		healthBar.setForeground(new Color(0, 169, 43));
		updateHealth();
		

		// Start adding everything to the panels
		healthPanel.add(healthBar);
		
	}

	public boolean checkIfCanAddShip(int size, int row, int column,
			Orientation orientation) {
		boolean canAddShip = true;
		try {
			if (orientation == Orientation.HORIZONTAL) {
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
	public void updateLocalButtons() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (hasShip[i][j]) {
					localButtons[i][j].addShip(ships[i][j]);
				}
			}
		}
	}

	public void flipOrientation() {
		if (orientationOfShip == Orientation.HORIZONTAL) {
			orientationOfShip = Orientation.VERTICAL;
		} else {
			orientationOfShip = Orientation.HORIZONTAL;
		}
	}

	public void updateSize(String battleShipName) {
		if (battleShipName.equals("Aircraft Carrier")) {
			shipSize = 5;
		} else if (battleShipName.equals("Battleship")) {
			shipSize = 4;
		} else if (battleShipName.equals("Submarine")) {
			shipSize = 3;
		} else if (battleShipName.equals("Cruiser")) {
			shipSize = 3;
		} else if (battleShipName.equals("Patrol boat")) {
			shipSize = 2;
		}
	}

	public void updateTexts() {
		if (orientationOfShip == Orientation.HORIZONTAL) {
			currentOrientationText.setText("Horizontal");
		} else {
			currentOrientationText.setText("Vertical");
		}
		sizeOfShipText.setText(shipSize + "");
	}

	public void undoMove() {
		try {

			Object jComboBoxItem = lastPlacedShipAndOrientation.pop();
			Orientation orientation = (Orientation) lastPlacedShipAndOrientation
					.pop();
			int lastSize = (int) lastPlacedShipAndOrientation.pop();
			int column = (int) lastPlacedShipAndOrientation.pop();
			int row = (int) lastPlacedShipAndOrientation.pop();

			if (orientation == Orientation.HORIZONTAL) {
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

			listOfNames.addItem(jComboBoxItem);
			listOfNames.setSelectedItem(jComboBoxItem);
			updateHealth();
			shipArrayCounter--;

		} catch (EmptyStackException e) {

		}
	}
	

	public LocalButton getButton(int row, int col){
		return localButtons[row][col];
	}

	
	public void updateHealth(){
		int counter = 0;
		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				if(hasShip[i][j]){
					counter++;
				}
			}
		}		
		healthBar.setValue(counter);	

	}


}
