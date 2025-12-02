package main;

import entities.User;

public interface UserDatabase {
    User getUserByUsername(String username);
}
