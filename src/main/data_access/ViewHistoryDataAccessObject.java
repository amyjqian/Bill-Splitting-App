package data_access;

import entities.Expense;
import entities.User;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.view_history.ViewHistoryDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewHistoryDataAccessObject implements ViewHistoryDataAccessInterface {

    private static final String BASE_URL = "https://secure.splitwise.com/api/v3.0/get_group/";

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

            if (!response.isSuccessful()) {
                throw new RuntimeException("API Error: " + response.code());
            }

            JSONObject root = new JSONObject(response.body().string());
            JSONObject groupObj = root.getJSONObject("group");
            JSONArray expenseArray = groupObj.getJSONArray("expenses");

            for (int i = 0; i < expenseArray.length(); i++) {
                JSONObject e = expenseArray.getJSONObject(i);
                result.add(parseExpense(e));
            }

            return result;

        } catch (IOException e) {
            throw new RuntimeException("Network failure: " + e.getMessage());
        }
    }

    private Expense parseExpense(JSONObject json) {
        String id = json.getString("id");
        double amount = json.getDouble("cost");
        String description = json.getString("description");
        String date = json.getString("date");

        // Who paid?
        JSONObject createdBy = json.getJSONObject("created_by");
        User paidBy = new User(
                String.valueOf(createdBy.getInt("id")),
                createdBy.getString("first_name"),
                "");

        // Participants
        List<User> participants = new ArrayList<>();
        JSONArray userShares = json.getJSONArray("users");

        for (int i = 0; i < userShares.length(); i++) {
            JSONObject u = userShares.getJSONObject(i).getJSONObject("user");

            // participants need to change to get users
            User participant = new User(
                    String.valueOf(u.getInt("id")),
                    u.getString("first_name"),
                    ""
            );

            participants.add(participant);
        }

        // Build Expense using your constructor
        Expense exp = new Expense(id, amount, description, paidBy, date);

        // Add participants through provided method
        for (User u : participants) {
            exp.addParticipant(u);
        }

        return exp;
    }
}
