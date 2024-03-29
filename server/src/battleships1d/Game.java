package battleships1d;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Thomas on 4/11/2014.
 */
public class Game {

	private Room room;
	private HashMap<String, Ship> hostsShips = new HashMap<String, Ship>();
	private HashMap<String, Ship> guestsShips = new HashMap<String, Ship>();

	private Boolean hostReady = false;
	private Boolean guestReady = false;
	private Connection starter = null;

	/**
	 * Constructor
	 * 
	 * @param room
	 *            - passing the room Object;
	 */
	public Game(Room room) {
		this.room = room;
	}

	/**
	 * Handling the command from the game
	 * 
	 * @param cmd
	 * @param connection
	 */
	public void handleCommand(Command cmd, Connection connection) {
		if (cmd.getParameters().length <= 1)
			return;
		if (cmd.getParameters()[0].equals("Setup")) {
			if (cmd.getParameters().length <= 1)
				return;
			if (cmd.getParameters()[1].equals("Ship")) {
				if (cmd.getParameters().length != 6)
					return;
				String shipName = cmd.getParameters()[2];
				int x;
				int y;
				boolean vertical = false;
				if (cmd.getParameters()[5].equals("Vertical"))
					vertical = true;

				if (tryParseInt(cmd.getParameters()[3]))
					x = Integer.parseInt(cmd.getParameters()[3]);
				else {
					connection.writeLine("Game::Setup::Ship::Error::InvalidX");
					return;
				}
				if (tryParseInt(cmd.getParameters()[4]))
					y = Integer.parseInt(cmd.getParameters()[4]);
				else {
					connection.writeLine("Game::Setup::Ship::Error::InvalidY");
					return;
				}

				if (connection.getUser().getUsername()
						.equals(room.getHost().getUsername())) {
					// Host
					setHostShip(shipName, x, y, vertical);
					connection.writeLine("Game::Setup::Ship::Success");
				} else {
					// Guest
					setGuestShip(shipName, x, y, vertical);
					connection.writeLine("Game::Setup::Ship::Success");
				}
			} else if (cmd.getParameters()[1].equals("Ready")) {
				if (connection.getUser().getUsername()
						.equals(room.getHost().getUsername())) {
					// Host
					if (hostsShips.size() != 5) {
						connection
								.writeLine("Game::Setup::Ready::Error::InsufficientShips");
						return;
					} else {
						connection.writeLine("Game::Setup::Ready::Success");
						hostReady = true;
						if (starter == null)
							starter = connection;
					}
				} else {
					// Guest
					if (guestsShips.size() != 5) {
						connection
								.writeLine("Game::Setup::Ready::Error::InsufficientShips");
						return;
					} else {
						connection.writeLine("Game::Setup::Ready::Success");
						guestReady = true;
						if (starter == null)
							starter = connection;
					}
				}

				if (hostReady && guestReady) {
					// Start Game
					starter.writeLine("Game::Turn");
				}
			}
		} else if (cmd.getParameters()[0].equals("Fire")) {
			if (cmd.getParameters().length != 3)
				return;
			int x = Integer.parseInt(cmd.getParameters()[1]);
			int y = Integer.parseInt(cmd.getParameters()[2]);
			if (connection.getUser().getUsername()
					.equals(room.getHost().getUsername())) {
				// Host Fired
				boolean hit = false;
				for (Ship ship : guestsShips.values()) {
					hit = shipFired(ship, x, y, room.getHost().getConnection(),
							room.getGuest().getConnection());
					if (hit) {
						if (allShipsDead(guestsShips)) {
							room.getHost().getConnection()
									.writeLine("Game::Win");
							room.getGuest().getConnection()
									.writeLine("Game::Lose");
						}
						break;
					}
				}
				if (!hit) {
					room.getHost().getConnection()
							.writeLine("Game::Fire::Miss");
					room.getGuest().getConnection()
							.writeLine("Game::Fired::" + x + "::" + y);
					room.getGuest().getConnection().writeLine("Game::Turn");
				}
			} else {
				// Guest Fired
				boolean hit = false;
				for (Ship ship : hostsShips.values()) {
					hit = shipFired(ship, x, y,
							room.getGuest().getConnection(), room.getHost()
									.getConnection());
					if (hit) {
						if (allShipsDead(guestsShips)) {
							room.getHost().getConnection()
									.writeLine("Game::Win");
							room.getGuest().getConnection()
									.writeLine("Game::Lose");
						}
						break;
					}
				}
				if (!hit) {
					room.getGuest().getConnection()
							.writeLine("Game::Fire::Miss");
					room.getHost().getConnection()
							.writeLine("Game::Fired::" + x + "::" + y);
					room.getHost().getConnection().writeLine("Game::Turn");
				}
			}
		}
	}

