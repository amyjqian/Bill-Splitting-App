package use_case.create_group;

import entities.Group;

public class CreateGroupOutputData {
    private final Group group;
    private final long id;

    public CreateGroupOutputData(Group group){
        this.group = group;
        this.id = group.getId();
    }
    public Long getInviteLink() {
        return group.getId();
    }
}
