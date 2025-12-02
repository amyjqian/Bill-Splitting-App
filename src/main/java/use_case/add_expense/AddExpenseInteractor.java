package use_case.add_expense;

import entities.Group;
import entities.Expense;
import entities.ExpenseFactory;
import entities.User;

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
        try {
            // Validate input in the interactor (use case layer)
            String validationError = validateInput(addExpenseInputData);
            if (validationError != null) {
                expensePresenter.prepareFailView(validationError);
                return;
            }

            // Get current user from Splitwise API to set as paidBy
            User currentUser = expenseDataAccessObject.getCurrentUser();
            addExpenseInputData.setPaidBy(currentUser);

            // Verify group exists
            Group group = expenseDataAccessObject.getGroup(addExpenseInputData.getGroupId());
            if (group == null) {
                expensePresenter.prepareFailView("Group not found");
                return;
            }

            // Create expense entity
            final Expense expense = expenseFactory.create(
                    addExpenseInputData.getExpenseName(),
                    addExpenseInputData.getDescription(),
                    addExpenseInputData.getAmount(),
                    addExpenseInputData.getCategory(),
                    addExpenseInputData.getParticipants(),
                    addExpenseInputData.getPaidBy());

            // Save to Splitwise API
            Expense createdExpense = expenseDataAccessObject.save(expense, addExpenseInputData.getGroupId());

            // Prepare success response
            final AddExpenseOutputData addExpenseOutputData = new AddExpenseOutputData(
                    createdExpense.getExpenseName(),
                    "Expense added successfully to Splitwise",
                    createdExpense.getAmount(),
                    createdExpense.getParticipants().size());

            expensePresenter.prepareSuccessView(addExpenseOutputData);

        } catch (Exception e) {
            expensePresenter.prepareFailView("Failed to add expense: " + e.getMessage());
        }
    }

    private String validateInput(AddExpenseInputData inputData) {
        if (inputData.getExpenseName() == null || inputData.getExpenseName().trim().isEmpty()) {
            return "Expense name cannot be empty";
        }
        if (inputData.getAmount() <= 0) {
            return "Amount must be positive";
        }
        if (inputData.getParticipants() == null || inputData.getParticipants().isEmpty()) {
            return "At least one participant is required";
        }
        if (inputData.getGroupId() == null) {
            return "Group ID is required";
        }
        return null;
    }
}