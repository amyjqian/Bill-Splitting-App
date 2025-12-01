package main.view;

import main.data_access.SettleUpDataAccessObject;
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

    private final Long groupId;

    public SettleUpPanel(Long groupId, String apiKey) {

        this.groupId = groupId;
        presenter = new SettleUpPresenter();
        SettleUpDataAccessInterface dataAccess = new SettleUpDataAccessObject(apiKey);
        SettlementCalculator calculator = new SettleUpCalculator();
        SettleUpInputBoundary interactor = new SettleUpInteractor(dataAccess, calculator, presenter);
        controller = new SettleUpController(interactor);
        initializeUI(groupId);
    }

    private void initializeUI(Long groupId) {
        setTitle("Settle Up");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(350, 200);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel titleLabel = new JLabel("Suggested Payments", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        controller.onSettleUpPressed(groupId);

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

        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> dispose());
        paidButton.addActionListener(e -> {controller.onPaidPressed(groupId);
                area.setText(presenter.getMessage());
                paidButton.setVisible(false);});
    }

    public static void main(String[] args) {
        Long groupId = 90437991L;
        String apiKey = "smmaCgUHfNZ3KRPzuny1KxRqLGMYoPzlHj6ABJwA";

        SwingUtilities.invokeLater(() -> new SettleUpPanel(groupId, apiKey).setVisible(true));
    }
}