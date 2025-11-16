package use_cases.create_group;

public class CreateGroupOutputData {
    private final String groupID;
    private final String groupName;

    public CreateGroupOutputData(String groupID, String name){
        this.groupID = groupID;
        this.groupName = name;
    }

    public String getGroupID() {
        return groupID;
    }
    public String getGroupName() {
        return groupName;
    }
}
