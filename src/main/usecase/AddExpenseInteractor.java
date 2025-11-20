package main.usecase;

import main.entities.Expense;
import main.entities.ExpenseFactory;

public class AddExpenseInteractor implements AddExpenseInputBoundary {
    private final AddExpenseDataAccessInterface expenseDataAccessObject;
    private final AddExpenseOutputBoundary expensePresenter;
    private final ExpenseFactory expenseFactory;

    public AddExpenseInteractor(AddExpenseDataAccessInterface expenseDataAccessInterface,
                                AddExpenseOutputBoundary addExpenseOutputBoundary,
                                ExpenseFactory expenseFactory) {
        this.expenseDataAccessObject = expenseDataAccessInterface;
        this.expensePresenter = addExpenseOutputBoundary;
        this.expenseFactory = expenseFactory;
    }

    @Override
    public void execute(AddExpenseInputData addExpenseInputData) {
        if (addExpenseInputData.getAmount() <= 0) {
            expensePresenter.prepareFailView("Amount must be positive");
        } else if (addExpenseInputData.getParticipants().isEmpty()) {
            expensePresenter.prepareFailView("At least one participant is required");
        } else {
            final Expense expense = expenseFactory.create(
                    addExpenseInputData.getExpenseName(),
                    addExpenseInputData.getDescription(),
                    addExpenseInputData.getAmount(),
                    addExpenseInputData.getCategory(),
                    addExpenseInputData.getParticipants(),
                    addExpenseInputData.getPaidBy());

            expenseDataAccessObject.save(expense);

            final AddExpenseOutputData addExpenseOutputData = new AddExpenseOutputData(
                    expense.getExpenseName(), "Expense added successfully");
            expensePresenter.prepareSuccessView(addExpenseOutputData);
        }
    }
}
