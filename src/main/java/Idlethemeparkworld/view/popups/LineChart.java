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
         
        ChartPanel chartPanel = new ChartPanel(lineChart);
        chartPanel.setPreferredSize( new java.awt.Dimension(400, 400));
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        dataset.addValue( 15 , "schools" , "1970" );
        dataset.addValue( 30 , "schools" , "1980" );
        dataset.addValue( 60 , "schools" ,  "1990" );
        dataset.addValue( 120 , "schools" , "2000" );
        dataset.addValue( 240 , "schools" , "2010" );
        dataset.addValue( 300 , "schools" , "2014" );
        return dataset;
    }
    
    private static DefaultCategoryDataset createDataset(String title, ArrayList<Pair<String,Double>> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        data.forEach(d -> dataset.addValue(d.getValue(), title, d.getKey()));
        return dataset;         
    }
   
    public void setData(String title, String x, String y, ArrayList<Pair<String,Double>> data){
        DefaultPieDataset dataset = new DefaultPieDataset( );
        data.forEach(d -> dataset.setValue(d.getKey(), d.getValue()));
        JFreeChart lineChart = ChartFactory.createLineChart(
            title,
            y,x,
            createDataset(title, data),
            PlotOrientation.VERTICAL,
            true,true,false);
    }
}
