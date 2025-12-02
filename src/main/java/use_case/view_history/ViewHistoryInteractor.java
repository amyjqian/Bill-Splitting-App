package use_case.view_history;

import entities.Expense;

import java.util.ArrayList;
import java.util.List;

public class ViewHistoryInteractor implements ViewHistoryInputBoundary {

    private final ViewHistoryDataAccessInterface viewHistoryDataAccessInterface;
    private final ViewHistoryOutputBoundary viewHistoryOutputBoundary;

    public ViewHistoryInteractor(ViewHistoryDataAccessInterface viewHistoryDataAccessInterface,
                                 ViewHistoryOutputBoundary viewHistoryOutputBoundary) {
        this.viewHistoryDataAccessInterface = viewHistoryDataAccessInterface;
        this.viewHistoryOutputBoundary = viewHistoryOutputBoundary;
    }

    @Override
    public void execute(ViewHistoryInputData viewHistoryInputData) {

        String groupId = viewHistoryInputData.getGroupId();
        if (groupId == null || groupId.isBlank()) {
            viewHistoryOutputBoundary.prepareFailedView(
                    new ViewHistoryOutputData(List.of(), "Invalid group ID.", false)
            );
            return;
        }

        try {
            List<Expense> expenses = viewHistoryDataAccessInterface.getGroupExpenses(groupId);

            if (expenses == null) {
                viewHistoryOutputBoundary.prepareFailedView(
                        new ViewHistoryOutputData(null, "No history found.", false)
                );
                return;
            }

            List<List<Object>> cleanRows = new ArrayList<>();

            for (Expense exp : expenses) {
                cleanRows.add(List.of(
                        exp.getExpenseName(),
                        exp.getAmount(),
                        exp.getDate()
                ));
            }

            viewHistoryOutputBoundary.prepareSuccessView(
                    new ViewHistoryOutputData(cleanRows, "", true)
            );

        } catch (Exception e) {
            viewHistoryOutputBoundary.prepareFailedView(
                    new ViewHistoryOutputData(List.of(), e.getMessage(), false)
            );
        }
    }
}
