package use_case.join_group;

import interface_adapter.join_group.JoinGroupViewModel;

public class JoinGroupPresenter implements JoinGroupOutputBoundary {
    private final JoinGroupViewModel viewModel;
    public JoinGroupPresenter(JoinGroupViewModel joinGroupViewModel) {
        this.viewModel = joinGroupViewModel;
    }

    @Override
    public void prepareStatus(JoinGroupOutputData outputData) {
        viewModel.setJoined(outputData.getJoined());
        System.out.println("You have joined: " + viewModel.getJoined());
    }
}
