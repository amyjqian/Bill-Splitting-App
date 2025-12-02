package view;

import use_case.create_group.CreateGroupFactory;
import use_case.view_history.CreateViewHistoryFactory;

import javax.swing.*;

public class GroupViewFrame extends JFrame {
    public GroupViewFrame() {
        initializeUI();
    }
    private void initializeUI(){
        setTitle("Group View (2)");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 100);

        JButton create = new JButton("Create Group");
        JButton view = new JButton("View My Group");

        create.addActionListener(e -> {
            CreateGroupFrame frame = CreateGroupFactory.createFrame();
            frame.setVisible(true);
            this.dispose();
        });

        view.addActionListener(e -> {
            String apiKey = System.getenv("SPLITWISE_API_KEY");
            MyGroupFrame frame = CreateViewHistoryFactory.createFrame(apiKey);
            frame.setVisible(true);
            this.dispose();
        });

        JPanel panel = new JPanel();
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
