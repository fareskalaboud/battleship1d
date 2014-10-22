package battleships1d;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Thomas on 21/10/2014.
 */
public class ServerManager implements Runnable {

    private static boolean running = false;
    private static int port = 8000;
    private static Thread thread;
    private static ServerSocket serverSocket;

    public static boolean isRunning() {
        return running;
    }

    public static void start() {
        running = true;
        thread = new Thread(new ServerManager());
        thread.start();
    }

    public static void stop() {
        running = false;
        thread.interrupt();
    }

    @Override
    public void run() {
        System.out.println("Starting server...");
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server running on port " + port);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (running) {
            try {
                Socket client = serverSocket.accept();
                System.out.println("Client connected on port " + client.getPort());
                new Thread(new Connection(client)).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
