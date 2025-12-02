
package data_access;

import entities.Expense;
import entities.Group;
import entities.User;
import use_case.AddExpenseDataAccessInterface;
import api.SplitwiseAPI;
import api.SplitwiseAPIImpl;

import java.util.List;

public class SplitwiseDataAccess implements AddExpenseDataAccessInterface {
    private final SplitwiseAPI splitwiseAPI;

    public SplitwiseDataAccess() {
        this.splitwiseAPI = new SplitwiseAPIImpl();
    }

    public SplitwiseDataAccess(SplitwiseAPI splitwiseAPI) {
        this.splitwiseAPI = splitwiseAPI;
    }

    @Override
    public Expense save(Expense expense, Long groupId) {
        try {
            // Use the Splitwise API to create the expense
            return splitwiseAPI.createExpense(expense, groupId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save expense to Splitwise: " + e.getMessage(), e);
        }
    }

    @Override
    public User getCurrentUser() {
        try {
            return splitwiseAPI.getCurrentUser();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get current user from Splitwise: " + e.getMessage(), e);
        }
    }

    @Override
    public Group getGroup(Long groupId) {
        try {
            return splitwiseAPI.getGroup(groupId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get group from Splitwise: " + e.getMessage(), e);
        }
    }
    public List<Group> getGroups() {
        try {
            return splitwiseAPI.getGroups();
        } catch (Exception e) {
            throw new RuntimeException("Failed to get groups: " + e.getMessage(), e);
        }
    }
}