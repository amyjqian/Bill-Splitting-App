package data_access;

import entities.Group;
import use_cases.create_group.CreateGroupDataAccessInterface;

import java.util.*;

public class GroupDataAccessObject implements CreateGroupDataAccessInterface {
    private HashMap<String, Group> groupMap; //private hashmap to test DAO

    public GroupDataAccessObject(){
        groupMap = new HashMap<>();
    }

    @Override
    public Group createGroup(String groupID, String groupName) {
        //create a new group
        Group g = new Group("id1", "group1");

        //add it to the "db"
        groupMap.put("group1", g);

        return g;
    }

}
