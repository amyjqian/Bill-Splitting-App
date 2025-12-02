package use_case.join_group;

public class JoinGroupOutputData {
    private final boolean joined;
    public JoinGroupOutputData(boolean joined) {
        this.joined = joined;
    }

    public boolean getJoined() {
        return joined;
    }
}
