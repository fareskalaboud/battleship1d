package battleships1d;

/**
 * 
 * @author Alexander Hanbury-Botherway
 *
 */
public class LocalButton extends GameButton {
	
	private boolean hasShip;
	private Ship ship; //null if no ship

	public LocalButton(int row, int col) {
		super(row, col);
		hasShip = false;
		ship = null;
	}
	/**z
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
	
	/**
	 * The ship that is at the button (null if no ship)
	 * @return the ship
	 */
	public Ship getShip(){
		return ship;
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
}
