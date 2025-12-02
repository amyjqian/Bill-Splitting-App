package view;

import interface_adapter.view_history.MyGroupViewModel;
import interface_adapter.view_history.ViewHistoryController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MyGroupFrame extends JFrame {

    private final MyGroupViewModel myGroupViewModel;
    private final ViewHistoryController controller;

    JTextField groupField = new JTextField("group14", 15);
    JLabel errorLabel = new JLabel("", SwingConstants.CENTER);

    JTable expenseTable;
    DefaultTableModel tableModel;

    public MyGroupFrame(MyGroupViewModel myGroupViewModel, ViewHistoryController viewHistoryController) {
        this.myGroupViewModel = myGroupViewModel;
        this.controller = viewHistoryController;

        setTitle("My Group");
        setLayout(new BorderLayout(10, 10));

        // top section: groupName, Back, Refresh
        // JLabel titleLabel = new JLabel("My Group", SwingConstants.CENTER);
        JLabel groupLabel = new JLabel("Group:");
        groupField.setEditable(false);

        JButton backButton = new JButton("Back");
        JButton refreshHistoryButton = new JButton("Refresh History");

        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        groupPanel.add(groupLabel);
        groupPanel.add(groupField);
        groupPanel.add(refreshHistoryButton);
        groupPanel.add(backButton);

        errorLabel.setForeground(Color.RED);

        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(groupPanel);
        northPanel.add(errorLabel);

        add(northPanel, BorderLayout.NORTH);

        // table section
        tableModel = new DefaultTableModel(new String[]{"Description", "Amount", "Date"}, 0);
        expenseTable = new JTable(tableModel);
        add(new JScrollPane(expenseTable), BorderLayout.CENTER);

        // bottom buttons
        JButton newExpenseButton = new JButton("New Expense");
        JButton settleUpButton = new JButton("Settle Up");

        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        footerPanel.add(newExpenseButton);
        footerPanel.add(settleUpButton);

        add(footerPanel, BorderLayout.SOUTH);

        // event listeners
        refreshHistoryButton.addActionListener(e -> {
            this.controller.execute(groupField.getText());
        });

        // ViewModel listener
        myGroupViewModel.addPropertyChangeListener(evt -> {
            SwingUtilities.invokeLater(() -> {
                updateErrorLabel();
                updateExpenseTable();
            });
        });

        // button link

        newExpenseButton.addActionListener(e -> {
            AddExpenseFrame expenseFrame = new AddExpenseFrame();
            expenseFrame.setVisible(true);
            this.dispose();    // close MyGroupFrame if that’s your pattern
        });

        settleUpButton.addActionListener(e -> {
            SettleUpPanel settlePanel = new SettleUpPanel();
            JFrame settleFrame = new JFrame("Settle Up");
            settleFrame.setVisible(true);
            this.dispose();
        });

        backButton.addActionListener(e -> {
            GroupViewFrame gv = new GroupViewFrame();
            gv.setVisible(true);
            this.dispose();
        });

    }

    // helpers
    private void updateErrorLabel() {
        errorLabel.setText(myGroupViewModel.getMessage());
    }

    private void updateExpenseTable() {
        // Clear table
        tableModel.setRowCount(0);

        if (myGroupViewModel.getExpenses().isEmpty()) {
            errorLabel.setText("No history found.");
            return;
        }

        myGroupViewModel.getExpenses().forEach(exp -> {
            Object[] row = {
                    exp.get(0),   // description
                    exp.get(1),   // amount
                    exp.get(2)    // date
            };
            tableModel.addRow(row);
        });
    }

    public String getViewName() {
        return "groupView";
    }
}
