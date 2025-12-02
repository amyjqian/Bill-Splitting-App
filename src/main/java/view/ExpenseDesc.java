package view;

import javax.swing.*;
import java.awt.*;

public class ExpenseDesc extends JFrame {

    public ExpenseDesc() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Add Expense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Enter Amount and Description", JLabel.CENTER);
        JTextField amountField = new JTextField(10);
        JTextField descField = new JTextField(10);
        JButton submitButton = new JButton("Submit");

        // Simple panel with components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));

        panel.add(titleLabel);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(submitButton);

        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExpenseDesc().setVisible(true);
        });
    }
}
