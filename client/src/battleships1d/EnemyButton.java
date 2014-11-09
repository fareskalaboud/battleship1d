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
	
	public EnemyButton(int row, int col, Room room) {
		super(row, col);
		this.room = room;
		state = EnemyButtonState.NOT_PLAYED;
	}
	
	/**
	 * Disables the button so it can't be played again. 
	 */
	public void playButton(){
		Result resultOfPlay = room.getAM().playButton(row, col);
		
		setEnabled(false);
		
		if (resultOfPlay.equals(Result.MISS)){
			state = EnemyButtonState.MISS;
			setBackground(Color.RED);
			room.getAM().sendMove();
			return;
		} if (resultOfPlay.equals(Result.SUNK)){
            //TODO Is there something to go into here? - Tom
            return;
		}
        state = EnemyButtonState.HIT;
		setBackground(Color.GREEN);
		room.getAM().sendMove();
		return;
	}
	
	public EnemyButtonState getState(){
		return state;
	}
	
	public void setState(EnemyButtonState state){
		this.state = state;
	}

}
