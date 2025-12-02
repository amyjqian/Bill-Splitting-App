package view;

import interface_adapter.view_history.MyGroupViewModel;
import interface_adapter.view_history.ViewHistoryController;
import interface_adapters.displayData.DisplayDataController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MyGroupFrame extends JFrame {

    private final MyGroupViewModel myGroupViewModel;
    private final DisplayDataController displayDataController;
    private final view.DisplayDataView chartView;

    JTextField groupField = new JTextField("group14", 15);
    JLabel errorLabel = new JLabel("", SwingConstants.CENTER);

    JTable expenseTable;
    DefaultTableModel tableModel;

    public MyGroupFrame(MyGroupViewModel myGroupViewModel,
                        ViewHistoryController viewHistoryController,
                        DisplayDataController displayDataController,
                        view.DisplayDataView chartView) {
        this.myGroupViewModel = myGroupViewModel;
        this.displayDataController = displayDataController;
        this.chartView = chartView;

        setTitle("My Group");
        setLayout(new BorderLayout(10, 10));

        // top section: groupName, Back, Refresh
        // JLabel titleLabel = new JLabel("My Group", SwingConstants.CENTER);
        JLabel groupLabel = new JLabel("Group:");
        groupField.setEditable(false);

        JButton backButton = new JButton("Back");
        JButton refreshHistoryButton = new JButton("Refresh History");

        JButton viewChartButton = new JButton("View Chart");

        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        groupPanel.add(groupLabel);
        groupPanel.add(groupField);
        groupPanel.add(refreshHistoryButton);
        groupPanel.add(backButton);
        groupPanel.add(viewChartButton);


        errorLabel.setForeground(Color.RED);

        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(groupPanel, BorderLayout.CENTER);
        northPanel.add(errorLabel, BorderLayout.SOUTH);


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
            viewHistoryController.execute(groupField.getText());
        });

        viewChartButton.addActionListener(e -> {
            displayDataController.load(); // run the use case

            // NOW show the view in a new window
            JFrame chartFrame = new JFrame("Expenses Pie Chart");
            chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            chartFrame.add(chartView);  // IMPORTANT!
            chartFrame.pack();
            chartFrame.setLocationRelativeTo(this);
            chartFrame.setVisible(true);
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
            view.ExpenseFrame expenseFrame = new view.ExpenseFrame();
            expenseFrame.setVisible(true);
            this.dispose();    // close MyGroupFrame if that’s your pattern
        });

        settleUpButton.addActionListener(e -> {
            view.SettleUpPanel settlePanel = new view.SettleUpPanel();
            JFrame settleFrame = new JFrame("Settle Up");
            settleFrame.setVisible(true);
            this.dispose();
        });

        backButton.addActionListener(e -> {
            view.GroupViewFrame gv = new view.GroupViewFrame();
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
                    exp.getDescription(),
                    exp.getAmount(),
                    exp.getDate()
            };
            tableModel.addRow(row);
        });
    }

    public String getViewName() {
        return "groupView";
    }
}