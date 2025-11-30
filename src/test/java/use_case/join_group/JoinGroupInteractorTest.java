package use_case.join_group;

import data_access.InMemoryGroupDataAccessObject;
import entities.Group;
import entities.User;
import use_case.create_group.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class JoinGroupInteractorTest {
    private final InMemoryGroupDataAccessObject dao = new InMemoryGroupDataAccessObject();

    @Test
    public void joinGroupMockTest(){
        // create dao
        InMemoryGroupDataAccessObject userRepository = dao;

        //add new users to userbase
        User creator = userRepository.createUser(100, "Katie", "Fruitman", "kfruitman@yahoo.ca", "123movies");
        User newUser = userRepository.createUser(75, "Lucy", "Liu", "lliu24@gmail.com", "test");

        //creating test group
        CreateGroupOutputBoundary testGroupPresenter = new CreateGroupOutputBoundary();
        CreateGroupInputBoundary createGroupInteractor = new CreateGroupInteractor(userRepository, testGroupPresenter);
        CreateGroupInputData createGroupInputData = new CreateGroupInputData("CSC207");
        createGroupInteractor.execute(createGroupInputData);

        //initialize join group input data
        JoinGroupOutputBoundary testPresenter = new JoinGroupOutputBoundary();
        JoinGroupInputBoundary interactor = new JoinGroupInteractor(userRepository, testPresenter);
        JoinGroupInputData inputData = new JoinGroupInputData(newUser.getId(), 20);
        interactor.execute(inputData);

        List expectedMembers = new ArrayList<>();
        expectedMembers.add(creator);
        expectedMembers.add(newUser);

        Group g = userRepository.getGroup(inputData.getGroupID());
        List actualMembers = g.getMembers();
        assertEquals(expectedMembers, actualMembers);

    }
    public static void main(String[]args){
        new JoinGroupInteractorTest().joinGroupMockTest();
    }
}
