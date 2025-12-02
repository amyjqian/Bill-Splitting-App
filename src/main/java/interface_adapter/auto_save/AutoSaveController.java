package interface_adapter.auto_save;

import use_case.auto_save.*;


public class AutoSaveController{

    private final AutoSaveInputBoundary interactor;

    public AutoSaveController(AutoSaveInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void autosave(String content) {
        interactor.save(new AutoSaveRequestModel(content));
    }

    public String loadDraft() {
        return interactor.load();
    }
}