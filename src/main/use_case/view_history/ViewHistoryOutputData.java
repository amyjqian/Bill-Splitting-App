package use_case.view_history;

import entities.Expense;

import java.util.List;

public class ViewHistoryOutputData {
    // list of expenses, their amount and date
    // error message if something goes wrong
    private final List<Expense> expenses;
    private final String message;
    private final boolean success;

    public ViewHistoryOutputData(List<Expense> expenses, String message, boolean success) {
        this.expenses = expenses;
        this.message = message;
        this.success = success;
    }

    public List<Expense> getExpenses() { return expenses; }
    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
}
