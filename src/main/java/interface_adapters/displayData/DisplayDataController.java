package interface_adapters.displayData;

import use_case.DisplayData.DisplayDataInputBoundary;

public class DisplayDataController {

    private final DisplayDataInputBoundary interactor;

    public DisplayDataController(DisplayDataInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void load() {
        interactor.execute();
    }
}
