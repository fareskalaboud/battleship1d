package battleships1d;

/**
 * 
 * @author Alexander Hanbury-Botherway
 *
 */
public class Ship {
	private int size;
	private Orientation orientation; 
	private int health;
	private boolean isSunk;
	private String name;

	public Ship(int size, Orientation orientation, String name) {
		this.size = size;
		this.orientation = orientation;
		this.name = name;
		isSunk = false;
		health = size;
	}
	
	public Ship(int size, Orientation orientation){
		this(size, orientation,"");
		switch (size) {
		case 2: name = "Patrol boat";
		break;
		case 3: name = "Destroyer";
		break;
		case 4: name = "Battleship";
		break;
		case 5: name = "Aircraft Carrier";
		break;
		default: name = "Nuclear Submarine";
		}
	}

	/**
	 * Called if a ship has been hit, takes one health away, and returns true if
	 * ship has sunk
	 * 
	 * @return true if ship has sunk
	 */
	public boolean hitShip() {
		health--;
		if (health == 0){
			isSunk = true;
		} return isSunk;
	}
	
	/**
	 * Returns the name of the ship
	 * @return name of ship
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the ship
	 * @param name of ship
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Returns orientation of the ship
	 * @return orientation of ship
	 */
	public Orientation getOrientation(){
		return orientation;
	}
	
	/**
	 * Returns the size of a ship
	 * @return size of ship
	 */
	public int getSize(){
		return size;
	}
}
