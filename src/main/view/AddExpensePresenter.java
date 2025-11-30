package main.view;

import main.use_case.AddExpenseOutputBoundary;
import main.use_case.AddExpenseOutputData;

public class AddExpensePresenter implements AddExpenseOutputBoundary {
    private final ExpenseDesc view;

    public AddExpensePresenter(ExpenseDesc view) {
        this.view = view;
    }

    @Override
    public void prepareSuccessView(AddExpenseOutputData outputData) {
        view.showSuccess(outputData.getMessage());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        view.showError(errorMessage);
    }
}