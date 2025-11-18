package entities;

import java.util.*;

public class Group {
    // Core properties
    private String groupId;
    private String groupName;

    // Members and relationships
    private ArrayList<User> members;
    private ArrayList<Expense> expenses;
    private Map<User, Double> balances; // Track who owes whom


    public Group(String groupId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.members = new ArrayList<>();
        this.expenses = new ArrayList<Expense>();
        this.balances = new HashMap<>();


    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public ArrayList<Expense> getExpenses(){
        return this.expenses;
    }



}