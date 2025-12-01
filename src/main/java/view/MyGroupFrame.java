package view;

import javax.swing.*;
import java.awt.*;

public class MyGroupFrame extends JFrame {

    JTextField groupField = new JTextField("group14", 15);
    JTable expenseTable;

        setTitle("My Group");
        setLayout(new BorderLayout(10, 10));


        JPanel groupPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        groupPanel.add(groupLabel);
        groupPanel.add(groupField);
        groupPanel.add(backButton);



        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        footerPanel.add(newExpenseButton);
        footerPanel.add(settleUpButton);
        add(footerPanel, BorderLayout.SOUTH);
    }

    public String getViewName() {
        return "groupView";
    }
}