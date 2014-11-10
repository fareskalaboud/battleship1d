package battleships1d;

import javax.swing.JButton;

/**
 * Represents the game buttons in a players own map
 * 
 * @author Alexander Hanbury-Botherway
 *
 */
@SuppressWarnings("serial")
public abstract class GameButton extends JButton {
	protected int row;
	protected int col;

	public GameButton(int row, int col) {
		super("");
		this.row = row;
		this.col = col;

		char[] rowLetters = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J' };
		int correctedCol = col + 1;

		setToolTipText(rowLetters[row] + "" + correctedCol);

		setSize(10, 10);
	}
}