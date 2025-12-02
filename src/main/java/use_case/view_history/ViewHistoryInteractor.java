package use_case.view_history;

import entities.Expense;
// import use_case.view_history.*;

import java.util.List;

public class ViewHistoryInteractor implements ViewHistoryInputBoundary{
    private final ViewHistoryDataAccessInterface viewHistoryDataAccessInterface;
    private final ViewHistoryOutputBoundary viewHistoryOutputBoundary;

    public ViewHistoryInteractor(ViewHistoryDataAccessInterface viewHistoryDataAccessInterface,
                                 ViewHistoryOutputBoundary viewHistoryOutputBoundary) {
        this.viewHistoryDataAccessInterface = viewHistoryDataAccessInterface;
        this.viewHistoryOutputBoundary = viewHistoryOutputBoundary;
    }
    public void execute(ViewHistoryInputData viewHistoryInputData) {

        String groupId = viewHistoryInputData.getGroupId();
        if (groupId == null || groupId.isBlank()) {
            viewHistoryOutputBoundary.prepareFailedView(
                    new ViewHistoryOutputData(List.of(), "Invalid group ID.", false)
            );
            return;
        }
        try {
            // dao
            List<Expense> expenses = viewHistoryDataAccessInterface.getGroupExpenses(groupId);

            // empty expense
            if (expenses == null) {
                ViewHistoryOutputData outputData =
                        new ViewHistoryOutputData(null, "No history found.", false);
                viewHistoryOutputBoundary.prepareFailedView(outputData);
                return;
            }

            // successful output
            ViewHistoryOutputData outputData =
                    new ViewHistoryOutputData(expenses,"", true);

            viewHistoryOutputBoundary.prepareSuccessView(outputData);

            } catch (Exception e) {
                // api failed
                ViewHistoryOutputData outputData =
                        new ViewHistoryOutputData(List.of(), e.getMessage(), false);
                viewHistoryOutputBoundary.prepareFailedView(outputData);
            }
        }
    }

