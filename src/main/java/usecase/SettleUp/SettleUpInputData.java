package usecase.SettleUp;


public class SettleUpInputData {

    private final Long groupId;

    public SettleUpInputData(Long groupId) {
        this.groupId = groupId;
    }

    public Long getGroupId() {
        return groupId;
    }
}
