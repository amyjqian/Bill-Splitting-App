package app;

import data_access.DisplayDataAccessObject;
import data_access.ViewHistoryDataAccessObject;
import interface_adapter.view_history.MyGroupViewModel;
import interface_adapter.view_history.ViewHistoryPresenter;
import interface_adapter.view_history.ViewHistoryController;
import interface_adapters.displayData.DisplayDataController;
import interface_adapters.displayData.DisplayDataViewModel;
import interface_adapters.displayData.DisplayDataPresenter;
import use_case.DisplayData.DisplayDataInteractor;
import use_case.DisplayData.ExpenseDataAccessInterface;
import use_case.view_history.ViewHistoryInteractor;

import io.github.cdimascio.dotenv.Dotenv;
import view.DisplayDataView;
import view.MyGroupFrame;

import javax.swing.*;

public class AppBuilder {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MyGroupViewModel vm = new MyGroupViewModel("groupView");
            Dotenv dotenv = Dotenv.load();
            String apiKey = dotenv.get("SPLITWISE_API_KEY");
            String groupId = "90437991";

            ViewHistoryPresenter presenter = new ViewHistoryPresenter(vm);
            ViewHistoryDataAccessObject dao = new ViewHistoryDataAccessObject(apiKey);
            ViewHistoryInteractor interactor = new ViewHistoryInteractor(dao, presenter);
            ViewHistoryController controller = new ViewHistoryController(interactor);

            DisplayDataViewModel chartVM = new DisplayDataViewModel();
            DisplayDataView chartView = new DisplayDataView();

            DisplayDataPresenter displayPresenter =
                    new DisplayDataPresenter(chartVM, chartView);

            ExpenseDataAccessInterface dataAccess =
                    new DisplayDataAccessObject(apiKey,groupId);

            DisplayDataInteractor displayInteractor =
                    new DisplayDataInteractor(dataAccess, displayPresenter);

            DisplayDataController displayController =
                    new DisplayDataController(displayInteractor);

            chartView.setController(displayController);

            MyGroupFrame frame = new MyGroupFrame(vm, controller, displayController, chartView);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}