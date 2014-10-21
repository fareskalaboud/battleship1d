package battleships1d;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Random;

/**
 * Created by Thomas on 21/10/2014.
 */
public class UserManager {

    private static String getUserPath() {
        try {
            return URLDecoder.decode(UserManager.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8").substring(1) + "users/";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    //Load all users into a list here (?) instead of having to check flat files constantly

    public static boolean checkPassword(String username, String password) {
        return false;
    }

    public static boolean userExists(String username) {
        File f = new File(getUserPath() + username);
        return f.exists();
    }

    public static String generateGuestUsername() {
        Random random = new Random();
        return "guest-" + (random.nextInt(8999) + 1000);
    }
}
