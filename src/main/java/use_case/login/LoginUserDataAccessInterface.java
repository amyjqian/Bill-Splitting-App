package use_case.login;

import entities.User;
import main.UserDatabase;

public class LoginUserDataAccessInterface {

    private UserDatabase database;

    public void UserDataAccessInterface(UserDatabase database) {
        this.database = database;
    }

    public LoginUserDataAccessInterface(UserDatabase database) {
        this.database = database;
    }

    public User getUserByUsername(String username) {
        return database.getUserByUsername(username);
    }
}
