package battleships1d;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Thomas on 4/11/2014.
 */
public class Server implements Runnable {

    // Server Variables
    private static BufferedWriter out;
    private static BufferedReader in;
    private static Socket socket;
    private static Thread thread;
    private static boolean running = false;

    /** Connect to the server.
     *
     * @return a boolean value: was the connection successful?
     * @author Alexander Hanbury-Botherway
     */
    public static boolean connectToServer() {
//        String hostName = "localhost";
//        int portNumber = 8000;
//        try {
//            socket = new Socket(hostName, portNumber);
//            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//        } catch (Exception e) {
//            System.out.println("Error connecting to server: " + e);
//            return false;
//        }
//
//        running = true;
//        thread = new Thread(new Server());
//        thread.start();
        return true;
    }

    /**
     * 
     * @param line
     * @author Tom
     */
    public static void writeLineToServer(String line) {
        try {
            out.write(line + "\r\n");
            out.flush();
        } catch (Exception e) { }
    }

    private static HashMap<ArrayList<String>, RequestFunction> commandRegisters = new HashMap<ArrayList<String>, RequestFunction>();

    public static Boolean registerCommands(ArrayList<String> commands, RequestFunction rf) {
        if (commandRegisters.containsKey(commands)) return false;
        commandRegisters.put(commands, rf);
        return true;
    }

    private static void handleCommand(String command) {
        Boolean done = false;
        for (ArrayList<String> list : commandRegisters.keySet()) {
            for (String s : list) {
                if (command.startsWith(s)) {
                    commandRegisters.get(list).Response(command);
                    commandRegisters.remove(list);
                    done = true;
                    break;
                }
            }
            if (done) break;
        }
    }
    

    @Override
    public void run() {
        try {
            while (running) {
                String line = in.readLine();
                if (line == null) {
                    running = false;
                    break;
                }
                Server.handleCommand(line);
            }
        } catch (Exception e) { }
    }

    public interface RequestFunction {
        void Response(String command);
    }

    public static class RequestVariables {
        private String command;
        private Boolean continueThread = false;

        public String getCommand() {
            return command;
        }

        public void setCommand(String command) {
            this.command = command;
        }

        public Boolean getContinueThread() {
            return continueThread;
        }

        public void setContinueThread(Boolean continueThread) {
            this.continueThread = continueThread;
        }
    }
}
