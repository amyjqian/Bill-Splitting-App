package use_case.create_group;

import entities.Group;

public class CreateGroupInteractor implements CreateGroupInputBoundary{
    private CreateGroupDataAccessInterface userDataAccessObject;
    private CreateGroupOutputBoundary createGroupPresenter;

    public CreateGroupInteractor(CreateGroupDataAccessInterface userDataAccessInterface,
                            CreateGroupOutputBoundary createGroupOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.createGroupPresenter = createGroupOutputBoundary;
    }

    @Override
    public void execute() {
        //implements the logic of the create_group use case
        // * insert a new group in the db
        // * instantiate the `CreateGroupOutputData`, which needs to contain NewGroup
        // * tell the presenter to prepare the "view my group" view (2.3).
        String test_id = "id2"; //placeholder for now
        Group NewGroup = userDataAccessObject.createGroup("id2");
        System.out.println("New Group: " + NewGroup);
        final CreateGroupOutputData updateCreateGroupOutputData = new CreateGroupOutputData(test_id, NewGroup);
        createGroupPresenter.prepareSuccessView(updateCreateGroupOutputData);
    }
}
