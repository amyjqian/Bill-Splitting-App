package usecase.SettleUp;

import entities.Expense;

import java.util.List;

public interface SettleUpDataAccessInterface {
    List<Expense> getGroupExpenses(String groupId) throws Exception;
}
