package battleships1dAI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Map extends JFrame {

	/**
	 * 
	 */
	

	private String[][] unitSquare;
	private JButton[][] button;

	public static final String[] battleships = { "X", "X", "X", "X", "X" };
	public static final int[] battlesize = { 2, 4, 5, 3, 3 };
	private int shipNumber;
	private boolean isHorizontal = true;

	private JTextArea horizontalOrVertical;
	private JTextArea size;

	public Map() {
		unitSquare = new String[10][10];
		button = new JButton[10][10];
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				unitSquare[i][j] = "";
			}
		}
		shipNumber = 0;

		setUpUI();
	}

	public void setUpUI() {

		JPanel topPanel = new JPanel();
		JLabel orientation = new JLabel("Orientation: ");
		horizontalOrVertical = new JTextArea("Horizontal");
		JLabel sizeLabel = new JLabel("Size");
		size = new JTextArea("" + battlesize[shipNumber]);

		topPanel.add(orientation);
		topPanel.add(horizontalOrVertical);
		topPanel.add(sizeLabel);
		topPanel.add(size);
		topPanel.setLayout(new FlowLayout());

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(10, 10));
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				final int row = i;
				final int column = j;
				button[i][j] = new JButton("");
				button[i][j].addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						// TODO Auto-generated method stub
						setUpActionListeners(row, column);
						updateButtonText();
						if (shipNumber > 4) {

						} else {
							size.setText("" + battlesize[shipNumber]);
						}

					}

				});

				mainPanel.add(button[i][j]);

			}
		}

		JPanel bottomPanel = new JPanel();

		JButton horizontalButton = new JButton("Flip Orientation");
		horizontalButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				flipOrientation();
			}

		});

		JButton launchEnemyView = new JButton("Launch Enemy View");
		launchEnemyView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				launchEnemyMap();
			}

		});

		JButton launchAIEnemyView = new JButton("Launch AI");
		launchAIEnemyView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				launchAIEnemyMap();

			}

		});

		bottomPanel.setLayout(new FlowLayout());
		bottomPanel.add(horizontalButton);
		bottomPanel.add(launchEnemyView);
		bottomPanel.add(launchAIEnemyView);

		add(topPanel, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	public void updateButtonText() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				button[i][j].setText(unitSquare[i][j]);
			}
		}
	}

	public void setUpActionListeners(int row, int column) {
		if (shipNumber < 5
				&& canItFit(battlesize[shipNumber], row, column, isHorizontal)) {
			addShip(battleships[shipNumber], battlesize[shipNumber], row,
					column, isHorizontal);
			shipNumber++;

		}

	}

	public void flipOrientation() {
		if (isHorizontal == true) {
			isHorizontal = false;
			horizontalOrVertical.setText("Vertical");

		} else {
			isHorizontal = true;
			horizontalOrVertical.setText("Horizontal");
		}
	}

	public void addShip(String name, int size, int row, int column,
			boolean isHorizontal) {
		Ship newShip = new Ship(name, size, isHorizontal);
		String shipName = newShip.getName();
		int shipSize = newShip.getSize();
		boolean shipHorizontal = newShip.isHorizontal();

		if (canItFit(shipSize, row, column, shipHorizontal) == true) {
			if (shipHorizontal == true) {
				for (int i = column; i < column + size; i++) {
					unitSquare[row][i] = shipName;
				}
			} else {
				for (int i = row; i < row + size; i++) {
					unitSquare[i][column] = shipName;
				}
			}
		}
	}

	public boolean canItFit(int size, int row, int column, boolean isHorizontal) {
		boolean fits = true;
		try {
			if (isHorizontal == true) {
				for (int i = column; i < column + size; i++) {
					if (unitSquare[row][i].equals("")) {

					} else {
						fits = false;
					}
				}
			} else {
				for (int i = row; i < row + size; i++) {
					if (unitSquare[i][column].equals("")) {

					} else {
						fits = false;
					}
				}
			}

		} catch (ArrayIndexOutOfBoundsException e) {
			fits = false;
		}
		return fits;
	}

	public String[][] getUnitSquares() {
		return unitSquare;
	}

	public void launchEnemyMap() {
		new EnemyMap(this);
	}

	public void launchAIEnemyMap() {
		new AIEnemyMap(this);
	}


}
