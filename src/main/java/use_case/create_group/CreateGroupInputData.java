package use_case.create_group;

import java.util.List;

public class CreateGroupInputData {
    private final String groupName;

    public CreateGroupInputData(String groupName) {
        this.groupName = groupName;
    }
    public String getGroupName() {
        return this.groupName;
    }

    public List getMembers() {
        return this.getMembers();
    }
}
