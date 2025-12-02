package use_case.create_group;

import data_access.SplitwiseDataAccess;

import static org.junit.jupiter.api.Assertions.assertEquals;

import use_case.create_group.*;
import org.junit.jupiter.api.Test;

public class CreateGroupInteractorTest {

    @Test
    public void mockTest(){
        SplitwiseDataAccess userRepository = new SplitwiseDataAccess();
        CreateGroupOutputBoundary testPresenter = new CreateGroupOutputBoundary() {
            @Override
            public void prepareSuccessView(CreateGroupOutputData outputData) {

            }

            @Override
            public void present(CreateGroupOutputData outputData) {

            }

            @Override
            public void prepareFailView(String errorMessage) {

            }
        };
        CreateGroupInputBoundary interactor = new CreateGroupInteractor(userRepository, testPresenter);
        //User creator = new User(100, "Katie", "Fruitman", "kfruitman@yahoo.ca", "123movies");
        CreateGroupInputData inputData = new CreateGroupInputData("CSC207");
        interactor.execute(inputData);
        assertEquals("CSC207", inputData.getGroupName());
        //assertEquals("accomodation", inputData.getGroupType());
       // assertEquals(creator, inputData.getGroupCreator());

    }



//    public static void main(String[] args) {
//        new CreateGroupInteractorTest().mockTest();
//    }
}