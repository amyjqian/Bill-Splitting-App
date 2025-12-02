package test;

import entities.Expense;
import entities.User;
import usecase.SettleUp.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class SettleUpInteractorTest {
    /** Fake DAO that either returns a list of expenses or throws an exception. */
    private static class FakeDAO implements SettleUpDataAccessInterface {
        List<Expense> expenses = new ArrayList<>();
        boolean throwError = false;

        @Override
        public List<Expense> getGroupExpenses(String groupId) throws Exception {
            if (throwError) {
                throw new Exception("DAO Error");
            }
            return expenses;
        }
    }

    /** Fake calculator that records whether it was called and with what. */
    private static class FakeCalculator implements SettlementCalculator {
        boolean called = false;
        List<Expense> receivedExpenses;
        String message = "Pay suggestion";

        @Override
        public String suggestedPayment(List<Expense> expenses) {
            called = true;
            receivedExpenses = expenses;
            return message;
        }
    }

    /** Fake presenter that stores the last output it received. */
    private static class FakePresenter implements SettleUpOutputBoundary {
        SettleUpOutputData lastOutput;

        @Override
        public void present(SettleUpOutputData outputData) {
            this.lastOutput = outputData;
        }
    }
    private Expense createExpense(boolean settled) {
        Expense e = new Expense(
                "TestExpense",
                "Description",
                10.0,
                "Food",
                new ArrayList<>(),
                null   // paidBy can be null for testing
        );
        e.setSettled(settled);
        return e;
    }
    @Test
    public void testSettleUp_emptyExpensesList() {
        FakeDAO dao = new FakeDAO();
        FakeCalculator calculator = new FakeCalculator();
        FakePresenter presenter = new FakePresenter();

        dao.expenses = new ArrayList<>();  // empty list

        SettleUpInteractor interactor = new SettleUpInteractor(dao, calculator, presenter);

        interactor.settleUp(new SettleUpInputData(10L));

        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isAllSettled());
        assertTrue(presenter.lastOutput.isSuccess());
        assertEquals("All Settled", presenter.lastOutput.getPayments());

        // Calculator must NOT be called for empty list → allSettled = true
        assertFalse(calculator.called);
    }
    @Test
    public void testSettleUp_allExpensesAlreadySettled() {
        FakeDAO dao = new FakeDAO();
        FakeCalculator calculator = new FakeCalculator();
        FakePresenter presenter = new FakePresenter();

        // all expenses settled
        dao.expenses.add(createExpense(true));
        dao.expenses.add(createExpense(true));

        SettleUpInteractor interactor = new SettleUpInteractor(dao, calculator, presenter);

        SettleUpInputData input = new SettleUpInputData(1L);
        interactor.settleUp(input);

        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isAllSettled());
        assertTrue(presenter.lastOutput.isSuccess());
        assertEquals("All Settled", presenter.lastOutput.getPayments());

        // Calculator should not be called when all are settled
        assertFalse(calculator.called);
    }

    @Test
    public void testSettleUp_someUnsettled() {
        FakeDAO dao = new FakeDAO();
        FakeCalculator calculator = new FakeCalculator();
        FakePresenter presenter = new FakePresenter();

        // one settled, one unsettled
        dao.expenses.add(createExpense(true));
        dao.expenses.add(createExpense(false));

        calculator.message = "User A pays User B";

        SettleUpInteractor interactor = new SettleUpInteractor(dao, calculator, presenter);

        SettleUpInputData input = new SettleUpInputData(2L);
        interactor.settleUp(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isAllSettled());
        assertTrue(presenter.lastOutput.isSuccess());
        assertEquals("User A pays User B", presenter.lastOutput.getPayments());

        // Calculator must be called with same expenses list
        assertTrue(calculator.called);
        assertEquals(dao.expenses, calculator.receivedExpenses);
    }

    @Test
    public void testSettleUp_calculatorThrows_withMultipleExpenses() {
        FakeDAO dao = new FakeDAO();
        FakePresenter presenter = new FakePresenter();

        dao.expenses = new ArrayList<>();
        dao.expenses.add(createExpense(false));
        dao.expenses.add(createExpense(true));   // second element ensures full branch coverage

        SettlementCalculator badCalc = new SettlementCalculator() {
            @Override
            public String suggestedPayment(List<Expense> expenses) {
                throw new RuntimeException("CalcFail2");
            }
        };

        SettleUpInteractor interactor = new SettleUpInteractor(dao, badCalc, presenter);

        interactor.settleUp(new SettleUpInputData(100L));

        assertFalse(presenter.lastOutput.isSuccess());
        assertFalse(presenter.lastOutput.isAllSettled());
        assertEquals("Error: CalcFail2", presenter.lastOutput.getPayments());
    }

    @Test
    public void testMarkAsSettled_emptyList_loopSkipped() {
        FakeDAO dao = new FakeDAO();
        FakeCalculator calc = new FakeCalculator();
        FakePresenter presenter = new FakePresenter();

        dao.expenses = new ArrayList<>(); // no expenses

        SettleUpInteractor interactor = new SettleUpInteractor(dao, calc, presenter);

        interactor.markAsSettled(new SettleUpInputData(200L));

        assertTrue(presenter.lastOutput.isSuccess());
        assertTrue(presenter.lastOutput.isAllSettled());
        assertEquals("All Settled", presenter.lastOutput.getPayments());
    }

    @Test
    public void testSettleUp_daoThrowsException() {
        FakeDAO dao = new FakeDAO();
        FakeCalculator calculator = new FakeCalculator();
        FakePresenter presenter = new FakePresenter();

        dao.throwError = true;

        SettleUpInteractor interactor = new SettleUpInteractor(dao, calculator, presenter);

        SettleUpInputData input = new SettleUpInputData(3L);
        interactor.settleUp(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertFalse(presenter.lastOutput.isAllSettled());
        assertEquals("Error: DAO Error", presenter.lastOutput.getPayments());

        // Calculator must NOT be called on error
        assertFalse(calculator.called);
    }

    @Test
    public void testSettleUp_calculatorThrowsException() {
        FakeDAO dao = new FakeDAO();
        FakePresenter presenter = new FakePresenter();

        // Make expenses include one unsettled so calculator would normally be called
        dao.expenses = new ArrayList<>();
        dao.expenses.add(createExpense(false));

        // Calculator that throws
        SettlementCalculator badCalculator = new SettlementCalculator() {
            @Override
            public String suggestedPayment(List<Expense> expenses) {
                throw new RuntimeException("CalcFail");
            }
        };

        SettleUpInteractor interactor = new SettleUpInteractor(dao, badCalculator, presenter);

        interactor.settleUp(new SettleUpInputData(11L));

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertFalse(presenter.lastOutput.isAllSettled());
        assertEquals("Error: CalcFail", presenter.lastOutput.getPayments());
    }

    // Tests for markAsSettled

    @Test
    public void testMarkAsSettled_emptyList() {
        FakeDAO dao = new FakeDAO();
        FakeCalculator calc = new FakeCalculator();
        FakePresenter presenter = new FakePresenter();

        dao.expenses = new ArrayList<>();  // empty

        SettleUpInteractor interactor = new SettleUpInteractor(dao, calc, presenter);

        interactor.markAsSettled(new SettleUpInputData(12L));

        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isSuccess());
        assertTrue(presenter.lastOutput.isAllSettled());
        assertEquals("All Settled", presenter.lastOutput.getPayments());
    }

    @Test
    public void testMarkAsSettled_success() {
        FakeDAO dao = new FakeDAO();
        FakeCalculator calculator = new FakeCalculator();
        FakePresenter presenter = new FakePresenter();

        Expense e1 = createExpense(false);
        Expense e2 = createExpense(false);
        dao.expenses.add(e1);
        dao.expenses.add(e2);

        SettleUpInteractor interactor = new SettleUpInteractor(dao, calculator, presenter);

        SettleUpInputData input = new SettleUpInputData(4L);
        interactor.markAsSettled(input);

        // All expenses should now be settled
        assertTrue(e1.getSettled());
        assertTrue(e2.getSettled());

        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isSuccess());
        assertTrue(presenter.lastOutput.isAllSettled());
        assertEquals("All Settled", presenter.lastOutput.getPayments());
    }

    @Test
    public void testMarkAsSettled_daoThrowsException() {
        FakeDAO dao = new FakeDAO();
        FakeCalculator calculator = new FakeCalculator();
        FakePresenter presenter = new FakePresenter();

        dao.throwError = true;

        SettleUpInteractor interactor = new SettleUpInteractor(dao, calculator, presenter);

        SettleUpInputData input = new SettleUpInputData(5L);
        interactor.markAsSettled(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertFalse(presenter.lastOutput.isAllSettled());
        assertEquals("Error: DAO Error", presenter.lastOutput.getPayments());
    }

    @Test
    public void testMarkAsSettled_expenseSetSettledThrows() {
        FakeDAO dao = new FakeDAO();
        FakeCalculator calculator = new FakeCalculator();
        FakePresenter presenter = new FakePresenter();

        String expenseName = "Test Expense";
        String description = "Desc";
        double amount = 10.0;
        String category = "Food";
        ArrayList<User> participants = new ArrayList<>();
        User paidBy = null;  // null is allowed because your constructor accepts it

        // Create an Expense that throws when setSettled(true)
        Expense badExpense = new Expense(
                expenseName,
                description,
                amount,
                category,
                participants,
                paidBy
        ){
            @Override
            public void setSettled(boolean value) {
                throw new RuntimeException("SetFail");
            }
        };

        dao.expenses = new ArrayList<>();
        dao.expenses.add(badExpense);

        SettleUpInteractor interactor = new SettleUpInteractor(dao, calculator, presenter);

        interactor.markAsSettled(new SettleUpInputData(13L));

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertFalse(presenter.lastOutput.isAllSettled());
        assertEquals("Error: SetFail", presenter.lastOutput.getPayments());
    }
}
