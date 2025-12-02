package interface_adapter.join_group;

import use_case.join_group.JoinGroupInputBoundary;
import use_case.join_group.JoinGroupInputData;

/**
 * The controller for the JoinGroup Use Case.
 */
public class JoinGroupController {

    private final JoinGroupInputBoundary joinGroupUseCaseInteractor;

    public JoinGroupController(JoinGroupInputBoundary joinGroupUseCaseInteractor) {
        this.joinGroupUseCaseInteractor = joinGroupUseCaseInteractor;
    }

    /**
     * Executes the JoinGroup Use Case.
     *
     * @param groupID the groupID.
     * @param userID  the userID.
     */
    public void execute(int groupID, int userID) {
        final JoinGroupInputData joinGroupInputData = new JoinGroupInputData(groupID, userID);
        joinGroupUseCaseInteractor.execute(joinGroupInputData);
    }
}
