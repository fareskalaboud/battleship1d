package battleships1d;

import java.io.*;
import java.net.Socket;

/**
 * Created by Thomas on 21/10/2014.
 */
public class Connection implements Runnable {

    private boolean running = false;
    private Socket socket;
    private BufferedWriter bufferedWriter;
    private BufferedReader bufferedReader;

    private User user;

    public Connection(Socket socket) {
        running = true;
        this.socket = socket;

        user = new User();
        user.setConnection(this);

        try {
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e) {
            running = false;
        }
    }

    public Boolean isConnected() {
        return socket.isConnected();
    }

    public User getUser() {
        return this.user;
    }

    public void writeLine(String message) {
        try {
            bufferedWriter.write(message + "\r\n");
            bufferedWriter.flush();
        } catch (IOException e) { }
    }

    private void disconnect() {
        try {
            running = false;
            socket.close();
            bufferedReader.close();
            bufferedWriter.close();
        } catch (IOException e) { }
    }

    @Override
    public void run() {
        try {
            while (running) {
                String line = bufferedReader.readLine();
                if (line == null) {
                    running = false;
                    break;
                }

                Command cmd = Command.parseCommand(line);
                if (cmd == null) {
                    //Discard if command is invalid
                    System.out.println("Invalid command: " + line);
                    continue;
                }
                else System.out.println("Command: " + cmd.getRawCommand());

               handleCommand(cmd);
            }
        } catch (Exception e) { System.out.println(e); }

        System.out.println("Client disconnected");
        disconnect();
    }

    public void handleCommand(Command cmd) {
        if (cmd.getCommand().equals("Login")) {
            if (cmd.getParameters().length == 1) return;
            if (cmd.getParameters()[0].equals("Guest")) {
                String guestUsername = UserManager.generateGuestUsername();
                user.setUsername(guestUsername);
                user.setPassword("");
                user.setLoggedIn(true);
                writeLine("Login::Guest::" + guestUsername);
            } else if (cmd.getParameters()[0].equals("User")) {
                if (cmd.getParameters().length != 3) return;
                String userName = cmd.getParameters()[1];
                String password = cmd.getParameters()[2];

                if (UserManager.userExists(userName)) {
                    if (UserManager.checkPassword(userName, password)) {
                        writeLine("Login::User::Successful::" + userName);
                        user = UserManager.getUserObject(userName);
                        user.setConnection(this);
                        user.setLoggedIn(true);
                    } else {
                        writeLine("Login::User::Error::Password");
                        return;
                    }
                } else {
                    writeLine("Login::User::Error::Username");
                    return;
                }
            } else if (cmd.getParameters()[0].equals("Create")) {
                if (cmd.getParameters().length != 3) return;
                String userName = cmd.getParameters()[1];
                String password = cmd.getParameters()[2];

                if (UserManager.userExists(userName)) {
                    writeLine("Login::Create::Error::Username");
                    return;
                } else{
                    if (UserManager.createUser(userName, password)) {
                        writeLine("Login::Create::Successful::" + userName);
                        user = UserManager.getUserObject(userName);
                        user.setConnection(this);
                        user.setLoggedIn(true);
                    } else {
                        writeLine("Login::Create::Error");
                    }
                }
            }
        } else if (cmd.getCommand().equals("Room")) {
            if (!checkLoggedIn()) return;
            RoomManager.handleCommand(cmd, this);
        } else if (cmd.getCommand().equals("Game")) {
            if (!checkLoggedIn()) return;
            Room room = RoomManager.getRoomByUser(user.getUsername());
            room.getGame().handleCommand(cmd, this);
        }
    }

    private boolean checkLoggedIn() {
        if (!user.getLoggedIn()) {
            writeLine("Login::User::Error::NotLoggedIn");
        }
        return user.getLoggedIn();
    }
}
