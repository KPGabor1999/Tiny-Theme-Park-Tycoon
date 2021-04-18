package Idlethemeparkworld.view.popups;

import Idlethemeparkworld.misc.utils.Pair;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.agent.Visitor;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class VisitorsPanel extends JPanel {

    private final GameManager gm;
    private final JComboBox statDropdown;
    private final JPanel visitorDetails;
    private final JPanel noInfo;
    private final JPanel allInfo;
    
    private ArrayList<JLabel> info;
    
    private Visitor currentVisitor;

    public VisitorsPanel(GameManager gm) {
        this.gm = gm;
        JPanel topControl = new JPanel(new GridLayout(1, 2));
        
        statDropdown = new JComboBox<>();
        statDropdown.setMaximumSize(new Dimension(400, 50));
        
        JButton refreshButton = new javax.swing.JButton();
        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                VisitorsPanel.this.updateVisitorList();
            }
        });

        updateVisitorList();
        topControl.add(statDropdown);
        topControl.add(refreshButton);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(topControl);

        visitorDetails = new JPanel();
        visitorDetails.setLayout(new CardLayout());
        noInfo = new JPanel();
        noInfo.setLayout(new BoxLayout(noInfo, BoxLayout.PAGE_AXIS));
        noInfo.add(new JLabel("No data (visitor has left)."));
        allInfo = new JPanel();
        allInfo.setLayout(new GridLayout(11, 2));
        info = new ArrayList<>();
        addDoubleLabel("State: ");
        addDoubleLabel("Location: ");
        addDoubleLabel("Cash: ");
        addDoubleLabel("Cash spent: ");
        addDoubleLabel("Thoughts: ");
        addDoubleLabel("Current action: ");
        addDoubleLabel("Happiness: ");
        addDoubleLabel("Hunger: ");
        addDoubleLabel("Thirst: ");
        addDoubleLabel("Toilet: ");
        addDoubleLabel("Energy: ");
        
        visitorDetails.add(noInfo, "noinfo");
        visitorDetails.add(allInfo, "allinfo");
        add(visitorDetails);
        
        updateVisitor();
        updateVisitorDetail();

        statDropdown.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                updateVisitor();
                updateVisitorDetail();
            }
        });

        Timer timeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateVisitorDetail();
            }
        });
        timeTimer.start();
        
        this.setPreferredSize(new Dimension(400,500));
    }
    
    private void addDoubleLabel(String labelText){
        allInfo.add(new JLabel(labelText));
        JLabel content = new JLabel();
        info.add(content);
        allInfo.add(content);
    }
    
    private void updateVisitorList(){
        ArrayList<String> visitors = new ArrayList<>();
        gm.getAgentManager().getVisitors().forEach(visitor -> visitors.add(visitor.getID() + " " + visitor.getName()));
        statDropdown.setModel(new DefaultComboBoxModel<>(visitors.toArray()));
    }

    private void updateVisitor() {
        String selected = (String) statDropdown.getSelectedItem();
        String[] parts = selected.split(" ", 2);
        int id = Integer.parseInt(parts[0]);
        currentVisitor = gm.getAgentManager().getVisitor(id);
    }
    
    private void updateVisitorDetail() {
        if(currentVisitor == null){
            ((CardLayout) (visitorDetails.getLayout())).show(visitorDetails, "noinfo");
        } else {
            ArrayList<String> data = currentVisitor.getAllData();
            for (int i = 0; i < 11; i++) {
                info.get(i).setText(data.get(i));
                info.get(i).revalidate();
            }
            ((CardLayout) (visitorDetails.getLayout())).show(visitorDetails, "allinfo");
        }
    }
}
