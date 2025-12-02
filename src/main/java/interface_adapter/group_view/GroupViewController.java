package interface_adapter.group_view;

import interface_adapter.create_group.CreateGroupController;
import interface_adapter.view_history.ViewHistoryController;

public class GroupViewController {
    private final CreateGroupController createGroupController;
    private final ViewHistoryController viewMyGroupController;

    public GroupViewController(CreateGroupController create, ViewHistoryController view) {
        this.createGroupController = create;
        this.viewMyGroupController = view;
    }
    public void onCreateGroupClicked() {
        createGroupController.execute();
    }

    public void onViewMyGroupClicked(String groupID) {
        viewMyGroupController.execute(groupID);
    }
}