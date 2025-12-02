/*
package view;

import interface_adapter.auto_save.AutoSaveController;
import use_case.auto_save.AutoSaveInteractor;
import data_access.FileAutoSaveDataAccessObject;
import data_access.AutoSaveDataAccessObject;

import javax.swing.*;
import java.awt.*;

public class AutoSave extends JPanel {

    private JLabel saveStatus;
    private AutoSaveController controller;

    public AutoSave() {
        setLayout(new BorderLayout());

        saveStatus = new JLabel("All changes saved.", SwingConstants.CENTER);
        saveStatus.setForeground(Color.GREEN);
        add(saveStatus, BorderLayout.SOUTH);

        AutoSaveDataAccessObject gateway = new FileAutoSaveDataAccessObject();
        AutoSaveInteractor interactor = new AutoSaveInteractor(gateway);
        controller = new AutoSaveController(interactor);

        String draft = controller.loadDraft();
        if (!draft.isEmpty()) {
            setSaveStatus("Load previous draft.", Color.BLUE);
        }
    }

    public void onUserEdit(String newText) {
        try {
            controller.autosave(newText);
            setSaveStatus("All changes saved.", Color.GREEN);
        } catch (Exception e) {
            setSaveStatus("Failed to save changes.", Color.RED);
        }
    }

    public void setSaveStatus(String text, Color color) {
        saveStatus.setText(text);
        saveStatus.setForeground(color);
    }
}
*/