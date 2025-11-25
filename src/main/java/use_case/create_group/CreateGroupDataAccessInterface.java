package use_case.create_group;

import entities.Group;
import entities.User;

/**
 * DAO interface for the Create Group Use Case.
 */

public interface CreateGroupDataAccessInterface {
    /**
     * Create a new group by groupID
     * @param name the name of the group.
     * @param groupType the type of group.
     * @param groupCreator the user who created the group.
     */

    Group createGroup(String name, String groupType, User groupCreator);

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
