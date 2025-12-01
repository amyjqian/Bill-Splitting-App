package data_access;

import entities.Expense;
import entities.User;
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
                // throw new RuntimeException("API Error: " + response.code());
                int code = response.code();

                if (code == 404) {
                    throw new IOException("Group not found");
                } else if (code == 401) {
                    throw new IOException("Unauthorized: invalid API key");
                } else if (code == 403) {
                    throw new IOException("Forbidden: not allowed to view this group");
                } else if (code >= 500) {
                    throw new IOException("Splitwise server error (" + code + ")");
                } else {
                    throw new IOException("API request failed (" + code + ")");
                }
            }

            JSONObject root = new JSONObject(responseString);
            JSONArray expenseArray = root.optJSONArray("expenses");
            if (expenseArray == null) {
                return result; // no expenses
            }

            for (int i = 0; i < expenseArray.length(); i++) {
                JSONObject e = expenseArray.optJSONObject(i);
                if (e != null) {
                    Expense exp = parseExpense(e);
                    if (exp != null) {
                        result.add(exp);
                    }
                }
            }

            return result;

        } catch (IOException e) {
            throw new RuntimeException("Network failure: " + e.getMessage(),e);
        }
    }

    private Expense parseExpense(JSONObject json) {
            try {
            String id = json.optString("id", "no id");
            double amount = json.optDouble("cost",0.0);
            String description = json.optString("description","(no description)");
            String date = json.optString("date", "(no date)");

            // Who paid?
            JSONObject createdByObj = json.optJSONObject("created_by");
            User paidBy;

            if (createdByObj != null) {
                paidBy = new User(
                        String.valueOf(createdByObj.optInt("id", 0)),
                        createdByObj.optString("first_name", "Unknown"),
                        ""
                );
            } else {
                paidBy = new User("0", "Unknown", "");
            }

            // Participants
            JSONArray userShares = json.optJSONArray("users");
            List<User> participants = new ArrayList<>();

            if (userShares != null) {
                for (int i = 0; i < userShares.length(); i++) {
                    JSONObject shareEntry = userShares.optJSONObject(i);
                    if (shareEntry == null) continue;

                    JSONObject u = shareEntry.optJSONObject("user");
                    if (u == null) continue;

                    User participant = new User(
                            String.valueOf(u.optInt("id", 0)),
                            u.optString("first_name", "Unknown"),
                            ""
                    );
                    participants.add(participant);
                }
            }

            // build expense entity
            Expense exp = new Expense(id, amount, description, paidBy, date);

            // Add participants
            for (User u : participants) {
                exp.addParticipant(u);
            }

            return exp;
        }  catch (JSONException e) {
                // unexpected missing field
                System.err.println("Failed to parse expense: " + e.getMessage());
                return null;
            }
    }

}
