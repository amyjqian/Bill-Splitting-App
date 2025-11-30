package use_case.create_group;

import data_access.InMemoryGroupDataAccessObject;
import entities.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CreateGroupInteractorTest {

    @Test
    public void mockTest(){
        InMemoryGroupDataAccessObject userRepository = new InMemoryGroupDataAccessObject();
        CreateGroupOutputBoundary testPresenter = new CreateGroupOutputBoundary();
        CreateGroupInputBoundary interactor = new CreateGroupInteractor(userRepository, testPresenter);
        //User creator = new User(100, "Katie", "Fruitman", "kfruitman@yahoo.ca", "123movies");
        CreateGroupInputData inputData = new CreateGroupInputData("CSC207");
        interactor.execute(inputData);
        assertEquals("CSC207", inputData.getGroupName());
        //assertEquals("accomodation", inputData.getGroupType());
       // assertEquals(creator, inputData.getGroupCreator());

    }

    public static void main(String[] args) {
        new CreateGroupInteractorTest().mockTest();
    }
}