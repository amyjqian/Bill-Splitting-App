package view;

import javax.swing.*;
import java.awt.*;

public class CreateGroupFrame extends JFrame {

    public CreateGroupFrame() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Create Group (2.2)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

            JLabel newGroupLabel = new JLabel("New Group: ", JLabel.LEFT);

        JLabel groupNameLabel = new JLabel("Set Group Name: ", JLabel.LEFT);
        JTextField nameField = new JTextField(5);
        JLabel groupID = new  JLabel("Your Group ID is: ", JLabel.LEFT);

        JButton submitButton = new JButton("Submit");

        // Simple panel with components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 10, 10));

        panel.add(newGroupLabel);
        panel.add(groupNameLabel);
        panel.add(nameField);
        panel.add(nameField);
        panel.add(groupID);
        panel.add(submitButton);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CreateGroupFrame().setVisible(true);
        });
    }
}
