package use_case.create_group;

/**
 * Input Boundary for the create_group use case.
 */
public interface CreateGroupInputBoundary {

    /**
     * Executes the create_group use case. After this executes,
     * a new group will be created.
     */
    void execute();
}