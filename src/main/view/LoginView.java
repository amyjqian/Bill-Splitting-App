package main.view;

import main.controllers.LoginController;

import javax.swing.*;

public class LoginView extends JPanel {

    private final LoginController controller;

    private JTextField usernameField = new JTextField(10);
    private JPasswordField passwordField = new JPasswordField(10);
    private JButton loginButton = new JButton("Login");
    private JLabel messageLabel = new JLabel("");

    public LoginView(LoginController controller) {
        this.controller = controller;

        add(new JLabel("Username:"));
        add(usernameField);

        add(new JLabel("Password:"));
        add(passwordField);

        add(loginButton);
        add(messageLabel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            controller.login(username, password);
        });
    }

    public void showMessage(String msg) {
        messageLabel.setText(msg);
    }
}
