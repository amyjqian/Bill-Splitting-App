package data_access;

import entities.Group;
import use_case.create_group.CreateGroupDataAccessInterface;

import java.util.*;

public class InMemoryGroupDataAccessObject implements CreateGroupDataAccessInterface {
    private HashMap<String, Group> groupMap; //private hashmap to test DAO
    private String groupName;
    public InMemoryGroupDataAccessObject(){
        groupMap = new HashMap<>();
    }

    @Override
    public Group createGroup(String groupID) {
        //create a new group
        Group g = new Group(groupID);

        //POST it to the db
        groupMap.put(groupID, null);

        return g;
    }

    @Override
    public Group getGroup(String groupID) {
        //GET a group from the db by its id
        return groupMap.get(groupID);
    }

    @Override
    public void setNewGroup(String id, Group group) {
        //POST a corresponding group to an id
        groupMap.replace(id, group);
    }

    @Override
    public void delete(String groupID) {
        //remove a group from the db
        groupMap.remove(groupID);
    }



}
