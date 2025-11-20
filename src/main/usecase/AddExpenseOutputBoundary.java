package main.usecase;

public interface AddExpenseOutputBoundary {
    void prepareSuccessView(AddExpenseOutputData addExpenseOutputData);
    void prepareFailView(String errorMessage);
}
