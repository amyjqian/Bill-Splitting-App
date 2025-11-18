package use_case.view_history;

import entities.Expense;
import entities.User;

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
                ViewHistoryOutputData outputData = new ViewHistoryOutputData(List.of(), "No expenses recorded.");
                return;
            }

            // api call to expense data
            // String id, String name, double amount, String description, User paidBy, String date
            List<Expense> dtoList = expenses.stream()
                    .map(e -> new Expense(
                            e.getId(),
                            e.getAmount(),
                            e.getDescription(),
                            e.getPaidBy(),
                            e.getDate()
                    ))
                    .toList();

            // successful output
            ViewHistoryOutputData outputData =
                    new ViewHistoryOutputData(dtoList, "");
            viewHistoryOutputBoundary.prepareSuccessView(outputData);
            } catch (Exception e) {
                // api failed
                ViewHistoryOutputData outputData =
                        new ViewHistoryOutputData(List.of(), "Failed to fetch history.");

                viewHistoryOutputBoundary.prepareFailedView(outputData);
            }
        }
    }

