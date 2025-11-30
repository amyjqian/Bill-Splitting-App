package data_access;

import entities.Group;
import entities.User;
import use_case.create_group.CreateGroupDataAccessInterface;
import use_case.join_group.JoinGroupDataAccessInterface;

import java.util.*;

public class InMemoryGroupDataAccessObject implements CreateGroupDataAccessInterface, JoinGroupDataAccessInterface {
    private final HashMap<Integer, Group> groupMap; //private hashmap to test DAO
    private final HashMap<Integer, User> userMap; //private hashmap of users

    public InMemoryGroupDataAccessObject(){
        groupMap = new HashMap<>();
        userMap = new HashMap<>();
    }


    public Group createGroup(String name) {
        //add creator to userbase
        //this.createGroup(name, groupType, groupCreator);
        Group newGroup = new Group(name);
        groupMap.put(newGroup.getGroupId(), newGroup);
        //userMap.put(groupCreator.getId(), groupCreator);
        //newGroup.addMember(groupCreator);
        return newGroup;
    }

    public HashMap<Integer, Group> getGroupMap() {
        return groupMap;
    }

    public User createUser(int id, String first_name, String last_name, String email, String password ){
        User newUser = new User(id, first_name, last_name, email, password);
        userMap.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public Group getGroup(int groupID) {
        return groupMap.get(groupID);
    }

    @Override
    public void delete(String groupID) {
        groupMap.remove(groupID);
    }

    @Override
    public void addUserToGroup(int groupID, int userID) {
        Group group = groupMap.get(groupID);
        User newUser = userMap.get(userID);
        userMap.put(userID, newUser);
        group.addMember(newUser);
    }

    @Override
    public void removeUserFromGroup(int groupID, int userID) {
        Group group = groupMap.get(groupID);
        User newUser = userMap.get(userID);
        group.removeMember(newUser);
    }
}
