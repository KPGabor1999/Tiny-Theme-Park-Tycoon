package Idlethemeparkworld.view.popups;

import Idlethemeparkworld.model.GameManager;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class StatsPanel extends JPanel {
    private GameManager gm;
    private JComboBox statDropdown;
    private JPanel chart;
    private PieChart pieChart;
    private LineChart lineChart;
    
    public StatsPanel(GameManager gm){
        this.gm = gm;
        this.pieChart = new PieChart();
        statDropdown = new JComboBox<>();

        statDropdown.setModel(new DefaultComboBoxModel<>(new String[]{
            "Building types",
            "Park rating",
            "Happiness"
        }));
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(statDropdown);
        
        chart = new JPanel();
        chart.setLayout(new CardLayout());
        pieChart = new PieChart();
        lineChart = new LineChart();
        chart.add(pieChart, "pie");
        chart.add(lineChart, "line");
        add(chart);
        
        statDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                switch((String)statDropdown.getSelectedItem()){
                    case "Building types":
                        pieChart.setData("Building types", gm.getStats().getBuildType());
                        ((CardLayout)(chart.getLayout())).show(chart, "pie");
                        break;
                    case "Park rating":
                        lineChart.setData("Park rating", "rating", "time", gm.getStats().getRatingHistory());
                        ((CardLayout)(chart.getLayout())).show(chart, "line");
                        break;
                    case "Happiness":
                        lineChart.setData("Happiness", "happiness", "time", gm.getStats().getHappinessHistory());
                        ((CardLayout)(chart.getLayout())).show(chart, "line");
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
