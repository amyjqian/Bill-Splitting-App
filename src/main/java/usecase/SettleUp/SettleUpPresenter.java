package usecase.SettleUp;

public class SettleUpPresenter implements SettleUpOutputBoundary {
    private String message;
    private boolean allSettled;

    @Override
    public void present(SettleUpOutputData outputData) {
        this.message = outputData.getPayments();
        this.allSettled = outputData.isAllSettled();
    }

    public String getMessage() {
        return message;
    }

    public boolean isAllSettled() {
        return allSettled;
    }
}
