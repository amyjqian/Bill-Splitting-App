package entities;

import java.util.*;

public class Group {
    // Core properties - parameters we need to send to the API
    private final String groupName;
    private final String groupType;
    private final User groupCreator; // the user who created the group, we will need to access their properties to create a group


    //properties the API will generate
    private int groupId;
    private final List<User> members;
    private final List<Expense> originalDebts;
    private final List<Expense> simplifiedDebts;

    //private Map<User, Double> balances; // Track who owes whom


    public Group(String groupName, String groupType, User groupCreator) {
        this.groupName = groupName;
        this.groupType = groupType;
        this.groupCreator = groupCreator;

        this.members = new ArrayList<>();
        this.originalDebts = new ArrayList<>();
        this.simplifiedDebts = new ArrayList<>();
    }

    //getters for the above

    public String getGroupName() {
        return groupName;
    }
    public String getGroupType() {
        return groupType;
    }
    public User getGroupCreator() {
        return groupCreator;
    }

    public int getGroupId() {
        return groupId;
    }
    public List<User> getMembers() {
        return members;
    }
    public List<Expense> getOriginalDebts() {
        return this.originalDebts;
    }
    public List<Expense> getSimplifiedDebts() {
        return simplifiedDebts;
    }

}