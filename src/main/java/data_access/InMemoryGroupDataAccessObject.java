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


    @Override
    public Group createGroup(String name, String groupType, User groupCreator) {
        Group newGroup = new Group(name, groupType, groupCreator);
        groupMap.put(newGroup.getGroupId(), newGroup);
        return newGroup;
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
        group.addMember(newUser);
    }

    @Override
    public void removeUserFromGroup(int groupID, int userID) {
        Group group = groupMap.get(groupID);
        User newUser = userMap.get(userID);
        group.removeMember(newUser);
    }
}
