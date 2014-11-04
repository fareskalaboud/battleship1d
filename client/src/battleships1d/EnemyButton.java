package battleships1d;

import java.awt.Color;

/**
 * 
 * @author Alexander Hanbury-Botherway
 *
 */
public class EnemyButton extends GameButton {

	EnemyButtonState state;
	Room room;
	
	public EnemyButton(int row, int col, Room room) {
		super(row, col);
		this.room = room;
		state = EnemyButtonState.NOT_PLAYED;
	}
	
	/**
	 * Disables the button so it can't be played again. 
	 */
	public void playButton(){
		//For test:
		Result resultOfPlay = Result.SUNK;
		//Result resultOfPlay = room.playButton(row, col);
		
		setEnabled(false);
		
		if (resultOfPlay.equals(Result.MISS)){
			state = EnemyButtonState.MISS;
			setBackground(Color.RED);
			return;
		} if (resultOfPlay.equals(Result.SUNK)){
			System.out.println("You sunk a ship");
		} state = EnemyButtonState.HIT;
			setBackground(Color.GREEN);
			return;
	}

}
