package use_case;

import view.AddExpenseFrame;

public class AddExpensePresenter implements AddExpenseOutputBoundary {
    private final AddExpenseFrame view;

    public AddExpensePresenter(AddExpenseFrame view) {
        this.view = view;
    }

    @Override
    public void prepareSuccessView(AddExpenseOutputData outputData) {
        String message = String.format("Expense '%s' added successfully!",
                outputData.getExpenseName());
        view.showSuccess(message);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        view.showError(errorMessage);
    }
}