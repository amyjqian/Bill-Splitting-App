package main.use_case.DisplayData;

import main.entities.Expense;
import java.util.List;
import java.util.Map;

public interface ExpenseDataAccessInterface {
    Map<String, Map<String, Object>> getAllExpenses();
}
