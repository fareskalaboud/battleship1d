package battleships1d;

/**
 * Created by Thomas on 24/10/2014.
 */
public class Room {
    private String roomID;
    private boolean privateRoom;
    private String password;
    private User host;
    private User guest;

    public Room(User host) {
        this("", host);
    }

    public Room(String password, User host) {
        if (password.equals("")) this.privateRoom = false;
        else this.privateRoom = true;
        this.password = password;
        this.roomID = RoomManager.generateRoomName();
        this.host = host;
    }

    public void handleCommand(Command cmd, User user) {

    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public boolean isPrivateRoom() {
        return privateRoom;
    }

    public void setPrivateRoom(boolean privateRoom) {
        this.privateRoom = privateRoom;
    }

    public User getHost() {
        return host;
    }

    public void setHost(User host) {
        this.host = host;
    }

    public User getGuest() {
        return guest;
    }

    public void setGuest(User guest) {
        this.guest = guest;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
