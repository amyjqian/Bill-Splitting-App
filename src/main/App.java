package main;

import main.presenter.DisplayDataViewModel;
import main.use_case.DisplayData.ExpenseDataAccessInterface;
import main.view.DisplayDataView;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

public class App {
    public static void main(String[] args) {

        // Fake data access for now
        ExpenseDataAccessInterface dataAccess = () -> {
            Map<String, Map<String, Object>> sample = new HashMap<>();

            Map<String, Object> coffee = new HashMap<>();
            coffee.put("amount", 4.50);
            coffee.put("category", "Food");
            sample.put("Coffee", coffee);

            Map<String, Object> uber = new HashMap<>();
            uber.put("amount", 12.80);
            uber.put("category", "Transport");
            sample.put("Uber Ride", uber);

            Map<String, Object> groceries = new HashMap<>();
            groceries.put("amount", 40.25);
            groceries.put("category", "Food");
            sample.put("Groceries", groceries);

            return sample;
        };

        DisplayDataViewModel vm = new DisplayDataViewModel();
        vm.setData(dataAccess.getAllExpenses());

        DisplayDataView.DisplayDataPanel view =
                new DisplayDataView.DisplayDataPanel(null, vm);

        JFrame frame = new JFrame("Expenses Pie Chart");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
