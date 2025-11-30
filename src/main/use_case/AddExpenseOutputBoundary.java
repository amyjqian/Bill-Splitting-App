package main.use_case;

public interface AddExpenseOutputBoundary {
    void prepareSuccessView(AddExpenseOutputData addExpenseOutputData);
    void prepareFailView(String errorMessage);
}
