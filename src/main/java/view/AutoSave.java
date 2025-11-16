package view;

import javax.swing.*;
import java.awt.*;

public class AutoSave extends JPanel {

    private JLabel saveStatus;

    public AutoSave() {
        setLayout(new BorderLayout());

        saveStatus = new JLabel("All changes saved.", SwingConstants.CENTER);
        saveStatus.setForeground(Color.GREEN);

        add(saveStatus, BorderLayout.SOUTH);
    }

    public void setSaveStatus(String text, Color color) {
        saveStatus.setText(text);
        saveStatus.setForeground(color);
    }
}