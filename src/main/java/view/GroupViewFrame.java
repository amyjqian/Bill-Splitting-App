package view;

import interface_adapter.group_view.GroupViewController;
import javax.swing.*;
import java.awt.*;

public class GroupViewFrame extends JFrame {
    private final GroupViewController controller;

    public GroupViewFrame(GroupViewController controller) {
        this.controller = controller;
        initializeUI();
    }
    private void initializeUI(){
        setTitle("Group View (2)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);

        JButton joinButton = new JButton("Join Group");
        JButton createButton = new JButton("Create Group");
        JButton viewButton = new JButton("View My Group");

        joinButton.addActionListener(e -> controller.onJoinGroupClicked());
        createButton.addActionListener(e -> controller.onCreateGroupClicked());
        viewButton.addActionListener(e -> controller.onViewMyGroupClicked());

        JPanel panel = new JPanel();
        panel.add(joinButton);
        panel.add(createButton);
        panel.add(viewButton);
        this.add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new GroupViewFrame().setVisible(true);
        });
    }
    }
