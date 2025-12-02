package use_case.create_group;

import interface_adapter.create_group.CreateGroupViewModel;

public class CreateGroupPresenter implements CreateGroupOutputBoundary {
    CreateGroupViewModel viewModel;
    public CreateGroupPresenter(CreateGroupViewModel viewModel) {
        this.viewModel = viewModel;
    }
    @Override
    public void prepareSuccessView(CreateGroupOutputData outputData) {
        //on success switch to the view my group view
    }

    @Override
    public void present(CreateGroupOutputData outputData) {
        //System.out.println(outputData.getInviteLink());
        viewModel.setInviteLink(outputData.getInviteLink());
        System.out.println("Presenter VM = " + viewModel);
        System.out.println(viewModel.getInviteLink());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Error: " + errorMessage);
    }
}
