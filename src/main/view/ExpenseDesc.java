package main.view;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class ExpenseDesc extends JFrame {

    public ExpenseDesc() {
        initializeUI();
    }

    private void initializeUI() {


        setTitle("Add Expense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        JLabel titleLabel = new JLabel("Enter Expense Details", JLabel.CENTER);
        JTextField nameField = new JTextField(10);
        JTextField amountField = new JTextField(10);
        JTextField descField = new JTextField(10);

        String[] options = {"Utility", "Food", "Gifts"};
        JComboBox<String> comboBox = new JComboBox<>(options);

        String[] options = {"Utility", "Food", "Gifts"};
        JComboBox<String> comboBox = new JComboBox<>(options);

        JButton submitButton = new JButton("Submit");

        // Create checkboxes for participants
        JLabel participantsLabel = new JLabel("Participants:");
        JCheckBox participant1 = new JCheckBox("Amy");
        JCheckBox participant2 = new JCheckBox("Katie");
        JCheckBox participant3 = new JCheckBox("Tan");
        JCheckBox participant4 = new JCheckBox("Patricia");
        JCheckBox participant5 = new JCheckBox("Lucy");
        JCheckBox participant6 = new JCheckBox("Angela");

        // Panel for checkboxes with grid layout
        JPanel participantsPanel = new JPanel();
        participantsPanel.setLayout(new GridLayout(3, 2, 5, 5));
        participantsPanel.add(participant1);
        participantsPanel.add(participant2);
        participantsPanel.add(participant3);
        participantsPanel.add(participant4);
        participantsPanel.add(participant5);
        participantsPanel.add(participant6);

        // Main panel with components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 1, 10, 8));

        panel.add(titleLabel);
        panel.add(new JLabel("Expense Name:"));
        panel.add(nameField);
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
