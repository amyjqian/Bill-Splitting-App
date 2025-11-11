package main.usecase;

public class AddExpenseInputData {
    private final float amount;
    private final String description;
    public AddExpenseInputData(float amount, String description) {
        this.amount = amount;
        this.description = description;
    }

    float getAmount() {
        return amount;
    }

    String getDescription() {
        return description;
    }
}
