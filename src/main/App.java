package main;

import interface_adapters.displayData.DisplayDataViewModel;
import use_case.DisplayData.ExpenseDataAccessInterface;
import view.DisplayDataView;

import javax.swing.*;
import java.net.http.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.json.*;


// ids for testing: // empty group: 90642377 // group with entries: 90437991
public class App {

    private static final String TOKEN = "smmaCgUHfNZ3KRPzuny1KxRqLGMYoPzlHj6ABJwA";

    public static void main(String[] args) {

        ExpenseDataAccessInterface dataAccess = () -> {
            try {
                HttpClient client = HttpClient.newHttpClient();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://secure.splitwise.com/api/v3.0/get_expenses?group_id="))
                        .header("Authorization", "Bearer " + TOKEN)
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
        };

        DisplayDataViewModel vm = new DisplayDataViewModel();
        vm.setData(dataAccess.getAllExpenses());

        DisplayDataView view = new DisplayDataView();
        view.update(vm);

        JFrame frame = new JFrame("Expenses Pie Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
