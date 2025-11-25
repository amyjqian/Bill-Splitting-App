package use_case.join_group;

import data_access.InMemoryGroupDataAccessObject;
import entities.User;
import use_case.create_group.*;

public class JoinGroupInteractorTest {
    void mockTest(){

        //create a test group
        CreateGroupInteractorTest.mockTest();

        //initialize join group input data
        InMemoryGroupDataAccessObject userRepository = new InMemoryGroupDataAccessObject();
        JoinGroupOutputBoundary testPresenter = new JoinGroupOutputBoundary();
        JoinGroupInputBoundary interactor = new JoinGroupInteractor(userRepository, testPresenter);
        User creator = new User(100, "Katie", "Fruitman", "kfruitman@yahoo.ca", "123movies");
        JoinGroupInputData inputData = new JoinGroupInputData(100, 20);
        interactor.execute(inputData);
    }
    public static void main(String[]args){
        new JoinGroupInteractorTest().mockTest();
    }
}
