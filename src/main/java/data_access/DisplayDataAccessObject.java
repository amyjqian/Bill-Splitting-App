package data_access;

import org.json.JSONArray;
import org.json.JSONObject;
import use_case.DisplayData.ExpenseDataAccessInterface;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

public class DisplayDataAccessObject implements ExpenseDataAccessInterface {

    private final String apiKey;
    private final String groupId;

    public DisplayDataAccessObject(String apiKey, String groupId) {
        this.apiKey = apiKey;
        this.groupId = groupId;
    }

    @Override
    public Map<String, Map<String, Object>> getAllExpenses(){
        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://secure.splitwise.com/api/v3.0/get_expenses?group_id="))
                    .header("Authorization", "Bearer " + apiKey)
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());
            JSONArray expenses = json.getJSONArray("expenses");

            Map<String, Map<String, Object>> data = new HashMap<>();

            for (int i = 0; i < expenses.length(); i++) {
                JSONObject exp = expenses.getJSONObject(i);
                String description = exp.getString("description");
                double amount = exp.getDouble("cost");

                String category = "Uncategorized";
                if (!exp.isNull("category")) {
                    category = exp.getJSONObject("category").getString("name");
                }

                Map<String, Object> entry = new HashMap<>();
                entry.put("amount", amount);
                entry.put("category", category);

                data.put(description + " #" + i, entry);
            }

            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }
}