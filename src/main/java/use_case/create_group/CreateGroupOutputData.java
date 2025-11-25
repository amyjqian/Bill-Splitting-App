package use_case.create_group;

import entities.Group;

public class CreateGroupOutputData {
    private final Group group;

    public CreateGroupOutputData(Group group){
        this.group = group;
    }
    public String getInvite_link() {
        return group.getInvite_link();
    }
}
