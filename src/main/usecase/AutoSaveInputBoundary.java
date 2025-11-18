package usecase;

public interface AutoSaveInputBoundary {
    AutoSaveResponseModel save(AutoSaveRequest Model requestModel);
    AutoSaveResponseModel load();
}