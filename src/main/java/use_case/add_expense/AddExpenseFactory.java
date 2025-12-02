package use_case.add_expense;

import interface_adapter.add_expense.AddExpenseController;
import data_access.SplitwiseDataAccess;
import entities.ExpenseFactory;
import view.AddExpenseFrame;

public class AddExpenseFactory {

    public static AddExpenseFrame createView() {
        AddExpenseFrame view = new AddExpenseFrame();
        configureView(view);
        return view;
    }

    private static void configureView(AddExpenseFrame view) {
        AddExpenseOutputBoundary presenter = new AddExpenseOutputBoundary() {
            @Override
            public void prepareSuccessView(AddExpenseOutputData outputData) {
                view.showSuccess(outputData.getMessage());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                view.showError(errorMessage);
            }
        };

        AddExpenseController controller = createController(presenter);
        view.setController(controller);
    }

    public static AddExpenseController createController(AddExpenseOutputBoundary presenter) {
        SplitwiseDataAccess dataAccess = new SplitwiseDataAccess();
        ExpenseFactory expenseFactory = new ExpenseFactory();

        AddExpenseInteractor interactor = new AddExpenseInteractor(
                dataAccess,
                presenter,
                expenseFactory
        );

        return new AddExpenseController(interactor);
    }
}