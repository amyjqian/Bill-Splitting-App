package main.view;

import javax.swing.*;
import java.awt.*;

public class SettleUpPanel extends JFrame {

    public SettleUpPanel() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Settle Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Suggested Payments", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        StringBuilder stringBuilder = new StringBuilder();

        String settlement = stringBuilder.toString();
        JLabel settlementLabel = new JLabel(settlement);
        JPanel textPanel = new JPanel();
        textPanel.add(settlementLabel);
        add(textPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 20));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SettleUpPanel().setVisible(true));
    }

}