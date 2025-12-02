package interface_adapter.create_group;

/**
 * The View Model for the CreateGroup View.
 */
public class CreateGroupViewModel {
    private String groupName;
    private Long inviteLink;

    public CreateGroupViewModel() {
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String newName) {
        groupName = newName;
    }


    public void setInviteLink(Long inviteLink) {
        this.inviteLink = inviteLink;
    }

    public Long getInviteLink() {
        return inviteLink;
    }
}
