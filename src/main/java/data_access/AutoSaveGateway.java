package data_access;

public interface AutoSaveGateway {
    void saveDraft(String content);
    String loadDraft();
}