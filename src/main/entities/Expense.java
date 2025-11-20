package main.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String expenseName;
    private double amount;
    private String description;
    private List<User> participants;
    private String category; // stores what category the expense should be
    private User paiedBy;
    private boolean settled;
    private String date;

    public Expense(String expenseName,
                   String description,
                   double amount,
                   String category,
                   ArrayList<User> participants,
                   User paiedBy) {
        this.expenseName = expenseName;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.participants = participants;
        this.paiedBy = paiedBy;
        this.settled = false;
        this.date = String.valueOf(LocalDate.now());
    }

    // Getters and setters
    public String getExpenseName() { return expenseName; }
    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getCategory() { return category; }
    public List<User> getParticipants() { return participants; }
    public User getPaiedBy() { return paiedBy; }
    public boolean getSettled() { return settled; }
    public String getDate() { return date; }
    public void setSettled(boolean newSettled) {this.settled = newSettled; }

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
