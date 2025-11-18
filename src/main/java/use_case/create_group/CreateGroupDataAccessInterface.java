package use_case.create_group;

import entities.Group;

/**
 * DAO interface for the Create Group Use Case.
 */

public interface CreateGroupDataAccessInterface {
    /**
     * Create a new group by groupID
     * @param groupID
     * @return group
     */
    Group createGroup(String groupID);

    /**
     * Fetch the group from the db by ID
     * @param groupID
     * @return group
     */
    Group getGroup(String groupID);

    /**
     * Modify the group associated with the id
     * @param id
     */
    void setNewGroup(String id, Group group);

    /**
     * Delete the group associated with groupID
     * @param groupID
     */
    void delete(String groupID);
}
