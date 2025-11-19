package interface_adapter.view_history;

import entities.Expense;
import java.util.List;

public class MyGroupState {
    private List<Expense> expenses;
    private String message;

    public List<Expense> getExpenses() { return expenses; }
    public void setExpenses(List<Expense> expenses) { this.expenses = expenses; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
