package battleships1dAI;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import java.util.Random;

public class AIEnemyMap extends EnemyMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean[][] movesYouCanTry;

	public AIEnemyMap(Map map) {
		super(map);
		movesYouCanTry = new boolean[10][10];
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				movesYouCanTry[i][j] = false;
			}
		}

	}

	public void setUpUI() {
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(10, 10));

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				buttons[i][j] = new JButton("");
				mainPanel.add(buttons[i][j]);
			}
		}

		JPanel bottomPanel = new JPanel();
		JButton moveAI = new JButton("Move");
		moveAI.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AIMove();
			}

		});
		
		bottomPanel.add(moveAI);
		bottomPanel.setLayout(new FlowLayout());
		JLabel counterLabel = new JLabel("Moves done: ");
		counterTextArea = new JTextArea(""+moveCounter);
		
		bottomPanel.add(counterLabel);
		bottomPanel.add(counterTextArea);
		
		
		add(bottomPanel, BorderLayout.SOUTH);
		add(mainPanel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
	}

	public void AIMove() {
		int[] tryMove = availableAIMove();
		int row = tryMove[0];
		int column = tryMove[1];
		 if(!wasShot(row, column)){
			 hitSquare(row, column);
			 if(isHit(row, column) == true){
				 buttons[row][column].setText("X");
				 setMovesYouCanTry(row, column);

			 } else{
				 buttons[row][column].setText("O");
			 }
			 
			 movesYouCanTry[row][column] = false;
			 
			 
			 
			 moveCounter++;
			 counterTextArea.setText(""+moveCounter);
			 
			 if(checkWin()){
				 JOptionPane.showMessageDialog(new JFrame(), "You win");
			 }
		}
		 
	}

	public void setMovesYouCanTry(int row, int column){
		if(!wasShot(row + 1, column)){
			
			movesYouCanTry[row + 1][column] = true;

		}
		
		if (!wasShot(row - 1, column)) {

				movesYouCanTry[row - 1][column] = true;

		}
		
		if(!wasShot(row, column + 1)){
	
				movesYouCanTry[row][column + 1] = true;

		}
		
		if(!wasShot(row, column - 1)){
			
				movesYouCanTry[row][column - 1] = true;

		}
	}
	
	


	public int[] availableAIMove() {
		int[] availableMoves = new int[2];
		boolean availableMove = false;
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (movesYouCanTry[i][j] == true) {
					availableMoves[0] = i;
					availableMoves[1] = j;
					return availableMoves;
				}
			}
		}

		if (availableMove == false) {
			int random1;
			int random2;
			do {
				Random rng = new Random();
				random1 = rng.nextInt(10);
				random2 = rng.nextInt(10);
			} while (hasBeenHit[random1][random2] != false);

			availableMoves[0] = random1;
			availableMoves[1] = random2;
		}

		return availableMoves;
	}

	public static void main(String args[]) {

	}
}
