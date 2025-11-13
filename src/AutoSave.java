package main.view;

import javax.swing.*;
import java.awt.*;

public class AutoSave extends JFrame{
    public static void main(String[] args) {
        JFrame frame = new JFrame("Auto Save Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new BorderLayout());

        JTextField inputField = new JTextField();
        frame.add(inputField, BorderLayout.CENTER);

        JLabel saveStatus = new JLabel("All changes saved.", SwingConstants.CENTER);
        saveStatus.setForeground(new Color(0, 128,0));
        frame.add(saveStatus, BorderLayout.SOUTH);

        Timer[] timerHolder = new Timer[1];

        inputField.addKeyListener(new KeyAdapter() {
        @Override
        public void keyTyped(keyEvent e) {
            saveStatus.setText("Saving...");
            saveStatus.setForeground(Color.GRAY);

            if (timerHolder[0] != null && timerHolder[0].isRunning())
                timerHolder[0].stop();
            }

            timerHolder[0] = new Timer(1000, ev -> {
                if (Math.random() < 0.85) {
                    saveStatus.setText("All changes saved.")
                    saveStatus.setForeground(new Color(0, 128, 0));
            } else {
                saveStatus.setText("Changes failed to save.")
                saveStatus.setForeground(Color.RED);
            });
            timerHolder[0].setRepeats(false);
            timerHolder[0].start();
        }
    });
    frame.setVisible(true);
}