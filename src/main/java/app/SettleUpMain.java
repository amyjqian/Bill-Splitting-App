package app;

import view.SettleUpPanel;

import javax.swing.*;

public class SettleUpMain {
    public static void main(String[] args) {

        Long groupId = 90692920L;

        SwingUtilities.invokeLater(() -> new SettleUpPanel(groupId).setVisible(true));
    }
}
