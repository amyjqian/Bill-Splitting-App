package use_case.create_group;

import entities.Group;

/**
 * DAO interface for the Create Group Use Case.
 */

public interface CreateGroupDataAccessInterface {
    /**
     * Create a new group by groupID
     *
     * @param name the name of the group.
     */

    Group createGroup(String name);

    /**
     * Fetch the group from the db by ID
     * @param groupID the group id.
     * @return group
     */
    Group getGroup(int groupID);

    /**
     * Delete the group associated with groupID
     * @param groupID the groupID.
     */
    void delete(String groupID);
}
