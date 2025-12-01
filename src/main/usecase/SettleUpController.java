package main.usecase;

import main.entities.Group;

public class SettleUpController {
    private final SettleUpInputBoundary interactor;

    public SettleUpController(SettleUpInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void onSettleUpPressed(Long groupId) {
        SettleUpInputData inputData = new SettleUpInputData(groupId);
        interactor.settleUp(inputData);
    }

    public void onPaidPressed(Long groupId) {
        SettleUpInputData inputData = new SettleUpInputData(groupId);
        interactor.markAsSettled(inputData);
    }
}
