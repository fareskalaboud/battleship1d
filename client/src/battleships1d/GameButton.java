package battleships1d;

import javax.swing.JButton;
/**
 * Represents the game buttons in a players own map
 * @author Alexander Hanbury-Botherway
 *
 */
@SuppressWarnings("serial")
public class GameButton extends JButton {
	private int row;
	private int col;
	private boolean hasShip;
	private Ship ship; //null if no ship
	
	public GameButton(int row, int col){
		super(" ");
		this.row = row;
		this.col = col;
		hasShip = false;
		ship = null;
	}
	
	/**
	 * Adds ship to the GameButton, returns true if successful, false if ship already present
	 * @param ship
	 * @return false if ship already present
	 */
	public boolean addShip(Ship ship){
		if (hasShip){
			return false;
		}
		this.ship = ship;
		hasShip = true;
		return true;
	}
	
	/**
	 * The ship that is at the button (null if no ship)
	 * @return the ship
	 */
	public Ship getShip(){
		return ship;
	}
	
	/**
	 * Used to play the button, called from ActionListener of enemy 
	 * @return Sink if critical hit, hit if hit but not sunk, miss if not hit
	 */
	public Result playButton(){
		if(hasShip){
			boolean sunk = ship.hitShip();
			if (sunk){
				return Result.SINK;
			} return Result.HIT;
		}
		return Result.MISS;
	}
	
	
}
