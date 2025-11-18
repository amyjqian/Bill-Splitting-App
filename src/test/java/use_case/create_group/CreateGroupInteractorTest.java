package use_case.create_group;

import data_access.InMemoryGroupDataAccessObject;

//import static org.junit.jupiter.api.Assertions.assertNull;

public class CreateGroupInteractorTest {
    void mockTest(){
        InMemoryGroupDataAccessObject userRepository = new InMemoryGroupDataAccessObject();
        CreateGroupOutputBoundary testPresenter = new CreateGroupOutputBoundary();
        CreateGroupInputBoundary interactor = new CreateGroupInteractor(userRepository, testPresenter);
        interactor.execute();

    }

    public static void main(String[] args) {
        new CreateGroupInteractorTest().mockTest();
    }
}


