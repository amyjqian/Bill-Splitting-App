package view;
import interface_adapter.auto_save.AutoSaveController;
import interface_adapter.auto_save.AutoSaveStatusDisplay;
import use_case.auto_save.AutoSaveInteractor;
import data_access.AutoSaveDataAccessObject;
import data_access.FileAutoSaveDataAccessObject;

import javax.swing.*;
import java.awt.*;

public class MyGroupFrame extends JFrame implements AutoSaveStatusDisplay {

    JLabel titleLabel = new JLabel("My Group", SwingConstants.CENTER);
    JLabel groupLabel = new JLabel("Group:");
    JTextField groupField = new JTextField("group14", 15);
    JButton backButton = new JButton("Back");
    JTable expenseTable;
    JButton newExpenseButton = new JButton("New Expense");
    JButton settleUpButton = new JButton("Settle Up");

    private AutoSaveController autoSaveController;
    private JLabel autosaveLabel;

    public MyGroupFrame() {
        setTitle("My Group");
        setLayout(new BorderLayout(10, 10));

        AutoSaveDataAccessObject dao = new FileAutoSaveDataAccessObject() {
            @Override
            public void safeDraft(String content) {

            }
        };
        AutoSaveInteractor interactor = new AutoSaveInteractor(dao, responseModel -> {
            if (responseModel.isSuccess()) {
                showAutoSaveStatus("All changes saved.");
            } else {
                showAutoSaveStatus("Failed to save.");
            }
        });
        autoSaveController = new AutoSaveController(interactor);

        // Autosave label
        autosaveLabel = new JLabel("All changes saved.", SwingConstants.CENTER);
        autosaveLabel.setForeground(Color.GREEN);
        add(autosaveLabel, BorderLayout.NORTH);

        JPanel titleRow = new JPanel(new BorderLayout());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleRow.add(titleLabel, BorderLayout.CENTER);

        // group name + back buttone
        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        groupPanel.add(groupLabel);
        groupField.setEditable(false);
        groupPanel.add(groupField);
        groupPanel.add(backButton);
        add(groupPanel,BorderLayout.BEFORE_FIRST_LINE);

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
    }

    @Override
    public void showAutoSaveStatus(String message) {
        if (autosaveLabel != null) {
            autosaveLabel.setText(message);
        }
    }

    public String getViewName() {
        return "groupView";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyGroupFrame frame = new MyGroupFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setVisible(true);
        });
    }
}