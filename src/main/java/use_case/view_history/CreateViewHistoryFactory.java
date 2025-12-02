package use_case.view_history;

import data_access.DisplayDataAccessObject;
import data_access.ViewHistoryDataAccessObject;
import interface_adapter.view_history.MyGroupViewModel;
import interface_adapter.view_history.ViewHistoryController;
import interface_adapter.view_history.ViewHistoryPresenter;
import interface_adapters.displayData.DisplayDataController;
import interface_adapters.displayData.DisplayDataPresenter;
import interface_adapters.displayData.DisplayDataViewModel;
import use_case.DisplayData.DisplayDataInteractor;
import use_case.DisplayData.ExpenseDataAccessInterface;
import view.DisplayDataView;
import view.MyGroupFrame;

import javax.swing.*;

public class CreateViewHistoryFactory {

    public static MyGroupFrame createFrame(String apiKey) {
        // Create the ViewModel
        MyGroupViewModel viewModel = new MyGroupViewModel("groupView");

        // Create the Presenter (output boundary)
        ViewHistoryOutputBoundary presenter = new ViewHistoryPresenter(viewModel);

        // Create the Interactor (input boundary)
        ViewHistoryInteractor interactor =
                new ViewHistoryInteractor(new ViewHistoryDataAccessObject(apiKey), presenter);

        // Create the Controller
        ViewHistoryController controller = new ViewHistoryController(interactor);

        DisplayDataViewModel chartVM = new DisplayDataViewModel();
        DisplayDataView chartView = new DisplayDataView();
        DisplayDataPresenter displayPresenter =
                new DisplayDataPresenter(chartVM, chartView);

        String groupId = "90437991";
        ExpenseDataAccessInterface dataAccess =
                new DisplayDataAccessObject(apiKey, groupId);

        DisplayDataInteractor displayInteractor =
                new DisplayDataInteractor(dataAccess, displayPresenter);

        DisplayDataController displayController =
                new DisplayDataController(displayInteractor);

        chartView.setController(displayController);

        // Return the UI frame
        return new MyGroupFrame(viewModel, controller, displayController, chartView);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // You may load your API key differently depending on how your program is set up.
            String apiKey = System.getenv("SPLITWISE_API_KEY");

            MyGroupFrame frame = CreateViewHistoryFactory.createFrame(apiKey);
            frame.setVisible(true);
        });
    }
}
