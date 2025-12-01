package main.use_case.DisplayData;

import java.util.Map;

public interface ExpenseDataAccessInterface {
    Map<String, Map<String, Object>> getAllExpenses();
}
