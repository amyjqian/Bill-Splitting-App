package main.use_case.login;

import main.use_case.login.LoginUserDataAccessInterface;
import main.entities.User;

public class LoginInteractor implements LoginInputBoundary {

    private final LoginUserDataAccessInterface dataAccess;
    private final LoginOutputBoundary presenter;

    public LoginInteractor(LoginUserDataAccessInterface dataAccess,
                           LoginOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void login(String username, String password) {

        User user = dataAccess.getUserByUsername(username);

        if (user == null) {
            presenter.showFailMessage("User not found.");
            return;
        }

        if (!user.getPassword().equals(password)) {
            presenter.showFailMessage("Incorrect password.");
            return;
        }

        presenter.showSuccessMessage("Welcome " + username + "!");
    }
}
