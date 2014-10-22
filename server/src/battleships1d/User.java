package battleships1d;

import java.util.Random;

/**
 * Created by Thomas on 21/10/2014.
 */
public class User {

    private Connection connection;

    public void handleCommand(Command cmd) {
        if (cmd.getCommand().equals("LoginGuest")) {
            connection.writeLine("LoginGuest::" + UserManager.generateGuestUsername());
        } else if (cmd.getCommand().equals("LoginUser")) {
            String userName = cmd.getParameters()[0];
            String password = cmd.getParameters()[1];

            if (UserManager.userExists(userName)) {

            } else {
                connection.writeLine("LoginUser::Error::Username");
                return;
            }
        }
    }



    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}
