package main.entities;

import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String id;
    private double amount;
    private String description;
    private main.entities.User paidBy;
    private List<main.entities.User> participants;
    private String date;

    public Expense(String id, double amount, String description, main.entities.User paidBy, String date) {
        this.id = id;
        this.amount = amount;
        this.description = description;
        this.paidBy = paidBy;
        this.participants = new ArrayList<>();
        this.date = date;
    }

    // Getters and setters
    public String getId() { return id; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public main.entities.User getPaidBy() { return paidBy; }
    public List<main.entities.User> getParticipants() { return participants; }
    public String getDate() { return date; }

    public void addParticipant(main.entities.User user) {
        if (!participants.contains(user)) {
            participants.add(user);
        }
    }

    public double calculateEqualShare() {
        if (participants.isEmpty()) return 0;
        return amount / participants.size();
    }
}
