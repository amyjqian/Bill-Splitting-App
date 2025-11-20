package view;

import data_access.ViewHistoryDataAccessObject;
import interface_adapter.view_history.MyGroupViewModel;
import interface_adapter.view_history.ViewHistoryPresenter;
import use_case.view_history.ViewHistoryDataAccessInterface;
import use_case.view_history.ViewHistoryInputData;
import interface_adapter.view_history.ViewHistoryController;
import use_case.view_history.ViewHistoryInteractor;

import javax.swing.*;
import java.awt.*;

public class MyGroupFrame extends JFrame {

    private final ViewHistoryController viewHistoryController;

    JLabel titleLabel = new JLabel("My Group", SwingConstants.CENTER);
    JLabel groupLabel = new JLabel("Group:");
    JTextField groupField = new JTextField("group14", 15);
    JButton backButton = new JButton("Back");
    JButton refreshHistoryButton = new JButton("Refresh History");
    JLabel errorLabel = new JLabel("", SwingConstants.CENTER);
    JTable expenseTable;
    JButton newExpenseButton = new JButton("New Expense");
    JButton settleUpButton = new JButton("Settle Up");

    public MyGroupFrame(MyGroupViewModel myGroupViewModel, ViewHistoryController viewHistoryController) {
        this.viewHistoryController = viewHistoryController;
        setTitle("My Group");
        setLayout(new BorderLayout(10, 10));

        JPanel titleRow = new JPanel(new BorderLayout());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleRow.add(titleLabel, BorderLayout.CENTER);

        // group name + back button
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        groupPanel.add(groupLabel);
        groupField.setEditable(false);
        groupPanel.add(groupField);
        groupPanel.add(backButton);
        groupPanel.add(refreshHistoryButton);
        add(groupPanel,BorderLayout.BEFORE_FIRST_LINE);

        // errorLabel
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(groupPanel);

        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        northPanel.add(errorLabel);

        add(northPanel, BorderLayout.NORTH);

        // table
        expenseTable = new JTable(new Object[][]{}, new String[]{"Expenses", "Amount", "Date Added"});
        JScrollPane scrollPane = new JScrollPane(expenseTable);
        expenseTable.setFillsViewportHeight(true);
        add(scrollPane, BorderLayout.CENTER);

        // lower buttons
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        newExpenseButton.setPreferredSize(new Dimension(150, 35));
        settleUpButton.setPreferredSize(new Dimension(150, 35));
        footerPanel.add(newExpenseButton);
        footerPanel.add(settleUpButton);
        add(footerPanel, BorderLayout.SOUTH);

        refreshHistoryButton.addActionListener(e -> {
            viewHistoryController.execute(groupField.getText());
        });

        // show error code
        myGroupViewModel.addPropertyChangeListener(evt -> {
            var state = myGroupViewModel.getState();
            errorLabel.setText(state.getMessage());
        });
    }

    public String getViewName() {
        return "groupView";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyGroupViewModel vm = new MyGroupViewModel("groupView");

            // you must construct interactor & presenter too
            ViewHistoryPresenter presenter = new ViewHistoryPresenter(vm);
            ViewHistoryDataAccessInterface dao = new ViewHistoryDataAccessObject("0"); // need to update api
            ViewHistoryInteractor interactor = new ViewHistoryInteractor(dao, presenter);
            ViewHistoryController controller = new ViewHistoryController(interactor);

            MyGroupFrame frame = new MyGroupFrame(vm, controller);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setVisible(true);
        });
    }
}
