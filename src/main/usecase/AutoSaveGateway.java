package data;

public interface AutoSaveGateway {
    void saveDraft(String content);
    String loadDraft();
}