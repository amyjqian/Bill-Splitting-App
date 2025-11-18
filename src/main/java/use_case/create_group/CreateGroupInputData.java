package use_case.create_group;

public class CreateGroupInputData {
    private final String groupName;

    public CreateGroupInputData(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupName() {
        return this.groupName;
    }
}
