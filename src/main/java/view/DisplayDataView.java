package view;

import interface_adapters.displayData.DisplayDataViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class DisplayDataView extends JPanel {

    private JPanel chartHolder;
    private JComboBox<String> modeSelector;
    private DisplayDataViewModel currentVM;

    public DisplayDataView() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(600, 400));

        modeSelector = new JComboBox<>(new String[]{"Description", "Category"});
        modeSelector.addActionListener(e -> repaintChart());
        add(modeSelector, BorderLayout.NORTH);

        chartHolder = new JPanel(new BorderLayout());
        add(chartHolder, BorderLayout.CENTER);
    }

    public void update(DisplayDataViewModel vm) {
        this.currentVM = vm;
        repaintChart();
    }

    private void repaintChart() {
        if (currentVM == null) return;

        chartHolder.removeAll();
        DefaultPieDataset dataset = new DefaultPieDataset();

        String mode = (String) modeSelector.getSelectedItem();

        if (mode.equals("Description")) {
            for (Map.Entry<String, Map<String, Object>> entry : currentVM.getData().entrySet()) {
                String description = entry.getKey();
                double amount = (double) entry.getValue().get("amount");
                String cleanName = description.split(" #")[0];
                dataset.setValue(cleanName, amount);
            }
        } else {
            Map<String, Double> categoryTotals = new HashMap<>();

            for (Map<String, Object> entry : currentVM.getData().values()) {
                double amount = (double) entry.get("amount");
                String category = (String) entry.get("category");
                categoryTotals.put(category, categoryTotals.getOrDefault(category, 0.0) + amount);
            }

            for (Map.Entry<String, Double> cat : categoryTotals.entrySet()) {
                dataset.setValue(cat.getKey(), cat.getValue());
            }
        }

        JFreeChart chart = ChartFactory.createPieChart(
                "Expenses Breakdown",
                dataset,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartHolder.add(chartPanel, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
