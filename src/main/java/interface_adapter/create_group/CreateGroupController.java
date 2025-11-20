package interface_adapter.create_group;

import use_case.create_group.CreateGroupInputBoundary;

/**
 * The controller for the CreateGroup Use Case.
 */
public class CreateGroupController {

    private static CreateGroupInputBoundary createGroupInteractor;

    public CreateGroupController(CreateGroupInputBoundary createGroupUseCaseInteractor) {
        this.createGroupInteractor = createGroupUseCaseInteractor;
    }

    /**
     * Executes the CreateGroup Use Case.
     */
    public static void execute() {
        createGroupInteractor.execute();
    }
}
