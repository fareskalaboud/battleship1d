package battleships1d;

import java.awt.Color;

import javax.swing.JDialog;
import javax.swing.JFrame;

/**
 * 
 * @author Alexander Hanbury-Botherway
 *
 */
public class EnemyButton extends GameButton {

	EnemyButtonState state;
	Room room;

	/**
	 * Constructor that takes the button's row and column on the grid, and also
	 * the room it's located in.
	 * 
	 * @param row
	 * @param col
	 * @param room
	 */
	public EnemyButton(int row, int col, Room room) {
		super(row, col);
		this.room = room;
		state = EnemyButtonState.NOT_PLAYED;
	}

	/**
	 * Disables the button so it can't be played again.
	 *
	 */

	public void playButton() {
		Result resultOfPlay = room.getAM().playButton(row, col);

		setEnabled(false);

		if (resultOfPlay.equals(Result.MISS)) {
			state = EnemyButtonState.MISS;
			setBackground(Color.RED);
			room.getAM().sendMove();
			return;
		}
		if (resultOfPlay.equals(Result.SUNK)) {
			return;
		}
		state = EnemyButtonState.HIT;
		setBackground(Color.GREEN);
		room.getAM().sendMove();
		return;
	}

	/**
	 * 
	 * @return the enemy button state (hit, sink, miss)
	 */
	public EnemyButtonState getState() {
		return state;
	}

	/**
	 * Sets the enemy button state.
	 * 
	 * @param state
	 *            (e.g. hit, sink, miss)
	 */
	public void setState(EnemyButtonState state) {
		this.state = state;
	}

}
