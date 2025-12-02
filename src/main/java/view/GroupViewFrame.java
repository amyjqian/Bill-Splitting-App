package view;

import data_access.SplitwiseDataAccess;
import data_access.ViewHistoryDataAccessObject;
import interface_adapter.create_group.CreateGroupController;
import interface_adapter.create_group.CreateGroupViewModel;
import interface_adapter.group_view.GroupViewController;
import interface_adapter.view_history.MyGroupViewModel;
import interface_adapter.view_history.ViewHistoryController;
import interface_adapter.view_history.ViewHistoryPresenter;
import use_case.create_group.CreateGroupDataAccessInterface;
import use_case.create_group.CreateGroupInteractor;
import use_case.create_group.CreateGroupOutputBoundary;
import use_case.create_group.CreateGroupPresenter;
import use_case.view_history.ViewHistoryInteractor;
import use_case.view_history.ViewHistoryOutputBoundary;

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
        setLayout(new BorderLayout());

        JButton createButton = new JButton("Create Group");
        JButton viewButton = new JButton("View My Group");

        String groupID = "group1";
        createButton.addActionListener(e -> controller.onCreateGroupClicked());
        viewButton.addActionListener(e -> controller.onViewMyGroupClicked(groupID));

        JPanel panel = new JPanel();
        panel.add(createButton);
        panel.add(viewButton);
        this.add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            CreateGroupDataAccessInterface createGroupDAO = new SplitwiseDataAccess();
            ViewHistoryDataAccessObject viewHistoryDAO = new ViewHistoryDataAccessObject("YOUR_API_KEY");

            CreateGroupViewModel createGroupVM = new CreateGroupViewModel();
            MyGroupViewModel myGroupVM = new MyGroupViewModel();

            CreateGroupOutputBoundary createPresenter = new CreateGroupPresenter(createGroupVM);
            ViewHistoryOutputBoundary viewPresenter = new ViewHistoryPresenter(myGroupVM);

            CreateGroupInteractor createInteractor = new CreateGroupInteractor(createGroupDAO, createPresenter);
            ViewHistoryInteractor viewHistoryInteractor = new ViewHistoryInteractor(viewHistoryDAO, viewPresenter);

            CreateGroupController createController = new CreateGroupController(createInteractor);
            ViewHistoryController viewHistoryController = new ViewHistoryController(viewHistoryInteractor);

            GroupViewController groupViewController = new GroupViewController(
                    createController,
                    viewHistoryController
            );
            new GroupViewFrame(groupViewController).setVisible(true);
        });
    }
    }
