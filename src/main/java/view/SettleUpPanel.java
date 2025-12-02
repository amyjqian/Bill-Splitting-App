package view;

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

        JTextArea suggestions = new JTextArea(stringBuilder.toString());
        suggestions.setEditable(false);
        suggestions.setMargin(new Insets(10, 10, 10, 10));
        add(new JScrollPane(suggestions), BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SettleUpPanel().setVisible(true));
    }

}