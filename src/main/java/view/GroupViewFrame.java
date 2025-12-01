package view;

import javax.swing.*;

public class GroupViewFrame extends JFrame {
    public GroupViewFrame() {
        initializeUI();
    }
    private void initializeUI(){
        setTitle("Group View (2)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);

        JButton join = new JButton("Join Group");
        JButton create = new JButton("Create Group");
        JButton view = new JButton("View My Group");

        JPanel panel = new JPanel();
        panel.add(join);
        panel.add(create);
        panel.add(view);
        this.add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()->{
            new GroupViewFrame().setVisible(true);
        });
    }
    }
