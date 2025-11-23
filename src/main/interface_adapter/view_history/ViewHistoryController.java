package interface_adapter.view_history;

import use_case.view_history.ViewHistoryInputBoundary;
import use_case.view_history.ViewHistoryInputData;

public class ViewHistoryController {

    private final ViewHistoryInputBoundary viewHistoryInputBoundary;

    public ViewHistoryController(ViewHistoryInputBoundary interactor) {
        this.viewHistoryInputBoundary = interactor;
    }

    public void execute(String groupId) {
        ViewHistoryInputData inputData = new ViewHistoryInputData(groupId);
        viewHistoryInputBoundary.execute(inputData);
    }
}
