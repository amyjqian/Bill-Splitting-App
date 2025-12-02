package use_case.auto_save;

public interface AutoSaveInputBoundary {
    AutoSaveResponseModel save(AutoSaveRequestModel requestModel);
    AutoSaveResponseModel load();
}