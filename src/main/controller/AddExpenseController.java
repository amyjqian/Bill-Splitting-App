package main.controller;

import main.entities.User;
import main.usecase.AddExpenseInputBoundary;
import main.usecase.AddExpenseInputData;

import java.util.ArrayList;

public class AddExpenseController {
    private final AddExpenseInputBoundary addExpenseInteractor;

    public AddExpenseController(AddExpenseInputBoundary addExpenseInteractor) {
        this.addExpenseInteractor = addExpenseInteractor;
    }

    public void execute(String expenseName, String description, float amount,
                        String category, ArrayList<User> participants) {
        AddExpenseInputData inputData = new AddExpenseInputData(
                expenseName, description, amount, category, participants
        );
        addExpenseInteractor.execute(inputData);
    }
}