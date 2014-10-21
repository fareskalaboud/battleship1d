package battleships1d;

import java.util.Random;

/**
 * Created by Thomas on 21/10/2014.
 */
public class User {

    private Connection connection;

    public void handleCommand(Command cmd) {
        if (cmd.getCommand().equals("LoginGuest")) {
            connection.writeLine("LoginGuest::" + User.createGuest());
        }
    }

    public static boolean userExists(String username) {
        return false;
    }

    public static String createGuest() {
        Random random = new Random();
        return "guest-" + (random.nextInt(8999) + 1000);
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
