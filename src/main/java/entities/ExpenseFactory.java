package entities;

import java.util.ArrayList;

public class ExpenseFactory {
    public Expense create(String expenseName,
                          String description,
                          double amount,
                          String category,
                          ArrayList<User> participants,
                          User paidBy) {
        return new Expense(expenseName, description, amount, category, participants, paidBy);
    }
}
