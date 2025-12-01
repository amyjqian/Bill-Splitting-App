package main.view;

import javax.swing.*;

public class AutoSaveDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("AutoSave Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);

            AutoSave autoSavePanel = new AutoSave();
            frame.add(autoSavePanel);

            frame.setVisible(true);
        });
    }
}