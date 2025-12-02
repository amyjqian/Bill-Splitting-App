package data_access;

public interface AutoSaveDataAccessObject {
    void safeDraft(String content);
    String loadDraft();
}