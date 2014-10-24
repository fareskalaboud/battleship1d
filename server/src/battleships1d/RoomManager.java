package battleships1d;

import java.util.HashMap;
import java.util.Random;

/**
 * Created by Thomas on 24/10/2014.
 */
public class RoomManager {

    private static HashMap<String, Room> rooms = new HashMap<String, Room>();

    public static boolean roomExists(String roomID) {
        return rooms.containsKey(roomID);
    }

    public static boolean userInRoom(String username) {
        for(Room r : rooms.values()) {
            if (r.getHost().getUsername().equals(username)) return true;
            if (r.getGuest() != null && r.getGuest().getUsername().equals(username)) return true;
        }
        return false;
    }

    public static String generateRoomName() {
        Random random = new Random();
        int num = -1;
        while (num == -1 && !roomExists("room-" + num)) {
            num = random.nextInt(8999) + 1000;
        }
        return "room-" + num;
    }

    public static void handleCommand(Command cmd, Connection connection) {
        if (cmd.getParameters()[0].equals("List")) {
            String returnString = "Room::" + rooms.size();
            for(Room r : rooms.values()) {
                returnString += "::" + r.getRoomID() + "::" + r.getHost().getUsername() + "::" + r.getPassword();
            }
            connection.writeLine(returnString);
        } else if (cmd.getParameters()[0].equals("Create")) {
            if (userInRoom(connection.getUser().getUsername())) {
                connection.writeLine("Room::Create::Error::UserInARoom");
                return;
            }
            Room newRoom;
            if (cmd.getParameters().length == 1) newRoom = hostRoom(connection.getUser(), "");
            else newRoom = hostRoom(connection.getUser(), cmd.getParameters()[1]);
            connection.writeLine("Room::Create::" + newRoom.getRoomID());
        } else if (cmd.getParameters()[0].equals("Close")) {
            if (!roomExists(cmd.getParameters()[1])) return;
            if(rooms.get(cmd.getParameters()[1]).getGuest() != null) {
                rooms.get(cmd.getParameters()[1]).getGuest().getConnection().writeLine("Room::Close::Success");
            }
            rooms.remove(cmd.getParameters()[1]);
            connection.writeLine("Room::Close::Success");
        } else if (cmd.getParameters()[0].equals("Join")) {
            String roomID = cmd.getParameters()[1];
            if (!roomExists(roomID)) return;
            Room room = rooms.get(roomID);
            if (room.getGuest() != null) {
                connection.writeLine("Room::Join::Error::Full");
                return;
            }
            room.setGuest(connection.getUser());
            room.getHost().getConnection().writeLine("Room::PlayedJoined::" + connection.getUser().getUsername());
            connection.writeLine("Room::Join::Success");
            connection.writeLine("Room::PlayedJoined::" + room.getHost().getUsername());
        }
    }

    public static void handleRoomCommand(Command cmd, User user) {
        String roomID = cmd.getParameters()[0];
        if (!roomExists(roomID)) return;
        rooms.get(roomID).handleCommand(cmd, user);
    }

    public static Room hostRoom(User host, String password) {
        Room room = new Room(password, host);
        rooms.put(room.getRoomID(), room);
        return room;
    }
}