package main.usecase;

import main.entities.Expense;
import main.entities.Group;

import java.util.List;

public class SettleUpCalculator implements SettlementCalculator{
    @Override
    public String suggestedPayment(Group group) {
        List<Expense> expenses = group.getExpenses();
        for (Expense expense : expenses){
            if (!expense.isSettled()){

            }
        }
        return "";
    }
}