	/**
	 * Checking for the fired Button;
	 * 
	 * @param ship
	 * @param x
	 * @param y
	 * @param connection
	 * @param otherConnection
	 * @return
	 */
	private boolean shipFired(Ship ship, int x, int y, Connection connection,
			Connection otherConnection) {
		if (ship.isVertical()) {
			int start = ship.getY();
			int end = ship.getLength() + start;
			if (y >= start && y < end && x == ship.getX()) {
				int indexOfHealth = y - start;
				if (ship.getHealth()[indexOfHealth] == 1) {
					ship.getHealth()[indexOfHealth] = 0;
					boolean destroyed = true;
					for (int i = 0; i < ship.getHealth().length; i++) {
						if (ship.getHealth()[i] == 1)
							destroyed = false;
					}
					if (destroyed)
						connection.writeLine("Game::Fire::Sunk::"
								+ ship.getName());
					else
						connection.writeLine("Game::Fire::Hit");
					otherConnection.writeLine("Game::Fired::" + x + "::" + y);
					return true;
				}
			}
		} else {
			int start = ship.getX();
			int end = ship.getLength() + start;
			if (x >= start && x < end && y == ship.getY()) {
				int indexOfHealth = x - start;
				if (ship.getHealth()[indexOfHealth] == 1) {
					ship.getHealth()[indexOfHealth] = 0;
					boolean destroyed = true;
					for (int i = 0; i < ship.getHealth().length; i++) {
						if (ship.getHealth()[i] == 1)
							destroyed = false;
					}
					if (destroyed)
						connection.writeLine("Game::Fire::Sunk::"
								+ ship.getName());
					else
						connection.writeLine("Game::Fire::Hit");
					otherConnection.writeLine("Game::Fired::" + x + "::" + y);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check if all the ships sunk
	 * 
	 * @param ships
	 * @return
	 */
	private boolean allShipsDead(HashMap<String, Ship> ships) {
		for (Ship ship : ships.values()) {
			for (int i = 0; i < ship.getHealth().length; i++) {
				if (ship.getHealth()[i] == 1)
					return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * @param shipName
	 * @param x
	 * @param y
	 * @param vertical
	 */
	public void setHostShip(String shipName, int x, int y, boolean vertical) {
		setShip(true, shipName, x, y, vertical);
	}

	/**
	 * 
	 * @param shipName
	 * @param x
	 * @param y
	 * @param vertical
	 */
	public void setGuestShip(String shipName, int x, int y, boolean vertical) {
		setShip(false, shipName, x, y, vertical);
	}

	/**
	 * 
	 * @param host
	 * @param shipName
	 * @param x
	 * @param y
	 * @param vertical
	 */
	private void setShip(Boolean host, String shipName, int x, int y,
			boolean vertical) {
		if (host) {
			if (hostsShips.containsKey(shipName)) {
				hostsShips.get(shipName).setX(x);
				hostsShips.get(shipName).setY(y);
			} else {
				Ship ship = new Ship(shipName, x, y, vertical);
				hostsShips.put(shipName, ship);
			}
		} else {
			if (guestsShips.containsKey(shipName)) {
				guestsShips.get(shipName).setX(x);
				guestsShips.get(shipName).setY(y);
			} else {
				Ship ship = new Ship(shipName, x, y, vertical);
				guestsShips.put(shipName, ship);
			}
		}
	}

	/**
	 * Try-Catch for the String value
	 * 
	 * @param value
	 * @return
	 */
	private boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException nfe) {
			return false;
		}
	}

	/**
	 * The class representing the ship
	 * 
	 * @author Tom
	 * 
	 */
	private class Ship {
		private String name;
		private int x;
		private int y;
		private int length;
		private int[] health;
		private boolean vertical;

		/**
		 * The Constructor for the ship
		 * 
		 * @param name
		 * @param x
		 * @param y
		 * @param vertical
		 */
		public Ship(String name, int x, int y, boolean vertical) {
			this.name = name;
			this.x = x;
			this.y = y;
			this.vertical = vertical;
			if (name.equals("AircraftCarrier")) {
				length = 5;
				health = new int[] { 1, 1, 1, 1, 1 };
			} else if (name.equals("Battleship")) {
				length = 4;
				health = new int[] { 1, 1, 1, 1 };
			} else if (name.equals("Submarine")) {
				length = 3;
				health = new int[] { 1, 1, 1 };
			} else if (name.equals("Destroyer")) {
				length = 3;
				health = new int[] { 1, 1, 1 };
			} else if (name.equals("PatrolBoat")) {
				length = 2;
				health = new int[] { 1, 1 };
			}
		}

		/**
		 * 
		 * @return the name of the ship
		 */
		public String getName() {
			return name;
		}

		/**
		 * Setting the name of the ship
		 * 
		 * @param name
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * 
		 * @return the X coordinates;
		 */
		public int getX() {
			return x;
		}

		/**
		 * Set the X coordinates
		 * 
		 * @param x
		 */
		public void setX(int x) {
			this.x = x;
		}

		/**
		 * 
		 * @return the Y coordinates;
		 */
		public int getY() {
			return y;
		}

		/**
		 * Set the Y coordinates;
		 * 
		 * @param y
		 */
		public void setY(int y) {
			this.y = y;
		}

		/**
		 * 
		 * @return the length of the ship;
		 */
		public int getLength() {
			return length;
		}

		/**
		 * Set the length of the ship;
		 * 
		 * @param length
		 */
		public void setLength(int length) {
			this.length = length;
		}

		/**
		 * 
		 * @return - the remaining help
		 */
		public int[] getHealth() {
			return health;
		}

		/**
		 * Set the health(initially);
		 * 
		 * @param health
		 */
		public void setHealth(int[] health) {
			this.health = health;
		}

		/**
		 * Check if the ship is vertical or not;
		 * 
		 * @return - boolean;
		 */
		public boolean isVertical() {
			return vertical;
		}

		/**
		 * Set the ship vertical or horizontal;
		 * 
		 * @param vertical
		 */
		public void setVertical(boolean vertical) {
			this.vertical = vertical;
		}
	}
}
