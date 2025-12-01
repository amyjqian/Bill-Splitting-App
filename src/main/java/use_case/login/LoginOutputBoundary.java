package use_case.login;

public interface LoginOutputBoundary {
    void showSuccessMessage(String message);
    void showFailMessage(String errorMessage);
}
