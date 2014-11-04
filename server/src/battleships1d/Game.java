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

    private Boolean inGame = false;
    private Boolean hostReady = false;
    private Boolean guestReady = false;

    public Game(Room room) {
        this.room = room;
    }

    public void handleCommand(Command cmd, Connection connection) {
        if (cmd.getParameters()[0].equals("Setup")) {
            if (cmd.getParameters()[1].equals("Ship")) {
                String shipName = cmd.getParameters()[2];
                int x;
                int y;
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
                    setHostShip(shipName, x, y);
                    connection.writeLine("Game::Setup::Ship::Success");
                } else {
                    //Guest
                    setGuestShip(shipName, x, y);
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
                    if (guestsShips.size() != 5) {
                        connection.writeLine("Game::Setup::Ready::Error::InsufficientShips");
                        return;
                    }
                    else {
                        connection.writeLine("Game::Setup::Ready::Success");
                        hostReady = true;
                    }
                }

                if (hostReady && guestReady) {
                    //Start Game
                    int starting = new Random().nextInt(2);
                    if (starting == 0) {
                        room.getHost().getConnection().writeLine("Game::Turn");
                    } else {
                        room.getGuest().getConnection().writeLine("Game::Turn");
                    }
                }
            }
        } else if (cmd.getParameters()[0].equals("Fire")) {

        }
    }

    public void setHostShip(String shipName, int x, int y) {
        setShip(true, shipName, x, y);
    }

    public void setGuestShip(String shipName, int x, int y) {
        setShip(false, shipName, x, y);
    }

    private void setShip(Boolean host, String shipName, int x, int y) {
        if (host) {
            if (hostsShips.containsKey(shipName)) {
                hostsShips.get(shipName).setX(x);
                hostsShips.get(shipName).setY(y);
            } else {
                Ship ship = new Ship(shipName, x, y);
                hostsShips.put(shipName, ship);
            }
        } else {
            if (guestsShips.containsKey(shipName)) {
                guestsShips.get(shipName).setX(x);
                guestsShips.get(shipName).setY(y);
            } else {
                Ship ship = new Ship(shipName, x, y);
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

        public Ship(String name, int x, int y) {
            this.name = name;
            this.x = x;
            this.y = y;
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
    }
}
