package use_case.DisplayData;

import interface_adapters.displayData.DisplayDataViewModel;
import view.DisplayDataView;

import java.util.Map;

public interface DisplayDataOutputBoundary {
    void present(Map<String, Map<String, Object>> data);

    class DisplayDataPresenter implements DisplayDataOutputBoundary {

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
}
