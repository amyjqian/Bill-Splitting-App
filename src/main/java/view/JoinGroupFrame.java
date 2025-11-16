package view;

import javax.swing.*;

public class JoinGroupFrame extends JFrame {
    int CODE_LENGTH = 15;
    public JoinGroupFrame() {
        initializeUI();
    }
    private void initializeUI(){
        setTitle("Group View (2.1)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        JLabel code = new JLabel("Enter code: ");
        JTextField codeField = new JTextField(CODE_LENGTH);
        JButton submit = new JButton("Submit");

        JPanel panel = new JPanel();
        panel.add(code);
        panel.add(codeField);
        panel.add(submit);
        this.add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new JoinGroupFrame().setVisible(true);
        });
    }
}
