package use_cases.create_group;

public class CreateGroupInputData {
    private final String groupName;
    private final String groupID;

    public CreateGroupInputData(String groupName, String groupID){
        this.groupName = groupName;
        this.groupID = groupID;
    }

    public String getGroupName()
    {
        return this.groupName;
    }

    public String getGroupID(){
        return this.groupID;
    }
}
