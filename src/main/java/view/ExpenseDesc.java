package view;

import main.controller.AddExpenseController;
import main.entities.User;
import main.entities.Group;
import main.data_access.SplitwiseDataAccess;
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
    private JPanel participantsPanel;
    private SplitwiseDataAccess dataAccess;
    private Group currentGroup;
    private JComboBox<Group> groupComboBox;

    public ExpenseDesc() {
        this.dataAccess = new SplitwiseDataAccess();
        initializeUI();
        initializeUI();
        loadRealGroups();
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

        String[] options = {"Utility", "Food", "Gifts", "Transportation", "Entertainment", "Other"};
        comboBox = new JComboBox<>(options);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new SubmitButtonListener());

        // Create panel for participants
        participantCheckboxes = new ArrayList<>();
        participantsPanel = new JPanel();
        participantsPanel.setLayout(new GridLayout(0, 2, 5, 5));

        // Add group selection
        JLabel groupLabel = new JLabel("Select Group:");
        groupComboBox = new JComboBox<>();
        groupComboBox.addActionListener(e -> {
            Group selectedGroup = (Group) groupComboBox.getSelectedItem();
            if (selectedGroup != null) {
                currentGroup = selectedGroup;
                loadParticipants();
                setTitle("Add Expense - " + currentGroup.getName());
            }
        });

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
        panel.add(groupLabel);
        panel.add(groupComboBox);
        panel.add(new JLabel("Category:"));
        panel.add(comboBox);
        panel.add(new JLabel("Participants:"));
        panel.add(participantsScrollPane);
        panel.add(submitButton);

        add(panel);
    }

    // Load participants for the current group
    private void loadRealGroups() {
        try {
            List<Group> groups = dataAccess.getGroups();

            if (groups.isEmpty()) {
                showError("No groups found in your Splitwise account.");
            } else {
                for (Group group : groups) {
                    groupComboBox.addItem(group);
                }

                // Auto-select the first group
                if (groupComboBox.getItemCount() > 0) {
                    groupComboBox.setSelectedIndex(0);
                }
            }

        } catch (Exception e) {
            showError("Failed to load groups: " + e.getMessage());
        }
    }

    private void loadParticipants() {
        try {
            participantsPanel.removeAll();
            participantCheckboxes.clear();

            if (currentGroup != null && currentGroup.getMembers() != null) {

                for (User member : currentGroup.getMembers()) {
                    JCheckBox checkbox = new JCheckBox(member.getFirstName() + " " + member.getLastName());
                    checkbox.putClientProperty("user", member);
                    participantCheckboxes.add(checkbox);
                    participantsPanel.add(checkbox);

                    // Auto-select all participants by default
                    checkbox.setSelected(true);
                }

            } else {
                JLabel noMembersLabel = new JLabel("No members in this group");
                noMembersLabel.setHorizontalAlignment(SwingConstants.CENTER);
                participantsPanel.add(noMembersLabel);
            }

            participantsPanel.revalidate();
            participantsPanel.repaint();

        } catch (Exception e) {
            System.out.println("Error loading participants: " + e.getMessage());
//            loadPlaceholderParticipants();
        }
    }


    // Fallback method if API fails
    private void loadPlaceholderParticipants() {
        participantsPanel.removeAll();
        participantCheckboxes.clear();

        String[] placeholderNames = {"Amy", "Tan", "Katie", "Patricia", "Angela", "Lucy"};
        for (String name : placeholderNames) {
            JCheckBox checkbox = new JCheckBox(name);
            User placeholderUser = new User(name, name.toLowerCase(), name.toLowerCase() + "@example.com");
            checkbox.putClientProperty("user", placeholderUser);
            participantCheckboxes.add(checkbox);
            participantsPanel.add(checkbox);
        }

        participantsPanel.revalidate();
        participantsPanel.repaint();
    }

    // Set the controller for this view
    public void setController(AddExpenseController controller) {
        this.controller = controller;
    }

    // Set the current group (call this before showing the window)
    public void setCurrentGroup(Group group) {
        this.currentGroup = group;
        loadParticipants(); // Reload participants when group changes
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
                        User user = (User) checkbox.getClientProperty("user");
                        if (user != null) {
                            participants.add(user);
                        }
                    }
                }

                // Validate at least one participant
                if (participants.isEmpty()) {
                    showError("Please select at least one participant");
                    return;
                }

                // Validate current group is set
                if (currentGroup == null) {
                    showError("No group selected");
                    return;
                }

                // Call controller to execute the use case with group ID
                controller.execute(expenseName, description, amount, category,
                        participants, currentGroup.getId());

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
        nameField.requestFocus();
    }

    public static void main(String[] args) {
        // Simple main method without UIManager
        ExpenseDesc view = new ExpenseDesc();

        // Create presenter
        main.use_case.AddExpenseOutputBoundary presenter = new main.use_case.AddExpenseOutputBoundary() {
            @Override
            public void prepareSuccessView(main.use_case.AddExpenseOutputData outputData) {
                view.showSuccess(outputData.getMessage());
            }

            @Override
            public void prepareFailView(String errorMessage) {
                view.showError(errorMessage);
            }
        };

        // Create data access
        SplitwiseDataAccess dataAccess = new SplitwiseDataAccess();
        main.entities.ExpenseFactory expenseFactory = new main.entities.ExpenseFactory();

        // Create interactor and controller
        main.use_case.AddExpenseInteractor interactor = new main.use_case.AddExpenseInteractor(
                dataAccess, presenter, expenseFactory
        );

        AddExpenseController controller = new AddExpenseController(interactor);
        view.setController(controller);

        view.setVisible(true);
    }
}