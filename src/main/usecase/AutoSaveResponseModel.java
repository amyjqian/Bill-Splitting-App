package usecase;

public class AutoSaveResponseModel {
    private final boolean success;
    private final String content;

    public AutoSaveResponseModel(boolean success, String content) {
        this.success = success;
        this.content = content;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getContent() {
        return content;
    }
}