package main.usecase;

import main.entities.Group;

public class SettleUpController {
    private final SettleUpInputBoundary interactor;

    public SettleUpController(SettleUpInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void onSettleUpPressed(Group group) {
        SettleUpInputData inputData = new SettleUpInputData(group);
        interactor.settleUp(inputData);
    }
}
