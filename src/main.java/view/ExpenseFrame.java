package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExpenseFrame extends JFrame {

    public ExpenseFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Bill Splitter - Group 14");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JLabel welcomeLabel = new JLabel("Split Expense Here", JLabel.CENTER);
        JButton fixedExpense = new JButton("Fixed Expense");

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
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExpenseFrame().setVisible(true);
        });
    }
}