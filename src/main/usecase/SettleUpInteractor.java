package main.usecase;

import main.entities.Expense;
import main.entities.Group;

import java.util.List;

public class SettleUpInteractor implements SettleUpInputBoundary {
    private final SettlementCalculator calculator;
    private final SettleUpOutputBoundary presenter;

    public SettleUpInteractor(SettlementCalculator calculator, SettleUpOutputBoundary presenter) {
        this.calculator = calculator;
        this.presenter = presenter;
    }

    @Override
    public void settleUp(SettleUpInputData inputData) {
        Group group = inputData.getGroup();
        List<Expense> expenses = group.getExpenses();
        boolean allSettled = true;
        for (Expense expense : expenses) {
            if (!expense.isSettled()) {
                allSettled = false;
                break;
            }
        }
        if (allSettled) {
            presenter.present( new SettleUpOutputData(
                    true,
                    true,
                    "All Settled"
            ));
            return;
        }
        String message = calculator.suggestedPayment(group);
        presenter.present( new SettleUpOutputData(
                true,
                true,
                message));
    }

    @Override
    public void markAsSettled(SettleUpInputData inputData) {
        Group group = inputData.getGroup();
        List<Expense> expenses = group.getExpenses();
        for (Expense expense : expenses) {
            expense.setSettled();
        }
        presenter.present( new SettleUpOutputData(
                true,
                true,
                "All Settled"));
    }
}
