package main.usecase;

import main.entities.Group;

public class SettleUpInputData {

    private final Group group;

    public SettleUpInputData(Group group) {
        this.group = group;
    }

    public Group getGroup() {
        return group;
    }
}
