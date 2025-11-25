package use_case.view_history;

import entities.Expense;
import entities.Group;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.view_history.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class ViewHistoryInteractorTest {

    // Fake DAO
    private static class FakeViewHistoryDAO implements ViewHistoryDataAccessInterface {

        Map<String, List<Expense>> data = new HashMap<>();
        boolean throwException = false;

        @Override
        public List<Expense> getGroupExpenses(String groupId) throws Exception {
            if (throwException) {
                throw new Exception("DAO failure");
            }
            return data.get(groupId);
        }
    }

    // Fake Presenter to capture output
    private static class FakePresenter implements ViewHistoryOutputBoundary {

        ViewHistoryOutputData successData = null;
        ViewHistoryOutputData failData = null;

        @Override
        public void prepareSuccessView(ViewHistoryOutputData outputData) {
            this.successData = outputData;
        }

        @Override
        public void prepareFailedView(ViewHistoryOutputData outputData) {
            this.failData = outputData;
        }
    }

    private FakeViewHistoryDAO dao;
    private FakePresenter presenter;
    private ViewHistoryInteractor interactor;

    @BeforeEach
    void setUp() {
        dao = new FakeViewHistoryDAO();
        presenter = new FakePresenter();
        interactor = new ViewHistoryInteractor(dao, presenter);
    }

    @Test
    void testSuccessfulHistoryFetch() {
        String gid = "group123";

        // users
        User A = new User("1","A", "123");
        User B = new User("2","B", "234");


        // Set up fake expenses
        Expense e1 = new Expense("Dinner", 60.0, "A" ,A  , "01-02-2025");
        Expense e2 = new Expense("Taxi", 30.0, "B", B, "01-02-2025");

        dao.data.put(gid, Arrays.asList(e1, e2));

        ViewHistoryInputData input = new ViewHistoryInputData(gid);
        interactor.execute(input);

        assertNotNull(presenter.successData);
        assertNull(presenter.failData);
        assertEquals(2, presenter.successData.getExpenses().size());
    }

    @Test
    void testGroupNotFound() {
        // No group added
        ViewHistoryInputData input = new ViewHistoryInputData("missingGroup");
        interactor.execute(input);

        assertNull(presenter.successData);
        assertNotNull(presenter.failData);
        assertFalse(presenter.failData.isSuccess());
        assertTrue(presenter.failData.getMessage().contains("No history found."));
    }

    @Test
    void testDaoThrowsException() {
        dao.throwException = true;

        ViewHistoryInputData input = new ViewHistoryInputData("anyGroup");
        interactor.execute(input);

        assertNull(presenter.successData);
        assertNotNull(presenter.failData);

        assertFalse(presenter.failData.isSuccess());
        assertTrue(presenter.failData.getMessage().contains("DAO"));
    }

    @Test
    void testEmptyExpenseList() {
        dao.data.put("emptyGroup", new ArrayList<>());

        ViewHistoryInputData input = new ViewHistoryInputData("emptyGroup");
        interactor.execute(input);

        assertNotNull(presenter.successData);
        assertEquals(0, presenter.successData.getExpenses().size());
    }
}
