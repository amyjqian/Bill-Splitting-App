package use_case.login;

import view.LoginView;

public class LoginPresenter implements LoginOutputBoundary {

    private LoginView view;

    public void setView(LoginView view) {
        this.view = view;
    }

    @Override
    public void showSuccessMessage(String message) {
        view.showMessage(message);
    }

    @Override
    public void showFailMessage(String message) {
        view.showMessage(message);
    }
}
