package interface_adapter.add_expense;

import use_case.add_expense.*;

public class AddExpenseController {
    private AddExpenseInputBoundary addExpenseInteractor;

    public AddExpenseController(AddExpenseInteractor interactor) {
    }

    public void AddExpenseController(AddExpenseInputBoundary addExpenseInteractor) {
        this.addExpenseInteractor = addExpenseInteractor;
    }

    public void execute(String expenseName,
                        String description,
                        float amount,
                        String category,
                        java.util.ArrayList<entities.User> participants,
                        Long groupId) {
        AddExpenseInputData inputData = new AddExpenseInputData(
                expenseName, description, amount, category, participants, groupId);
        addExpenseInteractor.execute(inputData);
    }
}