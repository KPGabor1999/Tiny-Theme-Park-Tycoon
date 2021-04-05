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
   public PieChart(String title, ArrayList<Pair<String,Double>> data) {
      JFreeChart chart = ChartFactory.createPieChart(      
         title, 
         createDataset(data),   
         true,             // include legend   
         true, 
         false);
         
      ChartPanel chartPanel = new ChartPanel( chart );        
      chartPanel.setPreferredSize(new Dimension( 400 , 400 ) );
      add(chartPanel);
   }
   
   private static PieDataset createDataset(ArrayList<Pair<String,Double>> data) {
      DefaultPieDataset dataset = new DefaultPieDataset( );
      //data.forEach(d -> dataset.setValue(d.getKey(), d.getValue()));
      dataset.setValue( "IPhone 5s" , new Double( 20 ) );  
      dataset.setValue( "SamSung Grand" , new Double( 20 ) );   
      dataset.setValue( "MotoG" , new Double( 40 ) );    
      dataset.setValue( "Nokia Lumia" , new Double( 10 ) );  
      return dataset;         
   }
}
