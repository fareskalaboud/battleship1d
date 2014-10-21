package battleships1d;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Thomas on 21/10/2014.
 */
public class Connection implements Runnable {

    private boolean running = false;
    private Socket socket;

    public Connection(Socket socket) {
        running = true;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream output = socket.getOutputStream();

            while (running) {
                System.out.println(input.readLine());
            }

            input.close();
            output.close();
        } catch (Exception e) {

        }
    }
}
