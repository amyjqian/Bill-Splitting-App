package view;

import data_access.SplitwiseDataAccess;
import interface_adapter.create_group.CreateGroupController;
import interface_adapter.create_group.CreateGroupViewModel;
import use_case.create_group.CreateGroupInputBoundary;
import use_case.create_group.CreateGroupInteractor;
import use_case.create_group.CreateGroupOutputBoundary;
import use_case.create_group.CreateGroupPresenter;

import javax.swing.*;
import java.awt.*;

public class CreateGroupFrame extends JFrame{
    private final CreateGroupViewModel createGroupViewModel;
    private final CreateGroupController createGroupController;


    public CreateGroupFrame(CreateGroupViewModel createGroupViewModel, CreateGroupController controller) {
        this.createGroupViewModel = createGroupViewModel;
        this.createGroupController = controller;
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
        JLabel groupID = new JLabel("Your Invite Link is: ", JLabel.LEFT);
        JLabel inviteLabel= new JLabel("placeholder");

        JButton submitButton = new JButton("Submit");
        JButton switchViewButton = new JButton("Ok");

        submitButton.addActionListener(
                evt -> {
                    createGroupViewModel.setGroupName(nameField.getText());
                    if (evt.getSource().equals(submitButton)) {
                        final String groupName = createGroupViewModel.getGroupName();
                        createGroupController.execute(groupName);
                        inviteLabel.setText(String.valueOf(createGroupViewModel.getInviteLink()));
                    }
                    else if (evt.getSource().equals(switchViewButton)) {
                        this.dispose();
                        SwingUtilities.invokeLater(() -> {
                            // create an instance of the MyGroup frame which should lead to my group view (2.3)
                        });
                    }
                }
        );

        // Simple panel with components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 1, 10, 10));

        panel.add(newGroupLabel);
        panel.add(groupNameLabel);
        panel.add(nameField);
        panel.add(submitButton);
        panel.add(groupID);
        panel.add(inviteLabel);
        panel.add(switchViewButton);
        this.add(panel);
    }
    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
//    public void actionPerformed(ActionEvent evt) {
//        System.out.println("Click " + evt.getActionCommand());
//    }
    }

