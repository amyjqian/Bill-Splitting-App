package interface_adapter.view_history;

import use_case.view_history.ViewHistoryOutputBoundary;
import use_case.view_history.ViewHistoryOutputData;

public class ViewHistoryPresenter implements ViewHistoryOutputBoundary {
    private final MyGroupViewModel viewModel;
    private ViewHistoryOutputData outputData;

    public ViewHistoryPresenter(MyGroupViewModel viewModel, ViewHistoryOutputData outputData) {
        this.viewModel = viewModel;
        this.outputData = outputData;
    }

    public void prepareSuccessView(ViewHistoryOutputData outputData) {
        viewModel.setExpenses(outputData.getExpenses());
        viewModel.setMessage(outputData.getMessage());
        viewModel.firePropertyChanged();
    }

    public void prepareFailedView(ViewHistoryOutputData outputData) {
        viewModel.setExpenses(outputData.getExpenses());
        viewModel.setMessage(outputData.getMessage());
        viewModel.firePropertyChanged();
    }
}

