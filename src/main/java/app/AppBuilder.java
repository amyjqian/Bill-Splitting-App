/*
package app;

import data_access.ViewHistoryDataAccessObject;
import interface_adapter.view_history.MyGroupViewModel;
import interface_adapter.view_history.ViewHistoryPresenter;
import interface_adapter.view_history.ViewHistoryController;
import use_case.view_history.ViewHistoryInteractor;
import io.github.cdimascio.dotenv.Dotenv;
import view.MyGroupFrame;

import javax.swing.*;

public class AppBuilder {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyGroupViewModel vm = new MyGroupViewModel("groupView");
            Dotenv dotenv = Dotenv.load();
            String apiKey = dotenv.get("SPLITWISE_API_KEY");

            ViewHistoryPresenter presenter = new ViewHistoryPresenter(vm);
            ViewHistoryDataAccessObject dao = new ViewHistoryDataAccessObject(apiKey);
            ViewHistoryInteractor interactor = new ViewHistoryInteractor(dao, presenter);
            ViewHistoryController controller = new ViewHistoryController(interactor);

            MyGroupFrame frame = new MyGroupFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setVisible(true);
        });
    }
}
*/
