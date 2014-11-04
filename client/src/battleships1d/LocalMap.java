package battleships1d;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * @author faresalaboud
 */
public class LocalMap extends Map {

	private boolean[][] hasShip;
	private LocalButton[][] localButtons;
	private Ship[][] ships;

	// for testing purposes
	private static int[] shipSize = { 2, 3, 3, 4, 5 };
	private int shipArrayCounter = 0;

	public LocalMap(Room room) {
		super();
		// Initialise the whole map as not having any ship
		hasShip = new boolean[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				hasShip[i][j] = false;
			}
		}

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				final int row = i;
				final int column = j;
				localButtons[i][j] = new LocalButton(i, j);
			}
		}

		ships = new Ship[10][10];

		// TODO: Fix next line

		this.setUpUI();

	}

	public void setUpHealthBar() {

	}

	public void setUpUI() {
		this.setLayout(new BorderLayout());
		this.setSize(300, 300);

		// Initialise UI objects
		mapPanel = new JPanel(new GridLayout(10, 10));

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				final int row = i;
				final int column = j;
				localButtons[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						if(shipArrayCounter > 5){
						
						} else{						
							Ship genericShip = new Ship(shipSize[shipArrayCounter],
									Orientation.HORIZONTAL);
							int size = genericShip.getSize();
							Orientation orientation = genericShip.getOrientation();
							if (checkIfCanAddShip(size, row, column, orientation)) {
								if (orientation == Orientation.HORIZONTAL) {
									for (int i = column; i < column + size; i++) {
										hasShip[row][i] = true;
										ships[row][i] = genericShip;
	
									}
								} else {
									for (int i = row; i < row + size; i++) {
										hasShip[i][column] = true;
										ships[i][column] = genericShip;
									}
								}
								shipArrayCounter++;
							}
						}
						
						
					}

				});

				mapPanel.add(localButtons[i][j]);
			}
		}

		healthPanel = new JPanel();
		healthBar = new JProgressBar(0, 17);

		// Set up health bar
		healthBar.setForeground(new Color(0, 169, 43));
		healthBar.setValue(healthBar.getMaximum());
		// healthBar.setMaximumSize();

		// Start adding everything to the panels
		healthPanel.add(healthBar);

		JPanel bottomPanel = new JPanel();
		JButton finished = new JButton("Finished");
		finished.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateLocalButtons();
			}

		});

		// Add the panels to the map
		this.add(mapPanel, BorderLayout.CENTER);
		mapPanel.add(healthPanel, BorderLayout.NORTH);
		mapPanel.add(bottomPanel,BorderLayout.SOUTH);

	}

	public void setActionListeners() {

	}

	public LocalButton getClikedButton(int row, int col) {
		// TO DO : Fares, return the button at given coordinates;
		return new LocalButton(0, 0);

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

}
