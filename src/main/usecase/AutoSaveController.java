package interface_adapters;

import usecase.*;

public class AutoSaveController{

    private final AutoSaveInputBoundary interactor;

    public AutoSaveController(AutoSaveInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void autosave(String content) {
        AutoSaveRequestModel req = new AutoSaveRequestModel(content);
        interactor.save(req);
    }

    public String loadDraft() {
        AutoSaveResponseModel res = interactor.load();
        return res.getContent();
    }
}