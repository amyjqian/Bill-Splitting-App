package main.entities;

import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String expenseName;
    private double amount;
    private String description;
    private List<User> participants;
    private String category; // stores what category the expense should be

    public Expense(String expenseName,
                   String description,
                   double amount,
                   String category,
                   ArrayList<User> participants) {
        this.expenseName = expenseName;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.participants = participants;
    }

    // Getters and setters
    public String getExpenseName() { return expenseName; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public List<User> getParticipants() { return participants; }

    public void addParticipant(User user) {
        if (!participants.contains(user)) {
            participants.add(user);
        }
    }

    public double calculateEqualShare() {
        if (participants.isEmpty()) return 0;
        return amount / participants.size();
    }
}
