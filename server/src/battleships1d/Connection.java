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

                user.handleCommand(cmd);
            }
        } catch (Exception e) { }

        System.out.println("Client disconnected");
        disconnect();
    }
}
