package battleships1d;

import java.util.Random;

/**
 * Created by Thomas on 21/10/2014.
 */
public class User {

    public static boolean userExists(String username) {
        return false;
    }

    public static String createGuest() {
        Random random = new Random();
        return "guest-" + (random.nextInt(8999) + 1000);
    }


}
