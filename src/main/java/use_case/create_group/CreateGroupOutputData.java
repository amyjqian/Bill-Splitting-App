package use_case.create_group;

import entities.Group;

public class CreateGroupOutputData {
    private final Group group;
    private final String inviteLink;

    public CreateGroupOutputData(Group group){
        this.group = group;
        this.inviteLink = group.getInviteLink();
    }
    public String getInviteLink() {
        return group.getInviteLink();
    }
}
