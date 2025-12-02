package use_case.create_group;

import entities.Group;

/**
 * DAO interface for the Create Group Use Case.
 */

public interface CreateGroupDataAccessInterface {
    /**
     * Create a new group by groupID
     * @param name the name of the group.
     */

    Group createGroup(String name);

}
