package main;

import main.entities.User;

public interface UserDatabase {
    User getUserByUsername(String username);
}
