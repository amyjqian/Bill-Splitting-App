package use_case.create_group;

import entities.Group;
import entities.User;

public class CreateGroupInteractor implements CreateGroupInputBoundary{
    private CreateGroupDataAccessInterface userDataAccessObject;
    private CreateGroupOutputBoundary createGroupPresenter;

    public CreateGroupInteractor(CreateGroupDataAccessInterface userDataAccessInterface,
                            CreateGroupOutputBoundary createGroupOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.createGroupPresenter = createGroupOutputBoundary;
    }

    @Override
    public void execute(CreateGroupInputData createGroupInputData) {
        //implements the logic of the create_group use case
        // * insert a new group in the db
        // * instantiate the `CreateGroupOutputData`, which needs to contain NewGroup
        // * tell the presenter to prepare the "view my group" view (2.3).
        final String groupName = createGroupInputData.getGroupName();
        //final String groupType = createGroupInputData.getGroupType();
        //final User groupCreator = createGroupInputData.getGroupCreator();

        Group newGroup = userDataAccessObject.createGroup(groupName);
        System.out.println("New group: " + newGroup.getGroupName() + "created" + newGroup);

        final CreateGroupOutputData createGroupOutputData = new CreateGroupOutputData(newGroup);
        createGroupPresenter.present(createGroupOutputData);
        //createGroupPresenter.prepareSuccessView(updateCreateGroupOutputData);
    }
}
