package interface_adapter;

import entities.User;
import use_case.AddExpenseInputBoundary;
import use_case.AddExpenseInputData;

public class AddExpenseController {
    private final AddExpenseInputBoundary addExpenseInteractor;

    public AddExpenseController(AddExpenseInputBoundary addExpenseInteractor) {
        this.addExpenseInteractor = addExpenseInteractor;
    }

    public void execute(String expenseName,
                        String description,
                        float amount,
                        String category,
                        java.util.ArrayList<User> participants,
                        Long groupId) {
        AddExpenseInputData inputData = new AddExpenseInputData(
                expenseName, description, amount, category, participants, groupId);
        addExpenseInteractor.execute(inputData);
    }
}