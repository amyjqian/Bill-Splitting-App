package main.api;

import main.entities.Group;
import main.entities.User;

public class SplidDatabaseCalls implements SplidDatabase{


    @Override
    public Group getGroup() {
        Group g = new Group("1", "Group 14");
        User amy = new User("10", "Amy");
        User katie = new User("11", "katie");

        g.addMember(amy);
        g.addMember(katie);

        return g;
    }

}
