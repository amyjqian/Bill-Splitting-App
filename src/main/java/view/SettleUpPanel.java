package view;

import data_access.SettleUpDataAccessObject;
import usecase.SettleUp.*;

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
    private JButton markButton;

    private final Long groupId;

    public SettleUpPanel(Long groupId) {

        this.groupId = groupId;

        String apiKey = System.getenv("SPLITWISE_API_KEY");

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
        this.markButton = new JButton("Mark as Settled");
        markButton.setPreferredSize(new Dimension(120, 20));
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);
        buttonPanel.add(markButton);
        markButton.setVisible(!presenter.isAllSettled());

        add(buttonPanel, BorderLayout.SOUTH);

        backButton.addActionListener(e -> dispose());
        markButton.addActionListener(e -> {controller.onMarkPressed(groupId);
                area.setText(presenter.getMessage());
                markButton.setVisible(false);});
    }
}