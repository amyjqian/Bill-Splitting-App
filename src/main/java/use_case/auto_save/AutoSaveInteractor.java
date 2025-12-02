package use_case.auto_save;

import data_access.AutoSaveDataAccessObject;

public class AutoSaveInteractor implements AutoSaveInputBoundary {
    private final AutoSaveDataAccessObject dao;
    private final AutoSaveOutputBoundary presenter;

    public AutoSaveInteractor(AutoSaveDataAccessObject dao, AutoSaveOutputBoundary presenter) {
        this.dao = dao;
        this.presenter = presenter;
    }

    @Override
    public void save(AutoSaveRequestModel requestModel) {
        dao.saveDraft(requestModel.getContent());
        presenter.present(
                new AutoSaveResponseModel(true, requestModel.getContent())
        );
    }

    @Override
    public String load() {
        String content = dao.loadDraft();
        boolean success = content != null && !content.isEmpty();
        presenter.present(
                new AutoSaveResponseModel(success, content)
        );
        return content;
    }
}