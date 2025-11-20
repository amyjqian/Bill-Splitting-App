package interface_adapter.view_history;

import use_case.view_history.ViewHistoryInputBoundary;
import use_case.view_history.ViewHistoryInputData;
import use_case.view_history.ViewHistoryInteractor;

public class ViewHistoryController {

    private final ViewHistoryInputBoundary interactor;

    public ViewHistoryController(ViewHistoryInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String groupId) {
        ViewHistoryInputData inputData = new ViewHistoryInputData(groupId);
        interactor.execute(inputData);
    }
}
