package main.frameworks;

import main.UserDatabase;
import main.entities.User;

import java.util.HashMap;

public class TempUserDatabase  implements UserDatabase {

    private final HashMap<String, User> users = new HashMap<>();

    public TempUserDatabase() {
        // Preload users (plain passwords for simplicity)
        users.put("admin", new User("temp","admin", "adminpass"));
    }
    public User getUserByUsername(String username) {
        return users.get(username);
    }
}
