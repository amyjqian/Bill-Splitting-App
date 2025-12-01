package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class ExpenseFrame extends JFrame {

    public ExpenseFrame() {
        initializeUI();
    }

    private void initializeUI() {
        // Temporary store the mapping from expense to color
        Map<String, String> Expenses = Map.of(
                "rent", "RED",
                "food", "GREEN",
                "util", "ORANGE",
                "other", "BLACK"
        );

        setTitle("Bill Splitter - Group 14");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Split Expense Here", JLabel.CENTER);
        JButton fixedExpense = new JButton("Fixed Expense");
        JButton temporaryExpense = new JButton("Temporary Expense");

        String category = "rent"; // The category of the expense should be displayed here
        String colorName = Expenses.get(category);
        Color color = Color.BLACK;

        try {
            color = (Color) Color.class.getField(colorName).get(null);
        } catch (Exception ignored) {}

        temporaryExpense.setForeground(color);



        // Simple button listener
        fixedExpense.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close current window
                dispose();

                // Open new screen
                new ExpenseDesc().setVisible(true);
            }
        });

        add(welcomeLabel, BorderLayout.NORTH);
        add(fixedExpense, BorderLayout.CENTER);
        add(temporaryExpense, BorderLayout.SOUTH);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExpenseFrame().setVisible(true);
        });
    }
}