package main.view;

import main.controller.AddExpenseController;
import main.entities.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDesc extends JFrame {
    private AddExpenseController controller;
    private JTextField nameField;
    private JTextField amountField;
    private JTextField descField;
    private JComboBox<String> comboBox;
    private List<JCheckBox> participantCheckboxes;

    public ExpenseDesc() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Add Expense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        // Initialize components
        JLabel titleLabel = new JLabel("Enter Expense Details", JLabel.CENTER);
        nameField = new JTextField(20);
        amountField = new JTextField(20);
        descField = new JTextField(20);

        String[] options = {"Utility", "Food", "Gifts"};
        comboBox = new JComboBox<>(options);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());

        // Create checkboxes for participants
        JLabel participantsLabel = new JLabel("Participants:");
        participantCheckboxes = new ArrayList<>();

        String[] participantNames = {"Amy", "Tan", "Katie", "Patricia", "Angela", "Lucy"};
        for (String name : participantNames) {
            participantCheckboxes.add(new JCheckBox(name));
        }

        // Panel for checkboxes with grid layout
        JPanel participantsPanel = new JPanel();
        participantsPanel.setLayout(new GridLayout(3, 2, 5, 5));
        for (JCheckBox checkbox : participantCheckboxes) {
            participantsPanel.add(checkbox);
        }

        // Scroll pane for participants
        JScrollPane participantsScrollPane = new JScrollPane(participantsPanel);
        participantsScrollPane.setPreferredSize(new Dimension(400, 120));

        // Main panel with components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 1, 10, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        panel.add(titleLabel);
        panel.add(new JLabel("Expense Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.add(new JLabel("Description:"));
        panel.add(descField);
        panel.add(new JLabel("Category:"));
        panel.add(comboBox);
        panel.add(participantsLabel);
        panel.add(participantsScrollPane);
        panel.add(submitButton);

        add(panel);
    }

    // Set the controller for this view
    public void setController(AddExpenseController controller) {
        this.controller = controller;
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (controller == null) {
                JOptionPane.showMessageDialog(ExpenseDesc.this,
                        "Controller not initialized", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // Get input values
                String expenseName = nameField.getText().trim();
                String amountText = amountField.getText().trim();
                String description = descField.getText().trim();
                String category = (String) comboBox.getSelectedItem();

                // Validate required fields
                if (expenseName.isEmpty()) {
                    showError("Expense name cannot be empty");
                    return;
                }

                // Validate amount
                float amount;
                try {
                    amount = Float.parseFloat(amountText);
                    if (amount <= 0) {
                        showError("Amount must be positive");
                        return;
                    }
                } catch (NumberFormatException ex) {
                    showError("Please enter a valid amount");
                    return;
                }

                // Get selected participants
                ArrayList<User> participants = new ArrayList<>();
                for (JCheckBox checkbox : participantCheckboxes) {
                    if (checkbox.isSelected()) {
                        // Create user objects with name and email
                        participants.add(new User(checkbox.getText(),
                                checkbox.getText().toLowerCase() + "@example.com"));
                    }
                }

                // Validate at least one participant
                if (participants.isEmpty()) {
                    showError("Please select at least one participant");
                    return;
                }

                // Call controller to execute the use case
                controller.execute(expenseName, description, amount, category, participants);

            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
            }
        }
    }

    // Method to show success message
    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }

    // Method to show error message
    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // Clear form after successful submission
    private void clearForm() {
        nameField.setText("");
        amountField.setText("");
        descField.setText("");
        comboBox.setSelectedIndex(0);
        for (JCheckBox checkbox : participantCheckboxes) {
            checkbox.setSelected(false);
        }
        nameField.requestFocus(); // Set focus back to first field
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // For demo purposes - in a real app, this would be properly dependency injected
            ExpenseDesc view = new ExpenseDesc();

            // Create a simple presenter that connects to the view
            main.usecase.AddExpenseOutputBoundary presenter = new main.usecase.AddExpenseOutputBoundary() {
                @Override
                public void prepareSuccessView(main.usecase.AddExpenseOutputData outputData) {
                    view.showSuccess(outputData.getMessage());
                }

                @Override
                public void prepareFailView(String errorMessage) {
                    view.showError(errorMessage);
                }
            };

            // Create simple data access and factory for demo
            main.usecase.AddExpenseDataAccessInterface dataAccess = new main.usecase.AddExpenseDataAccessInterface() {
                @Override
                public void save(main.entities.Expense expense) {
                    System.out.println(
                            "Demo: Saving expense - " + expense.getExpenseName() +
                            ", Description: " + expense.getDescription() +
                            ", Amount: " + expense.getAmount() +
                            ", Participants: " + expense.getParticipants());
                }
            };

            main.entities.ExpenseFactory expenseFactory = new main.entities.ExpenseFactory();

            // Create interactor and controller
            main.usecase.AddExpenseInteractor interactor = new main.usecase.AddExpenseInteractor(
                    dataAccess, presenter, expenseFactory
            );

            AddExpenseController controller = new AddExpenseController(interactor);
            view.setController(controller);

            view.setVisible(true);
        });
    }
}