package usecase;

import data.AutoSaveGateway;

public class AutoSaveInteractor implements AutoSaveInputBoundary {
    private final AutoSaveInteractor gateway;

    public AutoSaveInteractor(AutoSaveInteractor gateway) {
        this.gateway = gateway;
    }

    @Override
    public AutoSaveResponseModel save(AutoSaveRequestModel requestModel) {
        gateway.saveDraft(requestModel.getContent());
        return new AutoSaveResponseModel(true, requestModel.getContent());
    }

    @Override
    public AutoSaveResponseModel load() {
        String content = gateway.loadDraft();
        boolean success = content != null && !content.isEmpty();
        return new AutoSaveResponseModel(success, content);
    }
}