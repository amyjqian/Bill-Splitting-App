package main.presenter;

import main.use_case.DisplayData.DisplayDataOutputBoundary;
import main.view.DisplayDataView;

import java.util.Map;

public class DisplayDataPresenter implements DisplayDataOutputBoundary {

    private final DisplayDataViewModel viewModel;
    private final DisplayDataView view;

    public DisplayDataPresenter(DisplayDataViewModel viewModel,
                                DisplayDataView view) {
        this.viewModel = viewModel;
        this.view = view;
    }

    @Override
    public void present(Map<String, Map<String, Object>> data) {
        viewModel.setData(data);
        view.update(viewModel);
    }
}
