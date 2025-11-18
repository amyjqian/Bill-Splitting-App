package main.view;

import interface_adapters.AutoSaveController;
import usecase.AutoSaveInteractor;
import data.FileAutoSaveGateway;

import javax.swing.*;
import java.awt.*;

public class AutoSave extends JPanel {

    private JLabel saveStatus;

    public AutoSave() {
        setLayout(new BorderLayout());

        saveStatus = new JLabel("All changes saved.", SwingConstants.CENTER);
        saveStatus.setForeground(Color.GREEN);
        add(saveStatus, BorderLayout.SOUTH);

        FileAutoSaveGateway gateway = new FileAutoSaveGateway();
        AutoSaveInteractor interactor = new AutoSaveInteractor(gateway);
        controller = new AutoSaveController(interactor);

        String draft = controller.loadDraft();
        if (!draft.isEmpty()) {
            setSaveStatus("Load previous draft.", color.BLUE);
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