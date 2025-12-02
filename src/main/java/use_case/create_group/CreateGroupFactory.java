package use_case.create_group;

import data_access.SplitwiseDataAccess;
import interface_adapter.create_group.CreateGroupController;
import interface_adapter.create_group.CreateGroupViewModel;
import view.CreateGroupFrame;

import javax.swing.*;

public class CreateGroupFactory {

    public static CreateGroupFrame createFrame() {
        // Create the ViewModel
        CreateGroupViewModel viewModel = new CreateGroupViewModel();

        // Create the Presenter (output boundary)
        CreateGroupOutputBoundary presenter = new CreateGroupPresenter(viewModel);

        // Create the Interactor (input boundary)
        CreateGroupInteractor interactor = new CreateGroupInteractor(new SplitwiseDataAccess(), presenter);

        // Create the Controller
        CreateGroupController controller = new CreateGroupController(interactor);

        return new CreateGroupFrame(viewModel, controller);
    }

//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            CreateGroupFrame frame = CreateGroupFactory.createFrame();
//            frame.setVisible(true);
//        });
//    }
    }

