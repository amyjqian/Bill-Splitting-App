package use_case.auto_save;

public interface AutoSaveInputBoundary {
    void save(AutoSaveRequestModel requestModel);
    String load();
}