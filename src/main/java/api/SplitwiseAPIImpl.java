package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entities.Expense;
import entities.Group;
import entities.User;
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
        String apiKey = System.getenv("SPLITWISE_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            return "smmaCgUHfNZ3KRPzuny1KxRqLGMYoPzlHj6ABJwA";
        }

        return apiKey.trim();
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
            final String responseBodyString = response.body().string();
            System.out.println("DEBUG - Create Expense Response: " + responseBodyString);

            final JSONObject responseBody = new JSONObject(responseBodyString);

            // Check for errors first
            if (responseBody.has("errors") && !responseBody.getJSONObject("errors").isEmpty()) {
                JSONObject errors = responseBody.getJSONObject("errors");
                if (errors.has("base")) {
                    throw new RuntimeException("Failed to create expense: " + errors.getJSONArray("base").getString(0));
                } else {
                    throw new RuntimeException("Failed to create expense: " + errors.toString());
                }
            }

            // If we get here, the response was successful
            if (responseBody.has("expenses") && responseBody.getJSONArray("expenses").length() > 0) {
                final JSONObject expenseJson = responseBody.getJSONArray("expenses").getJSONObject(0);
                return parseExpenseFromJson(expenseJson);
            } else {
                throw new RuntimeException("No expense object in response");
            }

        } catch (IOException | JSONException event) {
            throw new RuntimeException("API call failed: " + event.getMessage(), event);
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

    /**
     * Builds the JSON request body for creating a group
     */
    private JSONObject buildGroupRequestBody(String name) {
        JSONObject requestBody = new JSONObject();

        // Required fields
        requestBody.put("name", name);
        // Debug logging
        System.out.println("DEBUG - Request Body: " + requestBody.toString(2));

        return requestBody;
    }

    /**
     * Builds the JSON request body for creating an expense
     */
    private JSONObject buildExpenseRequestBody(Expense expense, Long groupId) throws JSONException {
        JSONObject requestBody = new JSONObject();

        // Required fields
        double totalAmount = expense.getAmount();
        requestBody.put("cost", String.format("%.2f", totalAmount));
        requestBody.put("cost", String.format("%.2f", expense.getAmount()));
        requestBody.put("description", expense.getDescription());
        requestBody.put("details", expense.getDescription());

        // Group ID
        if (groupId != null) {
            requestBody.put("group_id", groupId);
        }

        // Category
        if (expense.getCategory() != null && !expense.getCategory().isEmpty()) {
            requestBody.put("category_id", getCategoryId(expense.getCategory()));
        }

        List<User> participants = expense.getParticipants();
        User paidBy = expense.getPaidBy();

        // Count only the participants who actually owe money (excluding payer if they're in the list)
        List<User> owingParticipants = participants.stream()
                .filter(user -> !user.getId().equals(paidBy.getId()))
                .collect(Collectors.toList());

        // Total number of people sharing the cost (including payer)
        int totalSharing = owingParticipants.size() + 1;

        // Calculate shares using exact integer arithmetic to avoid floating-point errors
        int totalCents = (int) Math.round(totalAmount * 100);
        int baseShareCents = totalCents / totalSharing;
        int remainderCents = totalCents % totalSharing;

        // Convert back to dollars
        double baseShare = baseShareCents / 100.0;

        // Add the payer first (index 0)
        double payerOwedShare = baseShare;
        if (remainderCents > 0) {
            payerOwedShare += remainderCents / 100.0; // Add the remainder to the payer
        }

        requestBody.put("users__0__user_id", paidBy.getId());
        requestBody.put("users__0__paid_share", String.format("%.2f", totalAmount));
        requestBody.put("users__0__owed_share", String.format("%.2f", payerOwedShare));

        // Add other participants with exact base share
        int userIndex = 1;
        for (User participant : owingParticipants) {
            requestBody.put("users__" + userIndex + "__user_id", participant.getId());
            requestBody.put("users__" + userIndex + "__paid_share", "0.00");
            requestBody.put("users__" + userIndex + "__owed_share", String.format("%.2f", baseShare));
            userIndex++;
        }

        // Calculate equal shares for participants
        double shareAmount = expense.calculateEqualShare();
        List<User> participants = expense.getParticipants();

        // Start with the person who paid (index 0)
        requestBody.put("users__0__user_id", expense.getPaidBy().getId());
        requestBody.put("users__0__paid_share", String.format("%.2f", expense.getAmount()));
        requestBody.put("users__0__owed_share", String.format("%.2f", shareAmount));

        // Add other participants (excluding the person who paid if they're in the list)
        int userIndex = 1;
        for (User participant : participants) {
            // Skip if this is the same user who paid (they're already added at index 0)
            if (!participant.getId().equals(expense.getPaidBy().getId())) {
                requestBody.put("users__" + userIndex + "__user_id", participant.getId());
                requestBody.put("users__" + userIndex + "__paid_share", "0.00");
                requestBody.put("users__" + userIndex + "__owed_share", String.format("%.2f", shareAmount));
                userIndex++;
            }
        }

        // Debug logging
        System.out.println("DEBUG - Request Body: " + requestBody.toString(2));

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
            case "food":
                return 1;
            case "transportation":
                return 2;
            case "entertainment":
                return 3;
            case "utilities":
                return 4;
            case "other":
                return 5;
            default:
                return 5; // Other
        }
    }

    /**
     * Parses a JSON response into an Expense object
     */
    /**
     * Parses a JSON response into an Expense object
     */
    private Expense parseExpenseFromJson(JSONObject expenseJson) throws JSONException {
        // Extract expense details from JSON response
        String description = expenseJson.getString("description");
        double cost = Double.parseDouble(expenseJson.getString("cost"));
        String date = expenseJson.getString("date");

        // Get category name if available
        String category = "Other";
        if (expenseJson.has("category")) {
            JSONObject categoryObj = expenseJson.getJSONObject("category");
            category = categoryObj.getString("name");
        }

        // Parse participants from users array
        List<User> participants = new ArrayList<>();
        if (expenseJson.has("users")) {
            JSONArray usersArray = expenseJson.getJSONArray("users");
            for (int i = 0; i < usersArray.length(); i++) {
                JSONObject userObj = usersArray.getJSONObject(i);
                JSONObject userDetails = userObj.getJSONObject("user");
                User user = parseUserFromJson(userDetails);
                participants.add(user);
            }
        }

        // Get the user who created the expense (paid by)
        User paidBy = null;
        if (expenseJson.has("created_by")) {
            JSONObject createdBy = expenseJson.getJSONObject("created_by");
            paidBy = parseUserFromJson(createdBy);
        }

        // Create expense with the parsed data
        Expense expense = new Expense(
                description, // expense name
                description, // description
                cost,        // amount
                category,    // category
                new ArrayList<>(participants),
                paidBy       // paid by
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
        List<User> members = new ArrayList<>();
        if (groupJson.has("members")) {
            JSONArray membersArray = groupJson.getJSONArray("members");
            for (int i = 0; i < membersArray.length(); i++) {
                JSONObject memberJson = membersArray.getJSONObject(i);
                User user = parseUserFromJson(memberJson);
                members.add(user);
            }
        }

        return new Group(id, name, members);
    }

    /**
     * Parses a JSON response into a User object
     */
    private User parseUserFromJson(JSONObject userJson) throws JSONException {
        Long id = userJson.getLong("id");

        // Handle null first_name
        String firstName = userJson.optString("first_name", "");
        if (firstName == null || firstName.equals("null")) {
            firstName = "";
        }

        // Handle null last_name - use optString and provide default
        String lastName = userJson.optString("last_name", "");
        if (lastName == null || lastName.equals("null") || lastName.isEmpty()) {
            lastName = ""; // Set to empty string if null
        }

        String email = userJson.optString("email", "");

        return new User(id, firstName, lastName, email);
    }

    public List<Group> getGroups() throws JSONException {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final Request request = new Request.Builder()
                .url(API_URL + "/get_groups")
                .method("GET", null)
                .addHeader(AUTHORIZATION, BEARER_PREFIX + getAPIKey())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final String responseBodyString = response.body().string();
            System.out.println("DEBUG - Groups Response: " + responseBodyString);

            final JSONObject responseBody = new JSONObject(responseBodyString);

            if (!response.isSuccessful()) {
                throw new RuntimeException("Failed to get groups: HTTP " + response.code());
            }

            List<Group> groups = new ArrayList<>();
            JSONArray groupsArray = responseBody.getJSONArray("groups");

            for (int i = 0; i < groupsArray.length(); i++) {
                JSONObject groupJson = groupsArray.getJSONObject(i);
                Group group = parseGroupFromJson(groupJson);
                groups.add(group);
            }

            return groups;

        } catch (IOException | JSONException event) {
            throw new RuntimeException("Failed to get groups: " + event.getMessage(), event);
        }
    }
}

    @Override
    public Group createGroup(String name) throws JSONException {
        final OkHttpClient client = new OkHttpClient().newBuilder().build();
        final MediaType mediaType = MediaType.parse(APPLICATION_JSON);

        // Build the request body according to Splitwise API requirements
        final JSONObject requestBody = buildGroupRequestBody(name);
        final RequestBody body = RequestBody.create(mediaType, requestBody.toString());

        final Request request = new Request.Builder()
                .url(API_URL + "/create_group")
                .method("POST", body)
                .addHeader(AUTHORIZATION, BEARER_PREFIX + getAPIKey())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final String responseBodyString = response.body().string();
            System.out.println("DEBUG - Create Group Response: " + responseBodyString);

            final JSONObject responseBody = new JSONObject(responseBodyString);

            // Check for errors first
            if (responseBody.has("errors") && !responseBody.getJSONObject("errors").isEmpty()) {
                JSONObject errors = responseBody.getJSONObject("errors");
                if (errors.has("base")) {
                    throw new RuntimeException("Failed to create group: " + errors.getJSONArray("base").getString(0));
                } else {
                    throw new RuntimeException("Failed to create group: " + errors.toString());
                }
            }

            // If we get here, the response was successful
            if (responseBody.has("group") && responseBody.length() > 0) {
                final JSONObject groupJson = responseBody.getJSONObject("group");
                return parseGroupFromJson(groupJson);
            } else {
                throw new RuntimeException("No group object in response");
            }

        } catch (IOException | JSONException event) {
            throw new RuntimeException("API call failed: " + event.getMessage(), event);
        }
    }

    /**
     * Builds the JSON request body for adding a user to group
     */
    private JSONObject addUserRequestBody(long groupID, long userID) throws JSONException {
        JSONObject requestBody = new JSONObject();
        requestBody.put("group_id", groupID);
        requestBody.put("user_id", userID);
        return requestBody;
    }

    @Override
    public User getUser(long userID) throws JSONException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(API_URL + "/get_user/" + userID)
                .get()
                .addHeader(AUTHORIZATION, BEARER_PREFIX + getAPIKey())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String body = response.body().string();
            JSONObject json = new JSONObject(body);

            if (!response.isSuccessful()) {
                // handle 403/404 etc.
                throw new RuntimeException("Failed to get user: HTTP " + response.code() + " – " + body);
            }

            if (json.has("user")) {
                JSONObject userJson = json.getJSONObject("user");
                return parseUserFromJson(userJson);
            } else if (json.has(ERRORS)) {
                // either errors as object or array
                Object errs = json.get(ERRORS);
                throw new RuntimeException("API returned error getting user: " + errs.toString());
            } else {
                throw new RuntimeException("Unexpected response fetching user: " + body);
            }
        } catch (IOException | JSONException e) {
            throw new RuntimeException("API call error getting user: " + e.getMessage(), e);
        }
    }

}

