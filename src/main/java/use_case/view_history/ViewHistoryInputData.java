package use_case.view_history;

public class ViewHistoryInputData {
    private final String groupId;

    public ViewHistoryInputData(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }
}
