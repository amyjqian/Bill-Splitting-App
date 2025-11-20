package main.usecase;

import main.entities.User;

import java.util.ArrayList;

public class AddExpenseInputData {
    private String expense_name;
    private String description;
    private float amount;
    private String category;
    private ArrayList<User> participants;

    public AddExpenseInputData(String expense_name,
                               String description,
                               float amount,
                               String category,
                               ArrayList<User> participants) {
        this.expense_name = expense_name;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.participants = participants;
    }

    public String getExpenseName() {return expense_name; }
    public String getDescription() {return description; }
    public float getAmount() {return amount; }
    public String getCategory() {return category; }
    public ArrayList<User> getParticipants() {return participants; }
}
