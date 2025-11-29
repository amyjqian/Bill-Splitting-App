package main.view;

import main.controllers.DisplayDataController;
import main.presenter.DisplayDataViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public interface DisplayDataView {
    void update(DisplayDataViewModel vm);

    class DisplayDataPanel extends JPanel implements DisplayDataView {

        private JPanel chartHolder;

        public DisplayDataPanel(DisplayDataController controller, DisplayDataViewModel vm) {
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(600, 400));

            chartHolder = new JPanel(new BorderLayout());
            add(chartHolder, BorderLayout.CENTER);

            // draw chart immediately
            update(vm);
        }

        @Override
        public void update(DisplayDataViewModel vm) {
            chartHolder.removeAll();

            DefaultPieDataset dataset = new DefaultPieDataset();

            for (Map.Entry<String, Map<String, Object>> entry : vm.getData().entrySet()) {
                String name = entry.getKey();
                double amount = (double) entry.getValue().get("amount");
                dataset.setValue(name, amount);
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
}
