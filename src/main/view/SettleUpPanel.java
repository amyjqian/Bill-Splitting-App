package main.view;

import main.entities.Expense;
import main.entities.Group;
import main.entities.User;
import main.usecase.*;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;

public class SettleUpPanel extends JFrame {

    private final SettleUpPresenter presenter;
    private final SettleUpController controller;

    private JLabel settlementLabel;
    private JButton settleUpButton;
    private JButton backButton;
    private JButton paidButton;

    private Group group;

    public SettleUpPanel(Group group) {
        this.group = group;
        presenter = new SettleUpPresenter();
        SettlementCalculator calculator = new SettleUpCalculator();
        SettleUpInputBoundary interactor = new SettleUpInteractor(calculator, presenter);
        controller = new SettleUpController(interactor);
        initializeUI(group);
    }

    private void initializeUI(Group group) {
        setTitle("Settle Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Suggested Payments", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        controller.onSettleUpPressed(group);
        //this.settlementLabel = new JLabel(presenter.getMessage());

        //StringBuilder stringBuilder = new StringBuilder();
        //String settlement = stringBuilder.toString();
        //this.settlementLabel = new JLabel(settlement);

//        JPanel textPanel = new JPanel();
//        textPanel.add(settlementLabel);
//        add(textPanel, BorderLayout.CENTER);

        JTextPane area = new JTextPane();
        area.setEditable(false);
        area.setText(presenter.getMessage());
        StyledDocument doc = area.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        add(area, BorderLayout.CENTER);

        this.backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(120, 20));
        this.paidButton = new JButton("Mark as Settled");
        paidButton.setPreferredSize(new Dimension(120, 20));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(paidButton);
        paidButton.setVisible(!presenter.isAllSettled());

//        this.settleUpButton = new JButton("Settle Up");
//        settleUpButton.setPreferredSize(new Dimension(100, 20));
//        buttonPanel.add(settleUpButton);

        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> dispose());
        paidButton.addActionListener(e -> {controller.onPaidPressed(group);
                area.setText(presenter.getMessage());
                paidButton.setVisible(false);});
//        settleUpButton.addActionListener(e -> {controller.onSettleUpPressed(group);
//            settlementLabel.setText(presenter.getMessage());});
    }

    public static void main(String[] args) {
        User user1 = new User("123", "A", "skdfjhsfkj");
        User user2 = new User("12345", "B", "skdfjj");
        User user3 = new User("sdfmhb", "C", "sdfbqwe");

        Expense exp1 = new Expense("1", 6, "exp1", user1);
        Expense exp2 = new Expense("2", 9, "exp2", user2);
        Expense exp3 = new Expense("3", 99, "exp3", user3);
        Expense exp4 = new Expense("4", 9, "exp4", user1);
        Expense exp5 = new Expense("5", 6, "exp5", user2);

        exp1.addParticipant(user1);
        exp1.addParticipant(user2);
        exp1.addParticipant(user3);
        exp2.addParticipant(user1);
        exp2.addParticipant(user2);
        exp2.addParticipant(user3);
        exp3.addParticipant(user1);
        exp3.addParticipant(user2);
        exp3.addParticipant(user3);
        exp4.addParticipant(user1);
        exp4.addParticipant(user2);
        exp4.addParticipant(user3);
        exp5.addParticipant(user1);
        exp5.addParticipant(user2);
        exp5.addParticipant(user3);

        Group group = new Group("skdfjn","group");
        group.addMember(user1);
        group.addMember(user2);
        group.addMember(user3);
        group.addExpense(exp1);
        group.addExpense(exp2);
        group.addExpense(exp3);
        group.addExpense(exp4);
        group.addExpense(exp5);

        exp5.setSettled();

        SwingUtilities.invokeLater(() -> new SettleUpPanel(group).setVisible(true));
    }

}