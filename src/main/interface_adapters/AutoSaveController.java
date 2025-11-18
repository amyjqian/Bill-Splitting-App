package main.interface_adapters;

import main.usecase.AutoSaveInputBoundary;
import main.usecase.AutoSaveRequestModel;
import main.usecase.AutoSaveResponseModel;

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