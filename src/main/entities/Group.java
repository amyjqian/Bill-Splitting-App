package main.entities;

import java.util.*;

public class Group {
    // Core properties
    private String groupId;
    private String groupName;

    // Members and relationships
    private List<User> members;
    private List<Expense> expenses;
    private Map<User, Double> balances; // Track who owes whom


    public Group(String groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<>();
        this.balances = new HashMap<>();
    }

    public List<Expense> getExpenses() {
        return expenses;
    }
}