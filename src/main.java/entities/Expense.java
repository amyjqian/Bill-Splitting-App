package entities;
<<<<<<<< HEAD:src/main/java/entities/Expense.java

========
//changed this entity
>>>>>>>> origin/viewHistory:src/main.java/entities/Expense.java
import java.util.ArrayList;
import java.util.List;

public class Expense {
    private String id;
    private String name;
    private double amount;
    private String description;
    private User paidBy;
    private List<User> participants;
    private boolean settled = false;
<<<<<<<< HEAD:src/main/java/entities/Expense.java

    public Expense(String id, String name, double amount, String description, User paidBy) {
========
    private String date; // creation date

    public Expense(String id, double amount, String description, User paidBy, String date) {
>>>>>>>> origin/viewHistory:src/main.java/entities/Expense.java
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
    public User getPaidBy() { return paidBy; }
    public List<User> getParticipants() { return participants; }
    public String getDate() { return date; }

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