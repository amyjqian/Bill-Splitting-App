package view;

import interface_adapter.create_group.CreateGroupController;
import interface_adapter.create_group.CreateGroupState;
import interface_adapter.create_group.CreateGroupViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateGroupFrame extends JFrame {

    private final CreateGroupViewModel createGroupViewModel;

    public CreateGroupFrame(CreateGroupViewModel createGroupViewModel) {
        this.createGroupViewModel = createGroupViewModel;
        initializeCreateGroupUI();
    }

    private void initializeCreateGroupUI() {
        setTitle("Create Group (2.2)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);

        JLabel newGroupLabel = new JLabel("New Group: ", JLabel.LEFT);

        JLabel groupNameLabel = new JLabel("Set Group Name: ", JLabel.LEFT);
        JTextField nameField = new JTextField(5);
        JLabel groupID = new JLabel("Your Group ID is: ", JLabel.LEFT);

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(submitButton)) {
                            final CreateGroupState currentState = createGroupViewModel.getState();

                            CreateGroupController.execute(
                                    //tbd
                            );
                        }
                    }
                }
        );

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
}
