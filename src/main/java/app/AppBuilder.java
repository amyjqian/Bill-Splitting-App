package app;

import javax.swing.*;
import view.GroupViewFrame;

public class AppBuilder {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GroupViewFrame frame = new GroupViewFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
