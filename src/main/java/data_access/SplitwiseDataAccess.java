package data_access;

import entities.Expense;
import entities.Group;
import entities.User;
import use_case.add_expense.AddExpenseDataAccessInterface;
import api.SplitwiseAPI;
import api.SplitwiseAPIImpl;
import use_case.create_group.CreateGroupDataAccessInterface;
import use_case.join_group.JoinGroupDataAccessInterface;

import java.util.List;

public class SplitwiseDataAccess implements AddExpenseDataAccessInterface,
        CreateGroupDataAccessInterface, JoinGroupDataAccessInterface {
    private final SplitwiseAPI splitwiseAPI;

    public SplitwiseDataAccess() {
        this.splitwiseAPI = new SplitwiseAPIImpl();
    }

    public SplitwiseDataAccess(SplitwiseAPI splitwiseAPI) {
        this.splitwiseAPI = splitwiseAPI;
    }

    @Override
    public Expense save(Expense expense, Long groupId) {
        try {
            // Use the Splitwise API to create the expense
            return splitwiseAPI.createExpense(expense, groupId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save expense to Splitwise: " + e.getMessage(), e);
        }
    }

    @Override
    public User getCurrentUser() {
        try {
            return splitwiseAPI.getCurrentUser();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get current user from Splitwise: " + e.getMessage(), e);
        }
    }

    @Override
    public Group getGroup(Long groupId) {
        try {
            return splitwiseAPI.getGroup(groupId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get group from Splitwise: " + e.getMessage(), e);
        }
    }
    public List<Group> getGroups() {
        try {
            return splitwiseAPI.getGroups();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get groups: " + e.getMessage(), e);
        }
    }

    @Override
    public Group createGroup(String name) {
        try{
            return splitwiseAPI.createGroup(name);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create group"  + e.getMessage(), e);
        }
    }

    @Override
    public void addUserToGroup(int groupID, int userID) {
        try{
            splitwiseAPI.addUserToGroup(groupID, userID);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add user"  + e.getMessage(), e);
        }
    }

    //initial hashmap implementation
//public class SplitwiseDataAccess implements CreateGroupDataAccessInterface, JoinGroupDataAccessInterface {
//    private final HashMap<Integer, Group> groupMap; //private hashmap to test DAO
//    private final HashMap<Integer, User> userMap; //private hashmap of users
//
//    public SplitwiseDataAccess(){
//        groupMap = new HashMap<>();
//        userMap = new HashMap<>();
//
//        //populating group and user maps with default values for now
//        userMap.put(75, new User(75, "Katie", "Fruitman", "me@yahoo.ca", "hello"));
//        groupMap.put(20, new Group("Test group"));
//    }
//
//
//    public Group createGroup(String name) {
//        //add creator to userbase
//        //this.createGroup(name, groupType, groupCreator);
//        Group newGroup = new Group(name);
//        groupMap.put(newGroup.getGroupId(), newGroup);
//        //userMap.put(groupCreator.getId(), groupCreator);
//        //newGroup.addMember(groupCreator);
//        return newGroup;
//    }
//
//    public HashMap<Integer, Group> getGroupMap() {
//        return groupMap;
//    }
//
//    public User createUser(int id, String first_name, String last_name, String email, String password ){
//        User newUser = new User(id, first_name, last_name, email, password);
//        userMap.put(newUser.getId(), newUser);
//        return newUser;
//    }
//
//    @Override
//    public Group getGroup(int groupID) {
//        return groupMap.get(groupID);
//    }
//
//    @Override
//    public void delete(String groupID) {
//        groupMap.remove(groupID);
//    }
//
//    @Override
//    public void addUserToGroup(int groupID, int userID) {
//        Group group = groupMap.get(groupID);
//        User newUser = userMap.get(userID);
//        userMap.put(userID, newUser);
//        group.addMember(newUser);
//    }
//
//    @Override
//    public void removeUserFromGroup(int groupID, int userID) {
//        Group group = groupMap.get(groupID);
//        User newUser = userMap.get(userID);
//        group.removeMember(newUser);
//    }

}