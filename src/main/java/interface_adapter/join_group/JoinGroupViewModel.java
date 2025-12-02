package interface_adapter.join_group;
/**
 * The View Model for the JoinGroup View.
 */
public class JoinGroupViewModel {
    private int groupID;
    private int userID;
    private boolean joined;

    public JoinGroupViewModel() {

    }
    public int getGroupID() {
        return groupID;
    }

    public void setGroupID(int id) {
        this.groupID = id;
    }

    public void setJoined() {
        this.joined = true;
    }

    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int id) {
        this.userID = id;
    }

    public void setJoined(boolean joined) {
        this.joined = joined;
    }
    public boolean getJoined() {
        return joined;
    }
}
