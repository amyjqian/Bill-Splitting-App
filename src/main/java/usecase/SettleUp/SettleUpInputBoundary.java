package usecase.SettleUp;

public interface SettleUpInputBoundary {
    void settleUp(SettleUpInputData inputData);
    void markAsSettled(SettleUpInputData inputData);
}
