package interface_adapter.view_history;

import entities.Expense;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MyGroupState {
    private List<Expense> expenses =  new ArrayList<>();
    private String message = "";

    public MyGroupState() {}

    public MyGroupState(MyGroupState other) {
        this.expenses = new ArrayList<>(other.expenses);
        this.message = other.message;
    }

    public List<Expense> getExpenses() { return expenses; }
    public void setExpenses(List<Expense> expenses) { this.expenses = new ArrayList<>(expenses); }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MyGroupState)) return false;
        MyGroupState that = (MyGroupState) o;
        return Objects.equals(expenses, that.expenses) &&
                Objects.equals(message, that.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expenses, message);
    }
}
