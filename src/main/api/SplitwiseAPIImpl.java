package main.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import main.entities.Expense;
import main.entities.Group;
import main.entities.User;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * SplitwiseAPIImpl class for Splitwise API integration.
 */
public class SplitwiseAPIImpl implements SplitwiseAPI {
    // Constants
    private static final String API_URL = "https://www.splitwise.com/api/v3.0";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String ERRORS = "errors";
    private static final String ERROR = "error";

    // Load API key from environment variable
    public static String getAPIKey() {
        return System.getenv("SPLITWISE_API_KEY");
    }

    @Override
    public Expense createExpense(Expense expense, Long groupId) throws JSONException {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final MediaType mediaType = MediaType.parse(APPLICATION_JSON);

        // Build the request body according to Splitwise API requirements
        final JSONObject requestBody = buildExpenseRequestBody(expense, groupId);
        final RequestBody body = RequestBody.create(mediaType, requestBody.toString());

        final Request request = new Request.Builder()
                .url(API_URL + "/create_expense")
                .method("POST", body)
                .addHeader(AUTHORIZATION, BEARER_PREFIX + getAPIKey())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            // Check for errors in response
            if (responseBody.has(ERRORS) && responseBody.getJSONArray(ERRORS).length() > 0) {
                throw new RuntimeException("Failed to create expense: " +
                        responseBody.getJSONArray(ERRORS).getJSONObject(0).getString("message"));
            }

            if (response.isSuccessful()) {
                final JSONObject expenseJson = responseBody.getJSONObject("expense");
                return parseExpenseFromJson(expenseJson);
            } else {
                throw new RuntimeException("Failed to create expense. HTTP " + response.code());
            }
        } catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }

    @Override
    public Group getGroup(Long groupId) throws JSONException {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(API_URL + "/get_group/" + groupId)
                .method("GET", null)
                .addHeader(AUTHORIZATION, BEARER_PREFIX + getAPIKey())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.has(ERRORS) && responseBody.getJSONArray(ERRORS).length() > 0) {
                throw new RuntimeException("Failed to get group: " +
                        responseBody.getJSONArray(ERRORS).getJSONObject(0).getString("message"));
            }

            if (response.isSuccessful()) {
                final JSONObject groupJson = responseBody.getJSONObject("group");
                return parseGroupFromJson(groupJson);
            } else {
                throw new RuntimeException("Failed to get group. HTTP " + response.code());
            }
        } catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }

    @Override
    public User getCurrentUser() throws JSONException {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(API_URL + "/get_current_user")
                .method("GET", null)
                .addHeader(AUTHORIZATION, BEARER_PREFIX + getAPIKey())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            if (responseBody.has(ERRORS) && responseBody.getJSONArray(ERRORS).length() > 0) {
                throw new RuntimeException("Failed to get current user: " +
                        responseBody.getJSONArray(ERRORS).getJSONObject(0).getString("message"));
            }

            if (response.isSuccessful()) {
                final JSONObject userJson = responseBody.getJSONObject("user");
                return parseUserFromJson(userJson);
            } else {
                throw new RuntimeException("Failed to get current user. HTTP " + response.code());
            }
        } catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }

    /**
     * Builds the JSON request body for creating an expense
     */
    private JSONObject buildExpenseRequestBody(Expense expense, Long groupId) throws JSONException {
        JSONObject requestBody = new JSONObject();

        // Required fields
        requestBody.put("cost", String.format("%.2f", expense.getAmount()));
        requestBody.put("description", expense.getDescription());

        // Group ID (optional but recommended for group expenses)
        if (groupId != null) {
            requestBody.put("group_id", groupId);
        }

        // Category (if available)
        if (expense.getCategory() != null && !expense.getCategory().isEmpty()) {
            // You might need to map your category to Splitwise category IDs
            // This is a simplified version - you may need to adjust based on your categories
            requestBody.put("category_id", getCategoryId(expense.getCategory()));
        }

        // Paid by user (the person who paid)
        requestBody.put("users__0__user_id", expense.getPaiedBy().getId());
        requestBody.put("users__0__paid_share", String.format("%.2f", expense.getAmount()));

        // Calculate equal shares for participants
        double shareAmount = expense.calculateEqualShare();
        List<User> participants = expense.getParticipants();

        // Add participants and their shares
        for (int i = 0; i < participants.size(); i++) {
            User participant = participants.get(i);
            requestBody.put("users__" + (i + 1) + "__user_id", participant.getId());
            requestBody.put("users__" + (i + 1) + "__owed_share", String.format("%.2f", shareAmount));
        }

        return requestBody;
    }

    /**
     * Maps category names to Splitwise category IDs
     * You'll need to expand this based on your actual categories
     */
    private int getCategoryId(String category) {
        // This is a simplified mapping - you should use actual Splitwise category IDs
        switch (category.toLowerCase()) {
            case "food": return 1;
            case "transportation": return 2;
            case "entertainment": return 3;
            case "utilities": return 4;
            case "other": return 5;
            default: return 5; // Other
        }
    }

    /**
     * Parses a JSON response into an Expense object
     */
    private Expense parseExpenseFromJson(JSONObject expenseJson) throws JSONException {
        // Extract expense details from JSON response
        String description = expenseJson.getString("description");
        double cost = Double.parseDouble(expenseJson.getString("cost"));
        String date = expenseJson.getString("date");

        // Note: You might need to adjust this based on what data you want to store
        // from the Splitwise response
        Expense expense = new Expense(
                description, // expense name
                description, // description
                cost,        // amount
                "other",     // category (you might want to extract this from response)
                new ArrayList<>(), // participants (would need to parse from users array)
                null         // paid by (would need to parse from users array)
        );

        return expense;
    }

    /**
     * Parses a JSON response into a Group object
     */
    private Group parseGroupFromJson(JSONObject groupJson) throws JSONException {
        Long id = groupJson.getLong("id");
        String name = groupJson.getString("name");

        // Parse members
        JSONArray membersArray = groupJson.getJSONArray("members");
        List<User> members = new ArrayList<>();
        for (int i = 0; i < membersArray.length(); i++) {
            JSONObject memberJson = membersArray.getJSONObject(i);
            User user = parseUserFromJson(memberJson);
            members.add(user);
        }

        return new Group(id, name, members);
    }

    /**
     * Parses a JSON response into a User object
     */
    private User parseUserFromJson(JSONObject userJson) throws JSONException {
        Long id = userJson.getLong("id");
        String firstName = userJson.getString("first_name");
        String lastName = userJson.getString("last_name");
        String email = userJson.optString("email", "");

        return new User(id, firstName, lastName, email);
    }
}