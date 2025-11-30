package entities;

import java.util.*;

public class Group {
    // Core properties - parameters we need to send to the API
    private final String groupName;
    // will need to access their properties to create a group


    //properties the API will generate
    private int groupId;
    private String inviteLink = "join123"; //default invite link for now
    private final List<User> members;
    private final List<Expense> originalDebts;
    private final List<Expense> simplifiedDebts;

    //private Map<User, Double> balances; // Track who owes whom


    public Group(String groupName) {
        this.groupName = groupName;
        this.groupId = 20; //this is a placeholder -> will be redundant eventually

        this.members = new ArrayList<>();
        this.originalDebts = new ArrayList<>();
        this.simplifiedDebts = new ArrayList<>();
    }

    //getters for the above

    public String getGroupName() {
        return groupName;
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

    public String getInviteLink() {
        return inviteLink;
    }

    public void addMember(User newMember) {
        members.add(newMember);
    }

    public void removeMember(User newMember) {
        members.remove(newMember);
    }
}