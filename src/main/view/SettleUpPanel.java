package main.view;

import main.entities.Group;
import main.usecase.*;

import javax.swing.*;
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
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Settle Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Suggested Payments", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        StringBuilder stringBuilder = new StringBuilder();

        String settlement = stringBuilder.toString();
        JLabel settlementLabel = new JLabel(settlement);
        JPanel textPanel = new JPanel();
        textPanel.add(settlementLabel);
        add(textPanel, BorderLayout.CENTER);

        JButton backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 20));
        JButton paidButton = new JButton("Paid");
        paidButton.setPreferredSize(new Dimension(100, 20));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(paidButton);
        paidButton.setVisible(!presenter.isAllSettled());
        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> dispose());
        paidButton.addActionListener(e -> {controller.onPaidPressed(group);
                paidButton.setVisible(false);});
    }

    //public static void main(String[] args) {
        //SwingUtilities.invokeLater(() -> new SettleUpPanel().setVisible(true));
    //}

}