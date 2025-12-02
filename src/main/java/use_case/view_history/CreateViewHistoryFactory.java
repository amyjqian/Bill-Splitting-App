package use_case.view_history;

import data_access.ViewHistoryDataAccessObject;
import interface_adapter.view_history.MyGroupViewModel;
import interface_adapter.view_history.ViewHistoryController;
import interface_adapter.view_history.ViewHistoryPresenter;
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

        // Return the UI frame
        return new MyGroupFrame(viewModel, controller);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            // You may load your API key differently depending on how your program is set up.
//            String apiKey = System.getenv("SPLITWISE_API_KEY");
//
//            MyGroupFrame frame = CreateViewHistoryFactory.createFrame(apiKey);
//            frame.setVisible(true);
//        });
//    }
}
