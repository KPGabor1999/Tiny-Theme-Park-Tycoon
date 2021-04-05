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
    
    public StatsPanel(GameManager gm){
        this.gm = gm;
        this.pieChart = new PieChart();
        statDropdown = new JComboBox<>();

        statDropdown.setModel(new DefaultComboBoxModel<>(new String[]{
            "Building types"}));
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(statDropdown);
        
        chart = new JPanel();
        chart.setLayout(new CardLayout());
        pieChart = new PieChart();
        chart.add(pieChart, "pie");
        add(chart);
        
        statDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                switch((String)statDropdown.getSelectedItem()){
                    case "Building types":
                        pieChart.setData("Building types", gm.getStats().getBuildType());
                        CardLayout cl = (CardLayout)(chart.getLayout());
                        cl.show(chart, "pie");
                        break;
                    default:
                        break;
                }
            }
        });
    }
}
