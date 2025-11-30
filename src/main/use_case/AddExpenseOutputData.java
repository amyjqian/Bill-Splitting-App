package main.use_case;

public class AddExpenseOutputData {
    private final String expenseName;
    private final String message;
    private final double amount;
    private final int participantCount;

    public AddExpenseOutputData(String expenseName, String message, double amount, int participantCount) {
        this.expenseName = expenseName;
        this.message = message;
        this.amount = amount;
        this.participantCount = participantCount;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getMessage() {
        return message;
    }

    public double getAmount() {
        return amount;
    }

    public int getParticipantCount() {
        return participantCount;
    }
}