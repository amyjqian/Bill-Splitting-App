package view;

import data_access.AutoSaveDataAccessObject;
import data_access.FileAutoSaveDataAccessObject;
import interface_adapter.AddExpenseController;
import entities.User;
import entities.Group;
import data_access.SplitwiseDataAccess;
import interface_adapter.auto_save.AutoSaveController;
import interface_adapter.auto_save.AutoSaveStatusDisplay;
import use_case.AddExpenseFactory;
import use_case.auto_save.AutoSaveInteractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AddExpenseFrame extends JFrame implements AutoSaveStatusDisplay {
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

    private AutoSaveController autoSaveController;
    private JLabel autosaveLabel;

    public AddExpenseFrame() {
        this.dataAccess = new SplitwiseDataAccess();

        AutoSaveDataAccessObject dao = new FileAutoSaveDataAccessObject();
        AutoSaveInteractor interactor = new AutoSaveInteractor(dao, responseModel -> {
            if (responseModel.isSuccess()) {
                showAutoSaveStatus("All changes saved.");
            } else {
                showAutoSaveStatus("Failed to save.");
            }
        });
            autoSaveController = new AutoSaveController(interactor);

        initializeUI();
        initializeUI();
        loadRealGroups();
        setupAutoSave();
    }

    private void initializeUI() {
        setTitle("Add Expense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        autosaveLabel = new JLabel("All changes saved.", SwingConstants.CENTER);
        autosaveLabel.setForeground(Color.GREEN);

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

    private void setupAutoSave() {
        JTextField[] fields = {nameField, amountField, descField};
        for (JTextField field : fields) {
            field.getDocument().addDocumentListener(new DocumentListener() {
                public void insertUpdate(DocumentEvent e) { autoSave(); }
                public void removeUpdate(DocumentEvent e) { autoSave(); }
                public void changedUpdate(DocumentEvent e) { autoSave(); }
            });
        }

    }
    private void autoSave() {
        if (autoSaveController != null) {
            String draftContent = String.format("Name: %s\nAmount: %s\nDesc: %s",
                    nameField.getText(), amountField.getText(), descField.getText());
            autoSaveController.safeDraft(draftContent);
        }
    }

    @Override
    public void showAutoSaveStatus(String message) {
        if (autosaveLabel != null) {
            autosaveLabel.setText(message);
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
        }
    }

    public void setController(AddExpenseController controller) {
        this.controller = controller;
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
//            if (controller == null) {
//                JOptionPane.showMessageDialog(ExpenseDesc.this,
//                        "Controller not initialized", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }

            try {
                String expenseName = nameField.getText().trim();
                String amountText = amountField.getText().trim();
                String description = descField.getText().trim();
                String category = (String) comboBox.getSelectedItem();
                float amount;


                try {
                    amount = Float.parseFloat(amountText);
                } catch (NumberFormatException ex) {
                    showError("Please enter a valid amount");
                    return;
                }
                ArrayList<User> participants = new ArrayList<>();
                for (JCheckBox checkbox : participantCheckboxes) {
                    if (checkbox.isSelected()) {
                        User user = (User) checkbox.getClientProperty("user");
                        if (user != null) {
                            participants.add(user);
                        }
                    }
                }
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
        AddExpenseFrame view = AddExpenseFactory.createView();
        view.setVisible(true);
    }
}