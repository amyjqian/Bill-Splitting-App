package use_case.add_expense;

public interface AddExpenseOutputBoundary {
    void prepareSuccessView(AddExpenseOutputData addExpenseOutputData);
    void prepareFailView(String errorMessage);
}