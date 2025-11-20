package main.usecase;

public class SettleUpOutputData {
    private boolean allSettled;
    private boolean success;
    private String payments;

    public SettleUpOutputData(boolean allSettled, boolean success, String payments) {
        this.allSettled = allSettled;
        this.success = success;
        this.payments = payments;
    }

    public boolean isAllSettled() {
        return allSettled;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getPayments() {
        return payments;
    }
}