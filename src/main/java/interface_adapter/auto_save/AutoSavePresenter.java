package interface_adapter.auto_save;

import use_case.auto_save.AutoSaveOutputBoundary;
import use_case.auto_save.AutoSaveResponseModel;

public class AutoSavePresenter implements AutoSaveOutputBoundary{
    private final AutoSaveStatusDisplay display;

    public AutoSavePresenter(AutoSaveStatusDisplay display) {
        this.display = display;
    }

    @Override
    public void present(AutoSaveResponseModel responseModel) {
        if (responseModel.isSuccess()) {
            display.showAutoSaveStatus("All changes saved.");
        } else {
            display.showAutoSaveStatus("Failed to load draft.");
        }
    }}