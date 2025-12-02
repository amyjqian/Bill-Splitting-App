package frameworks;

import use_case.login.LoginUserDataAccessInterface;
import main.UserDatabase;
import entities.User;

public class UserDataAccessObject extends LoginUserDataAccessInterface {

    public UserDataAccessObject(UserDatabase database) {
        super(database);
    }

    @Override
    public User getUserByUsername(String username) {
        return super.getUserByUsername(username);
    }
}
