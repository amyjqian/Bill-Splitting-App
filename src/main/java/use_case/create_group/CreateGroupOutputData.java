package use_case.create_group;

import entities.Group;

public class CreateGroupOutputData {
    private final String groupID;
    private final Group group;

    public CreateGroupOutputData(String groupID, Group group){
        this.groupID = groupID;
        this.group = group;
    }

    public String getGroupID() {
        return groupID;
    }
    public Group getGroup() {
        return group;
    }
}
