package test;

import api.SplitwiseAPIImpl;
import entities.Expense;
import entities.User;
import java.util.ArrayList;

public class TestExpenseAPI {
    public static void main(String[] args) {
        System.out.println("Testing Expense API");

        SplitwiseAPIImpl api = new SplitwiseAPIImpl();

        try {
            // Get current user
            User currentUser = api.getCurrentUser();
            System.out.println("User: " + currentUser.getDisplayName());

            // Create a simple test expense
            ArrayList<User> participants = new ArrayList<>();
            participants.add(currentUser);

            Expense testExpense = new Expense(
                    "API Test Expense",
                    "Testing expense creation via API",
                    10.00,
                    "Other",
                    participants,
                    currentUser
            );

            System.out.println("Creating expense in group 0 (Non-group expenses)...");
            Expense created = api.createExpense(testExpense, 0L);

            System.out.println("Expense created");

        } catch (Exception e) {
            System.out.println("Expense creation failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}