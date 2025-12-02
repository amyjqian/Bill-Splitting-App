package data_access;

import java.io.*;

public abstract class FileAutoSaveDataAccessObject implements AutoSaveDataAccessObject {

    private final File file = new File("autosave.txt");

    @Override
    public void saveDraft(String content) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(content);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save draft", e);
        }
    }
    @Override
    public String loadDraft() {
        if (!file.exists()) return "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load draft", e);
        }
    }
}