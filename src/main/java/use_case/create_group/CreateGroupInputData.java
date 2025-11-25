package use_case.create_group;

import entities.User;

public class CreateGroupInputData {
    private final String groupName;
    private final String groupType;
    private final User groupCreator;

    public CreateGroupInputData(String groupName, String groupType, User groupCreator) {
        this.groupName = groupName;
        this.groupType = groupType;
        this.groupCreator = groupCreator;
    }
    public String getGroupName() {
        return this.groupName;
    }

    public String getGroupType() {
        return groupType;
    }

    public User getGroupCreator() {
        return this.groupCreator;
    }


}
