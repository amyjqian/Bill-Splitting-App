package main.controller;

import main.use_case.*;

public class AddExpenseController {
    private final AddExpenseInputBoundary addExpenseInteractor;

    public AddExpenseController(AddExpenseInputBoundary addExpenseInteractor) {
        this.addExpenseInteractor = addExpenseInteractor;
    }

    public void execute(String expenseName,
                        String description,
                        float amount,
                        String category,
                        java.util.ArrayList<main.entities.User> participants,
                        Long groupId) {
        AddExpenseInputData inputData = new AddExpenseInputData(
                expenseName, description, amount, category, participants, groupId);
        addExpenseInteractor.execute(inputData);
    }
}