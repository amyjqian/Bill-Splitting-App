package interface_adapter.view_history;

import use_case.view_history.ViewHistoryOutputBoundary;
import use_case.view_history.ViewHistoryOutputData;

public class ViewHistoryPresenter implements ViewHistoryOutputBoundary {
    private final MyGroupViewModel viewModel;

    public ViewHistoryPresenter(MyGroupViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void prepareSuccessView(ViewHistoryOutputData outputData) {
        viewModel.setExpenses(outputData.getExpenses());
        viewModel.setMessage(outputData.getMessage());
        viewModel.firePropertyChanged();
    }

    public void prepareFailedView(ViewHistoryOutputData outputData) {
        viewModel.setMessage(outputData.getMessage());
        viewModel.firePropertyChanged();
    }
}
