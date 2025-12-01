package view;

import data_access.InMemoryGroupDataAccessObject;
import entities.Group;
import entities.User;
import interface_adapter.join_group.JoinGroupController;
import use_case.join_group.JoinGroupPresenter;
import interface_adapter.join_group.JoinGroupViewModel;
import use_case.join_group.JoinGroupInputBoundary;
import use_case.join_group.JoinGroupInteractor;
import use_case.join_group.JoinGroupOutputBoundary;

import javax.swing.*;

public class JoinGroupFrame extends JFrame {
    int CODE_LENGTH = 15;
    private JoinGroupViewModel joinGroupViewModel;
    private JoinGroupOutputBoundary joinGroupOutputBoundary;
    private JoinGroupInputBoundary joinGroupInteractor;
    private JoinGroupController joinGroupController;

    public JoinGroupFrame(JoinGroupViewModel joinGroupViewModel) {
        this.joinGroupViewModel = joinGroupViewModel;
        this.joinGroupOutputBoundary = new JoinGroupPresenter(this.joinGroupViewModel);
        this.joinGroupInteractor =
                new JoinGroupInteractor(new InMemoryGroupDataAccessObject(),
                        this.joinGroupOutputBoundary);
        this.joinGroupController = new JoinGroupController(this.joinGroupInteractor);
        initializeJoinGroupUI();
    }

    private void initializeJoinGroupUI(){
        setTitle("Group View (2.1)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        JLabel code = new JLabel("Enter group ID: ");
        JTextField codeField = new JTextField(CODE_LENGTH);
        JButton submit = new JButton("Submit");

        submit.addActionListener(
                evt -> {
                    joinGroupViewModel.setGroupID(Integer.parseInt(codeField.getText()));
                    joinGroupViewModel.setUserID(75); //placeholder
                    if (evt.getSource().equals(submit)) {
                        //check the db to see if a group with that group ID is found
                        final int groupID = joinGroupViewModel.getGroupID();
                        final int userID = joinGroupViewModel.getUserID();
                        joinGroupController.execute(groupID, userID);
                    }
                }
        );

        JPanel panel = new JPanel();
        panel.add(code);
        panel.add(codeField);
        panel.add(submit);
        this.add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new JoinGroupFrame(new JoinGroupViewModel()).setVisible(true);
        });
    }
}
