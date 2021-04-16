package Idlethemeparkworld.view.popups;

import Idlethemeparkworld.misc.utils.Pair;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

public class PieChart extends JPanel {

    private final ChartPanel chartPanel;

    public PieChart() {
        JFreeChart chart = ChartFactory.createPieChart(
                "",
                new DefaultPieDataset(),
                true, // include legend   
                true,
                false);

        chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(600, 400));
        add(chartPanel);
    }

    private static PieDataset createDataset(ArrayList<Pair<String, Double>> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        data.forEach(d -> dataset.setValue(d.getKey(), d.getValue()));
        return dataset;
    }

    public void setData(String title, ArrayList<Pair<String, Double>> data, boolean displayLable) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        data.forEach(d -> dataset.setValue(d.getKey(), d.getValue()));
        chartPanel.setChart(ChartFactory.createPieChart(
                title,
                createDataset(data),
                true, // include legend   
                displayLable,
                false));
    }
}
