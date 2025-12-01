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

        JLabel titleLabel = new JLabel("Enter Amount and Description and Participants ", JLabel.CENTER);
        JTextField amountField = new JTextField(10);
        JTextField descField = new JTextField(10);
        JTextField participantsField = new JTextField(10);

        String[] options = {"Utility", "Food", "Gifts"};
        JComboBox<String> comboBox = new JComboBox<>(options);

        JButton submitButton = new JButton("Submit");

        // Simple panel with components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1, 10, 8));

        panel.add(titleLabel);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Participants:"));
        panel.add(participantsField);
        panel.add(new JLabel("Category"));
        panel.add(comboBox);
        panel.add(submitButton);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExpenseDesc().setVisible(true);
        });
    }
}
