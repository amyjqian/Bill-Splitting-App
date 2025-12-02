package view;

import interface_adapter.add_expense.AddExpenseController;
import entities.User;
import entities.Group;
import data_access.SplitwiseDataAccess;
import use_case.add_expense.AddExpenseFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AddExpenseFrame extends JFrame {
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

    private JFrame previousFrame;

    public AddExpenseFrame(JFrame previousFrame) {
        this.previousFrame = previousFrame;
        this.dataAccess = new SplitwiseDataAccess();
        initializeUI();
        loadRealGroups();
    }

    public AddExpenseFrame() {
        this(null);
    }

    private void initializeUI() {
        setTitle("Add Expense");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600); // Slightly taller to accommodate back button
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton backButton = new JButton("Back to Group");
        backButton.addActionListener(new BackButtonListener());
        topPanel.add(backButton);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // CENTER PANEL: Expense form
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(0, 1, 10, 8));

        // Title
        JLabel titleLabel = new JLabel("Add New Expense", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        centerPanel.add(titleLabel);

        // Initialize components
        nameField = new JTextField(20);
        amountField = new JTextField(20);
        descField = new JTextField(20);

        String[] options = {"Utility", "Food", "Gifts", "Transportation", "Entertainment", "Other"};
        comboBox = new JComboBox<>(options);

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

        // Add form components to center panel
        centerPanel.add(new JLabel("Expense Name:"));
        centerPanel.add(nameField);
        centerPanel.add(new JLabel("Amount ($):"));
        centerPanel.add(amountField);
        centerPanel.add(new JLabel("Description:"));
        centerPanel.add(descField);
        centerPanel.add(groupLabel);
        centerPanel.add(groupComboBox);
        centerPanel.add(new JLabel("Category:"));
        centerPanel.add(comboBox);
        centerPanel.add(new JLabel("Participants:"));
        centerPanel.add(participantsScrollPane);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // BOTTOM PANEL: Submit button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Add Expense");
        submitButton.addActionListener(new SubmitButtonListener());
        submitButton.setPreferredSize(new Dimension(150, 40));
        submitButton.setBackground(new Color(76, 175, 80));
        submitButton.setForeground(Color.black);
        bottomPanel.add(submitButton);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    // Back button listener
    private class BackButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Close current frame
            dispose();

            // Show previous frame if it exists
            if (previousFrame != null) {
                previousFrame.setVisible(true);
            } else {
            }
        }
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
        }
    }


    public void setController(AddExpenseController controller) {
        this.controller = controller;
    }

    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String expenseName = nameField.getText().trim();
                String amountText = amountField.getText().trim();
                String description = descField.getText().trim();
                String category = (String) comboBox.getSelectedItem();
                float amount;

                // Validation
                if (expenseName.isEmpty()) {
                    showError("Please enter an expense name");
                    return;
                }

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
                ArrayList<User> participants = new ArrayList<>();
                for (JCheckBox checkbox : participantCheckboxes) {
                    if (checkbox.isSelected()) {
                        User user = (User) checkbox.getClientProperty("user");
                        if (user != null) {
                            participants.add(user);
                        }
                    }
                }
                if (participants.isEmpty()) {
                    showError("Please select at least one participant");
                    return;
                }
                if (currentGroup == null) {
                    showError("Please select a group");
                    return;
                }
                controller.execute(expenseName, description, amount, category,
                        participants, currentGroup.getId());

            } catch (Exception ex) {
                showError("Error: " + ex.getMessage());
            }
        }
    }

    public void showSuccess(String message) {
        int choice = JOptionPane.showConfirmDialog(this,
                message + "\n\nWould you like to add another expense?",
                "Success",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            clearForm();
        } else {
            // Go back to previous frame
            dispose();
            if (previousFrame != null) {
                previousFrame.setVisible(true);
            }
        }
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void clearForm() {
        nameField.setText("");
        amountField.setText("");
        descField.setText("");
        comboBox.setSelectedIndex(0);
        nameField.requestFocus();
    }

    public static void main(String[] args) {
        // Example usage
        JFrame dummyPreviousFrame = new JFrame("Previous Frame");
        dummyPreviousFrame.setSize(300, 200);
        dummyPreviousFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dummyPreviousFrame.setVisible(true);

        AddExpenseFrame view = new AddExpenseFrame(dummyPreviousFrame);
        view.setVisible(true);
    }
}