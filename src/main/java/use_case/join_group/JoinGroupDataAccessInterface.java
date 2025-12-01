package use_case.join_group;

import entities.Group;
import entities.User;

/**
 * DAO interface for the Create Group Use Case.
 */

public interface JoinGroupDataAccessInterface {
    /**
     * Add user to a group.
     * @param groupID the id of the group
     * @param userID the id of the user
     */

    void addUserToGroup(int groupID, int userID);

    /**
     * Remove a user from the group.
     * @param groupID the id of the group
     * @param userID the id of the user
     */

    void removeUserFromGroup(int groupID, int userID);
}
