package use_case.view_history;

import entities.Expense;

import java.util.List;

public class ViewHistoryOutputData {
    // list of expenses, their amount and date
    // error message if something goes wrong
    private final List<List<Object>> expenses;
    private final String message;
    private final boolean success;

    public ViewHistoryOutputData(List<List<Object>> expenses, String message, boolean success) {
        this.expenses = expenses;
        this.message = message;
        this.success = success;
    }

    public List<List<Object>> getExpenses() { return expenses; }
    public String getMessage() { return message; }
    public boolean isSuccess() { return success; }
}
