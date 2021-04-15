package Idlethemeparkworld.view.popups;

import Idlethemeparkworld.misc.utils.Pair;
import java.util.ArrayList;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class LineChart extends JPanel {
    private ChartPanel chartPanel;
    public LineChart() {
        JFreeChart lineChart = ChartFactory.createLineChart(
            "",
            "","",
            new DefaultCategoryDataset(),
            PlotOrientation.VERTICAL,
            true,true,false);
        
        chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize(new java.awt.Dimension(600, 400));
        add(chartPanel);
    }
    
    private static DefaultCategoryDataset createDataset(String title, ArrayList<Pair<String,Double>> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        data.forEach(d -> dataset.addValue(d.getValue(), title, d.getKey()));
        return dataset;         
    }
   
    public void setData(String title, String x, String y, ArrayList<Pair<String,Double>> data){
        DefaultPieDataset dataset = new DefaultPieDataset( );
        data.forEach(d -> dataset.setValue(d.getKey(), d.getValue()));
        chartPanel.setChart(ChartFactory.createLineChart(
            title,
            y,x,
            createDataset(title, data),
            PlotOrientation.VERTICAL,
            true,true,false));
    }
}
