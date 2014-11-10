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

    public Game(Room room) {
        this.room = room;
    }

    public void handleCommand(Command cmd, Connection connection) {
        if (cmd.getParameters().length <= 1) return;
        if (cmd.getParameters()[0].equals("Setup")) {
            if (cmd.getParameters().length <= 1) return;
            if (cmd.getParameters()[1].equals("Ship")) {
                if (cmd.getParameters().length != 6) return;
                String shipName = cmd.getParameters()[2];
                int x;
                int y;
                boolean vertical = false;
                if (cmd.getParameters()[5].equals("Vertical")) vertical = true;

                if (tryParseInt(cmd.getParameters()[3])) x = Integer.parseInt(cmd.getParameters()[3]);
                else {
                    connection.writeLine("Game::Setup::Ship::Error::InvalidX");
                    return;
                }
                if (tryParseInt(cmd.getParameters()[4])) y = Integer.parseInt(cmd.getParameters()[4]);
                else {
                    connection.writeLine("Game::Setup::Ship::Error::InvalidY");
                    return;
                }

                if (connection.getUser().getUsername().equals(room.getHost().getUsername())) {
                    //Host
                    setHostShip(shipName, x, y, vertical);
                    connection.writeLine("Game::Setup::Ship::Success");
                } else {
                    //Guest
                    setGuestShip(shipName, x, y, vertical);
                    connection.writeLine("Game::Setup::Ship::Success");
                }
            } else if (cmd.getParameters()[1].equals("Ready")) {
                if (connection.getUser().getUsername().equals(room.getHost().getUsername())) {
                    //Host
                    if (hostsShips.size() != 5) {
                        connection.writeLine("Game::Setup::Ready::Error::InsufficientShips");
                        return;
                    }
                    else {
                        connection.writeLine("Game::Setup::Ready::Success");
                        hostReady = true;
                    }
                } else {
                    //Guest
                    if (guestsShips.size() != 5) {
                        connection.writeLine("Game::Setup::Ready::Error::InsufficientShips");
                        return;
                    }
                    else {
                        connection.writeLine("Game::Setup::Ready::Success");
                        guestReady = true;
                    }
                }

                if (hostReady && guestReady) {
                    //Start Game
                	room.getHost().getConnection().writeLine("Game::Turn");
                }
            }
        } else if (cmd.getParameters()[0].equals("Fire")) {
            if (cmd.getParameters().length != 3) return;
            int x = Integer.parseInt(cmd.getParameters()[1]);
            int y = Integer.parseInt(cmd.getParameters()[2]);
            if (connection.getUser().getUsername().equals(room.getHost().getUsername())) {
                //Host Fired
                boolean hit = false;
                for (Ship ship : guestsShips.values()) {
                    hit = shipFired(ship, x, y, room.getHost().getConnection(), room.getGuest().getConnection());
                    if (hit) {
                        if (allShipsDead(guestsShips)) {
                            room.getHost().getConnection().writeLine("Game::Win");
                            room.getGuest().getConnection().writeLine("Game::Lose");
                        }
                        break;
                    }
                }
                if (!hit) {
                    room.getHost().getConnection().writeLine("Game::Fire::Miss");
                    room.getGuest().getConnection().writeLine("Game::Fired::" + x + "::" + y);
                    room.getGuest().getConnection().writeLine("Game::Turn");
                }
            } else {
                //Guest Fired
                boolean hit = false;
                for (Ship ship : hostsShips.values()) {
                    hit = shipFired(ship, x, y, room.getGuest().getConnection(), room.getHost().getConnection());
                    if (hit) {
                        if (allShipsDead(guestsShips)) {
                            room.getHost().getConnection().writeLine("Game::Win");
                            room.getGuest().getConnection().writeLine("Game::Lose");
                        }
                        break;
                    }
                }
                if (!hit) {
                    room.getGuest().getConnection().writeLine("Game::Fire::Miss");
                    room.getHost().getConnection().writeLine("Game::Fired::" + x + "::" + y);
                    room.getHost().getConnection().writeLine("Game::Turn");
                }
            }
        }
    }

    private boolean shipFired(Ship ship, int x, int y, Connection connection, Connection otherConnection) {
        if (ship.isVertical()) {
            int start = ship.getY();
            int end = ship.getLength() + start;
            if (y >= start && y < end && x == ship.getX()) {
                int indexOfHealth = y - start;
                if (ship.getHealth()[indexOfHealth] == 1) {
                    ship.getHealth()[indexOfHealth] = 0;
                    boolean destroyed = true;
                    for (int i = 0; i < ship.getHealth().length; i++) {
                        if (ship.getHealth()[i] == 1) destroyed = false;
                    }
                    if (destroyed) connection.writeLine("Game::Fire::Sunk::" + ship.getName());
                    else connection.writeLine("Game::Fire::Hit");
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
                        if (ship.getHealth()[i] == 1) destroyed = false;
                    }
                    if (destroyed) connection.writeLine("Game::Fire::Sunk::" + ship.getName());
                    else connection.writeLine("Game::Fire::Hit");
                    otherConnection.writeLine("Game::Fired::" + x + "::" + y);
                    return true;
                }
            }
        }
        return false;
    }

    private boolean allShipsDead(HashMap<String, Ship> ships) {
        for (Ship ship : ships.values()) {
            for (int i = 0; i < ship.getHealth().length; i++) {
                if (ship.getHealth()[i] == 1) return false;
            }
        }
        return true;
    }

    public void setHostShip(String shipName, int x, int y, boolean vertical) {
        setShip(true, shipName, x, y, vertical);
    }

    public void setGuestShip(String shipName, int x, int y, boolean vertical) {
        setShip(false, shipName, x, y, vertical);
    }

    private void setShip(Boolean host, String shipName, int x, int y, boolean vertical) {
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

    private boolean tryParseInt(String value)
    {
        try
        {
            Integer.parseInt(value);
            return true;
        } catch(NumberFormatException nfe)
        {
            return false;
        }
    }

    private class Ship {
        private String name;
        private int x;
        private int y;
        private int length;
        private int[] health;
        private boolean vertical;

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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getLength() {
            return length;
        }

        public void setLength(int length) {
            this.length = length;
        }

        public int[] getHealth() {
            return health;
        }

        public void setHealth(int[] health) {
            this.health = health;
        }

        public boolean isVertical() {
            return vertical;
        }

        public void setVertical(boolean vertical) {
            this.vertical = vertical;
        }
    }
}
