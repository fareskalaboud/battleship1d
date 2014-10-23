package battleships1d;

import java.util.Random;

/**
 * Created by Thomas on 21/10/2014.
 */
public class User {

    private Connection connection;
    private String username;
    private String password;

    public void handleCommand(Command cmd) {
        if (cmd.getCommand().equals("Login")) {
            if (cmd.getParameters()[0].equals("Guest")) {
                connection.writeLine("Login::Guest::" + UserManager.generateGuestUsername());
            } else if (cmd.getParameters()[0].equals("User")) {
                String userName = cmd.getParameters()[1];
                String password = cmd.getParameters()[2];

                if (UserManager.userExists(userName)) {
                    if (UserManager.checkPassword(userName, password)) {
                        connection.writeLine("Login::User::Successful::" + userName);
                    } else {
                        connection.writeLine("Login::User::Error::Password");
                        return;
                    }
                } else {
                    connection.writeLine("Login::User::Error::Username");
                    return;
                }
            } else if (cmd.getParameters()[0].equals("Create")) {
                String userName = cmd.getParameters()[1];
                String password = cmd.getParameters()[2];

                if (UserManager.userExists(userName)) {
                    connection.writeLine("Login::Create::Error::Username");
                    return;
                } else{
                    if (UserManager.createUser(userName, password)) {
                        connection.writeLine("Login::Create::Successful::" + userName);
                    } else {
                        connection.writeLine("Login::Create::Error");
                    }
                }
            }
        }
    }

    public Boolean hasConnection() {
        return connection == null;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
