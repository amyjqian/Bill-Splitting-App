package use_case.view_history;

import entities.Expense;
import java.util.List;

public interface ViewHistoryDataAccessInterface {
    List<Expense> getGroupExpenses(String groupId) throws Exception;
}
