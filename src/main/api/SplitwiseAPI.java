package main.api;

import main.entities.Expense;
import main.entities.Group;
import main.entities.User;
import org.json.JSONException;

/**
 * SplitwiseAPI is an interface that defines the methods for Splitwise integration.
 */
public interface SplitwiseAPI {
    /**
     * Creates a new expense in Splitwise
     * @param expense The expense to create
     * @param groupId The Splitwise group ID
     * @return The created expense
     * @throws JSONException if an error occurs
     */
    Expense createExpense(Expense expense, Long groupId) throws JSONException;

    /**
     * Gets a group by ID from Splitwise
     * @param groupId The Splitwise group ID
     * @return The group details
     * @throws JSONException if an error occurs
     */
    Group getGroup(Long groupId) throws JSONException;

    /**
     * Gets the current user's information
     * @return The current user
     * @throws JSONException if an error occurs
     */
    User getCurrentUser() throws JSONException;
}