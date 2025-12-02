package data_access;

import entities.Expense;
import entities.User;
import entities.ExpenseFactory;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.view_history.ViewHistoryDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewHistoryDataAccessObject implements ViewHistoryDataAccessInterface {

    private static final String BASE_URL = "https://secure.splitwise.com/api/v3.0/get_expenses?group_id=";

    private final OkHttpClient client = new OkHttpClient();
    private final String apiKey;
    private final ExpenseFactory expenseFactory = new ExpenseFactory();

    public ViewHistoryDataAccessObject(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public List<Expense> getGroupExpenses(String groupId) {

        List<Expense> result = new ArrayList<>();

        Request request = new Request.Builder()
                .url(BASE_URL + groupId)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();

            if (!response.isSuccessful()) {
                int code = response.code();
                if (code == 404) throw new IOException("Group not found");
                if (code == 401) throw new IOException("Unauthorized: invalid API key");
                if (code == 403) throw new IOException("Forbidden: not allowed to view this group");
                if (code >= 500) throw new IOException("Splitwise server error (" + code + ")");
                throw new IOException("API request failed (" + code + ")");
            }

            JSONObject root = new JSONObject(responseString);
            JSONArray expenseArray = root.optJSONArray("expenses");
            if (expenseArray == null) return result;

            for (int i = 0; i < expenseArray.length(); i++) {
                JSONObject e = expenseArray.optJSONObject(i);
                if (e != null) {
                    Expense exp = parseExpense(e);
                    if (exp != null) result.add(exp);
                }
            }

            return result;

        } catch (IOException e) {
            throw new RuntimeException("Network failure: " + e.getMessage(), e);
        }
    }

    private Expense parseExpense(JSONObject json) {

        try {
            // Expense name (Splitwise doesn't provide a separate name)
            String expenseName = json.optString("description", "Expense");

            // Cost
            double amount = json.optDouble("cost", 0.0);

            // Description / details
            String description = json.optString("details",
                    json.optString("description", "(no description)")
            );

            // Category
            String category = "Uncategorized";
            if (json.has("category")) {
                JSONObject cat = json.optJSONObject("category");
                if (cat != null) category = cat.optString("name", "Uncategorized");
            }

            // Paid by
            JSONObject creator = json.optJSONObject("created_by");
            User paidBy;
            if (creator != null) {
                paidBy = new User(
                        (long) creator.optInt("id", 0),
                        creator.optString("first_name", "Unknown"),
                        creator.optString("last_name", ""),
                        creator.optString("email", "")
                );
            } else {
                paidBy = new User(0L, "Unknown", "", "");
            }

            // Participants
            ArrayList<User> participants = new ArrayList<>();
            JSONArray usersArr = json.optJSONArray("users");

            if (usersArr != null) {
                for (int i = 0; i < usersArr.length(); i++) {

                    JSONObject shareEntry = usersArr.optJSONObject(i);
                    if (shareEntry == null) continue;

                    JSONObject u = shareEntry.optJSONObject("user");
                    if (u == null) continue;

                    User participant = new User(
                            (long) u.optInt("id", 0),
                            u.optString("first_name", "Unknown"),
                            u.optString("last_name", ""),
                            ""    // email not provided by Splitwise
                    );

                    participants.add(participant);
                }
            }

            // Use your ExpenseFactory to build the final Expense
            return expenseFactory.create(
                    expenseName,
                    description,
                    amount,
                    category,
                    participants,
                    paidBy
            );

        } catch (JSONException e) {
            System.err.println("Failed to parse expense: " + e.getMessage());
            return null;
        }
    }
}
