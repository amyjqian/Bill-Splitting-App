package use_cases.create_group;

import entities.Group;

/**
 * DAO interface for the Create Group Use Case.
 */

public interface CreateGroupDataAccessInterface {
    /**
     * Create a new group
     * @param groupID
     * @param groupName
     */
    Group createGroup(String groupID, String groupName);
}
