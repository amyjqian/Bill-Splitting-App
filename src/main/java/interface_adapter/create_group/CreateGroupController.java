package interface_adapter.create_group;

import use_case.create_group.CreateGroupInputBoundary;
import use_case.create_group.CreateGroupInputData;

/**
 * The controller for the CreateGroup Use Case.
 */
public class CreateGroupController {

    private final CreateGroupInputBoundary createGroupUseCaseInteractor;

    public CreateGroupController(CreateGroupInputBoundary createGroupUseCaseInteractor) {
        this.createGroupUseCaseInteractor = createGroupUseCaseInteractor;
    }

    /**
     * Executes the CreateGroup Use Case.
     *
     * @param groupName the group name.
     */
    public void execute(String groupName) {
        final CreateGroupInputData createGroupInputData = new CreateGroupInputData(groupName);
        createGroupUseCaseInteractor.execute(createGroupInputData);
    }
}
