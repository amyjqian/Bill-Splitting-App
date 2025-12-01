package use_case.auto_save;

public class AutoSaveRequestModel {
    private final String content;

    public AutoSaveRequestModel(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}