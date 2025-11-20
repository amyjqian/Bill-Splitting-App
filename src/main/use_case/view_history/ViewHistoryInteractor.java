package src.main.use_case.view_history;

import entities.Expense;
import src.main.use_case.view_history.*;

import java.util.List;

public class ViewHistoryInteractor implements ViewHistoryInputBoundary{
    private final ViewHistoryDataAccessInterface viewHistoryDataAccessobject;
    private final ViewHistoryOutputBoundary viewHistoryOutputBoundary;

    public ViewHistoryInteractor(ViewHistoryDataAccessInterface viewHistoryDataAccessobject,
                                 ViewHistoryOutputBoundary viewHistoryOutputBoundary) {
        this.viewHistoryDataAccessobject = viewHistoryDataAccessobject;
        this.viewHistoryOutputBoundary = viewHistoryOutputBoundary;
    }
    public void execute(ViewHistoryInputData viewHistoryInputData) {
        try {
            // get group
            String groupId = viewHistoryInputData.getGroupId();

            // dao
            List<Expense> expenses = viewHistoryDataAccessobject.getGroupExpenses(groupId);

            //history is empty
            if (expenses.isEmpty()) {
                ViewHistoryOutputData outputData = new ViewHistoryOutputData(List.of(),
                        "No expenses recorded.", true);
                viewHistoryOutputBoundary.prepareSuccessView(outputData);
                return;
            }

            // api call to expense data
            List<Expense> dtoList = expenses;

            // successful output
            ViewHistoryOutputData outputData =
                    new ViewHistoryOutputData(expenses, "Success", true);
            viewHistoryOutputBoundary.prepareSuccessView(outputData);
            } catch (Exception e) {
                // api failed
                ViewHistoryOutputData outputData =
                        new ViewHistoryOutputData(List.of(), "API Call Failed", false);

                viewHistoryOutputBoundary.prepareFailedView(outputData);
            }
        }
    }

