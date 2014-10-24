package battleships1d;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Thomas on 21/10/2014.
 */
public class UserManager {

    private static HashMap<String, User> users;

    private static String getUserPath() {
        try {
            return URLDecoder.decode(UserManager.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8").substring(1) + "users/";
        } catch (UnsupportedEncodingException e) {
            return "";
        }
    }

    private static void loadUsers() throws IOException {
        users = new HashMap<String, User>();
        File[] userFiles = new File(getUserPath()).listFiles();
        for (File f : userFiles) {
            BufferedReader reader = new BufferedReader(new FileReader(f.getPath()));
            User user = new User();
            user.setUsername(reader.readLine());
            user.setPassword(reader.readLine());
            users.put(user.getUsername(), user);
        }
        System.out.println(users.size() + " users are loaded");
    }

    public static void init() {
        try {
            loadUsers();
        } catch (IOException e) { }
    }

    public static Boolean createUser(String username, String password) {
        if (userExists(username)) return false;
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        users.put(username, user);

        try {
            File file = new File(getUserPath() + username + ".txt");
            file.createNewFile();
            BufferedWriter writer = new BufferedWriter(new FileWriter(file.getPath()));
            writer.write(username + "\r\n" + password);
            writer.close();
        } catch (IOException e) { }
        return true;
    }

    public static boolean checkPassword(String username, String password) {
        if (users.containsKey(username)) return users.get(username).getPassword().equals(password);
        else return false;
    }

    public static boolean userExists(String username) {
        return users.containsKey(username);
    }

    public static String generateGuestUsername() {
        Random random = new Random();
        return "guest-" + (random.nextInt(8999) + 1000);
    }

    public static User getUserObject(String username) {
        if (!userExists(username)) return null;
        return users.get(username);
    }
}
