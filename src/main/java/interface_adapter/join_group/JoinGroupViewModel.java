package interface_adapter.join_group;
/**
 * The View Model for the JoinGroup View.
 */
public class JoinGroupViewModel {
    private int groupID;
    private int userID;

    public JoinGroupViewModel() {

    }
    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int id) {
        this.groupID = id;
    }


    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int id) {
        this.userID = id;
    }
}
