package main.usecase;

public class AutoSaveRequestModel {
    private final String content;

    public AutoSaveRequestModel(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}