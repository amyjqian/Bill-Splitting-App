package use_case.join_group;

/**
 * Input Boundary for the join_group use case.
 */
public interface JoinGroupInputBoundary {

    /**
     * Executes the create_group use case. After this executes,
     * a new group will be created.
     * @param joinGroupInputData the input data
     */
    void execute(JoinGroupInputData joinGroupInputData);
}