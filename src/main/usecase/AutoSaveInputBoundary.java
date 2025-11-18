package main.usecase;

public interface AutoSaveInputBoundary {
    AutoSaveResponseModel save(AutoSaveRequestModel requestModel);
    AutoSaveResponseModel load();
}