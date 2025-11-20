package main.usecase;

public class AddExpenseOutputData {

    private final String expenseName;
    private final String message;

    public AddExpenseOutputData(String expenseName, String message) {
        this.expenseName = expenseName;
        this.message = message;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getMessage() {
        return message;
    }
}
