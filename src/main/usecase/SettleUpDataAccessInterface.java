package main.usecase;

import main.entities.Expense;

import java.util.List;

public interface SettleUpDataAccessInterface {
    List<Expense> getGroupExpenses(String groupId) throws Exception;
}
