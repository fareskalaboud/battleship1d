package battleships1d;

import java.util.Random;

/**
 * Created by Thomas on 21/10/2014.
 */
public class User {

    private Connection connection;
    private String username;
    private String password;
    private boolean loggedIn;

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

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
