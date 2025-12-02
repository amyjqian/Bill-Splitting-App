package use_case.join_group;

public class JoinGroupInputData {
    private final int userID;
    private final int groupID;

    public JoinGroupInputData(int groupID, int userID) {
        this.userID = userID;
        this.groupID = groupID;
    }

    public int getUserID() {
        return userID;
    }

    public int getGroupID() {
        return groupID;
    }
}

