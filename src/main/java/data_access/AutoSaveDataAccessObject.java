package data_access;

public interface AutoSaveDataAccessObject {
    void safeDraft(String content);

    void saveDraft(String content);

    String loadDraft();
}