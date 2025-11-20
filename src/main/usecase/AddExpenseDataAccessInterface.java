package main.usecase;

import main.entities.Expense;

/**
 * The DAO interface for the Add Expense Use Case.
 */
public interface AddExpenseDataAccessInterface {

    /**
     * Saves the expense to the data store.
     * @param expense the expense to be saved
     */
    void save(Expense expense);
}