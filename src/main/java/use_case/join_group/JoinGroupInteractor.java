package use_case.join_group;

public class JoinGroupInteractor implements JoinGroupInputBoundary{
    private JoinGroupDataAccessInterface userDataAccessObject;
    private JoinGroupOutputBoundary joinGroupPresenter;

    public JoinGroupInteractor(JoinGroupDataAccessInterface joinGroupDataAccessInterface,
                               JoinGroupOutputBoundary joinGroupOutputBoundary) {
        this.userDataAccessObject = joinGroupDataAccessInterface;
        this.joinGroupPresenter = joinGroupOutputBoundary;

    }

    @Override
    public void execute(JoinGroupInputData joinGroupInputData) {
        //implements the logic of the join_group use case
        // * add the user to an existing group in the db
        // * instantiate the `CreateGroupOutputData`, which needs to contain NewGroup
        // * tell the presenter to prepare the "view my group" view (2.3).
        int groupID = joinGroupInputData.getGroupID();
        int userID = joinGroupInputData.getUserID();
        userDataAccessObject.addUserToGroup(groupID, userID);
        System.out.println("User " + userID + " has been added to the group");
        final JoinGroupOutputData joinGroupOutputData = new JoinGroupOutputData(true);
        joinGroupPresenter.prepareStatus(joinGroupOutputData);
    }
}
