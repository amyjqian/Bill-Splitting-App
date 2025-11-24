package main.controllers;

import main.use_case.login.LoginInputBoundary;

public class LoginController {

    private final LoginInputBoundary loginInput;

    public LoginController(LoginInputBoundary loginInput) {
        this.loginInput = loginInput;
    }

    public void login(String username, String password) {
        loginInput.login(username, password);
    }
}
