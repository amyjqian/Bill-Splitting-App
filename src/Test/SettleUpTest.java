package Test;

import main.usecase.*;
import main.entities.*;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

public class SettleUpTest {
    @Test
    void testUserEntity() {
        User u = new User("1", "Alice", "pw");
        assertEquals("1", u.getId());
        assertEquals("Alice", u.getName());
        assertEquals("pw", u.getPassword());

        u.setName("Bob");
        u.setPassword("new");
        assertEquals("Bob", u.getName());
        assertEquals("new", u.getPassword());

        assertEquals("Bob", u.toString()); // toString returns name
    }

    @Test
    void testExpenseEntity() {
        User payer = new User("1", "A", "pw");
        Expense e = new Expense("exp1", 30, "Dinner", payer);

        assertEquals("exp1", e.getId());
        assertEquals(30.0, e.getAmount());
        assertEquals("Dinner", e.getDescription());
        assertEquals(payer, e.getPaidBy());
        assertFalse(e.isSettled());

        User u1 = new User("2", "B", "pw");
        User u2 = new User("3", "C", "pw");
        e.addParticipant(payer);
        e.addParticipant(u1);
        e.addParticipant(u2);

        assertEquals(3, e.getParticipants().size());
        assertEquals(10.0, e.calculateEqualShare());

        e.setSettled();
        assertTrue(e.isSettled());
    }

    @Test
    void testGroupEntity() {
        Group g = new Group("id", "Group Name");
        assertTrue(g.getExpenses().isEmpty());

        User a = new User("1", "A", "pw");
        User b = new User("2", "B", "pw");

        g.addMember(a);
        g.addMember(b);

        Expense ex = new Expense("1", 20, "Test", a);
        ex.addParticipant(a);
        ex.addParticipant(b);

        g.addExpense(ex);

        assertEquals(1, g.getExpenses().size());
    }

    @Test
    void testCalculatorNoExpenses() {
        SettleUpCalculator calculator = new SettleUpCalculator();
        Group group = new Group("g", "group");
        assertEquals("No payments required", calculator.suggestedPayment(group));
    }

    @Test
    void testCalculatorAllExpensesSettled() {
        SettleUpCalculator calculator = new SettleUpCalculator();

        Group g = new Group("g1", "group");

        User a = new User("1", "A", "pw");
        User b = new User("2", "B", "pw");

        g.addMember(a);
        g.addMember(b);

        Expense e = new Expense("1", 10, "Food", a);
        e.addParticipant(a);
        e.addParticipant(b);
        e.setSettled();

        g.addExpense(e);

        assertEquals("No payments required", calculator.suggestedPayment(g));
    }

    @Test
    void testPresenterStoresValues() {
        SettleUpPresenter presenter = new SettleUpPresenter();
        SettleUpOutputData output = new SettleUpOutputData(true, true, "Done");

        presenter.present(output);

        assertEquals("Done", presenter.getMessage());
        assertTrue(presenter.isAllSettled());
    }

    @Test
    void testInteractorAllSettledFlow() {
        Group g = new Group("gid", "gname");

        User u = new User("1", "A", "pw");
        g.addMember(u);

        Expense e = new Expense("1", 20, "Test", u);
        e.addParticipant(u);
        e.setSettled();
        g.addExpense(e);

        SettleUpPresenter presenter = new SettleUpPresenter();
        SettleUpCalculator calc = new SettleUpCalculator();
        SettleUpInteractor interactor = new SettleUpInteractor(calc, presenter);

        interactor.settleUp(new SettleUpInputData(g));

        assertEquals("All Settled", presenter.getMessage());
        assertTrue(presenter.isAllSettled());
    }
}
