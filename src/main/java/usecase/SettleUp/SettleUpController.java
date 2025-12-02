package usecase.SettleUp;

public class SettleUpController {
    private final SettleUpInputBoundary interactor;

    public SettleUpController(SettleUpInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void onSettleUpPressed(Long groupId) {
        SettleUpInputData inputData = new SettleUpInputData(groupId);
        interactor.settleUp(inputData);
    }

    public void onMarkPressed(Long groupId) {
        SettleUpInputData inputData = new SettleUpInputData(groupId);
        interactor.markAsSettled(inputData);
    }
}
