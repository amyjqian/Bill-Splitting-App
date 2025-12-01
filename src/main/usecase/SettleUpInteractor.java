package main.usecase;

import main.entities.Expense;
import main.entities.Group;

import java.util.List;

public class SettleUpInteractor implements SettleUpInputBoundary {

    private final SettleUpDataAccessInterface dataAccess;
    private final SettlementCalculator calculator;
    private final SettleUpOutputBoundary presenter;

    public SettleUpInteractor(SettleUpDataAccessInterface dataAccess, SettlementCalculator calculator, SettleUpOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.calculator = calculator;
        this.presenter = presenter;
    }

    @Override
    public void settleUp(SettleUpInputData inputData) {
        Long groupId = inputData.getGroupId();

        try {
            List<Expense> expenses = dataAccess.getGroupExpenses(groupId.toString());

            boolean allSettled = expenses.stream().allMatch(Expense::getSettled);

            if (allSettled) {
                presenter.present(new SettleUpOutputData(true, true, "All Settled"));
                return;
            }

            String message = calculator.suggestedPayment(expenses);
            presenter.present(new SettleUpOutputData(false, true, message));

        } catch (Exception e) {
            presenter.present(new SettleUpOutputData(false, false, "Error: " + e.getMessage()));
        }
    }

    public void markAsSettled(SettleUpInputData inputData) {
        Long groupId = inputData.getGroupId();

        try {
            List<Expense> expenses = dataAccess.getGroupExpenses(groupId.toString());
            for (Expense e : expenses) e.setSettled(true);

            presenter.present(new SettleUpOutputData(true, true, "All Settled"));

        } catch (Exception e) {
            presenter.present(new SettleUpOutputData(false, false, "Error: " + e.getMessage()));
        }
    }
}