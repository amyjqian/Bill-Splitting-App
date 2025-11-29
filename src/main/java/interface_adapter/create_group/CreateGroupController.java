package interface_adapter.create_group;

import use_case.create_group.CreateGroupInputBoundary;
import use_case.create_group.CreateGroupInputData;

/**
 * The controller for the CreateGroup Use Case.
 */
public class CreateGroupController {

    private static CreateGroupInputBoundary createGroupInteractor;

    public CreateGroupController(CreateGroupInputBoundary createGroupUseCaseInteractor) {
        this.createGroupInteractor = createGroupUseCaseInteractor;
    }

//    /**
//     * Executes the CreateGroup Use Case.
//     * @param group_name
//     */
//    public void execute(String group_name, String group_type, String group_creator_name, CreateGroupInputData createGroupInputData) {
//        final CreateGroupInputData createGroupInputData = new CreateGroupInputData(
//                group_name, group_creator_name, );
//        createGroupInteractor.execute();
//    }
}
