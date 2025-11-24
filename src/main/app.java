package main;

import main.controllers.LoginController;
import main.use_case.login.LoginUserDataAccessInterface;
import main.frameworks.TempUserDatabase;
import main.presenter.LoginPresenter;
import main.use_case.login.LoginInteractor;
import main.view.LoginView;

import javax.swing.*;

public class app {
    public static void main(String[] args) {

        TempUserDatabase db = new TempUserDatabase();
        LoginUserDataAccessInterface dataAccess = new LoginUserDataAccessInterface(db);

        LoginPresenter presenter = new LoginPresenter();
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        LoginController controller = new LoginController(interactor);

        LoginView view = new LoginView(controller);
        presenter.setView(view);

        JFrame frame = new JFrame("Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
