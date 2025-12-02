package use_case.add_expense;

import entities.Group;
import entities.Expense;
import entities.User;

/**
 * The DAO interface for the Add Expense Use Case.
 */
public interface AddExpenseDataAccessInterface {

    /**
     * Saves the expense to the data store and Splitwise API.
     * @param expense the expense to be saved
     * @param groupId the Splitwise group ID
     * @return the created expense from API
     */
    Expense save(Expense expense, Long groupId);

    /**
     * Gets the current user from Splitwise API
     * @return the current user
     */
    User getCurrentUser();

    /**
     * Gets a group from Splitwise API
     * @param groupId the group ID
     * @return the group
     */
    Group getGroup(Long groupId);
}