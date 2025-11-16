package view;

import javax.swing.*;

public class MainFrame extends JFrame {

    public MainFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Bill Splitter - Group 14");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Add initial components
        JLabel welcomeLabel = new JLabel("Welcome to Bill Splitter App!", JLabel.CENTER);
        add(welcomeLabel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
