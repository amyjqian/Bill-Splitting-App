package main.entities;

import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String id;
    private double amount;
    private String description;
    private User paidBy;
    private List<User> participants;

    public Expense(String id, double amount, String description, User paidBy) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.paidBy = paidBy;
        this.participants = new ArrayList<>();
    }

    // Getters and setters
    public String getId() { return id; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public User getPaidBy() { return paidBy; }
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
