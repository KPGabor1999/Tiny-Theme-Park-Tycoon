package Idlethemeparkworld.view.popups;

import Idlethemeparkworld.model.GameManager;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;

public class FinancePanel extends JPanel {
    
    GameManager gm;
    JTextArea textArea;

    public FinancePanel(GameManager gm) {
        this.gm = gm;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel topPanel = new JPanel();
        topPanel.setMaximumSize(new Dimension(350, 50));
        
        JLabel title = new JLabel("Finances");

        JButton resetButton = new javax.swing.JButton();
        resetButton.setText("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.getFinance().resetFinanceData();
                refresh();
            }
        });
        topPanel.add(title);
        topPanel.add(resetButton);
       
        add(topPanel);

        textArea = new JTextArea(5, 20);
        JScrollPane scrollPane = new JScrollPane(textArea); 
        textArea.setEditable(false);
        refresh();
        add(scrollPane);
        this.setPreferredSize(new Dimension(400, 200));
        
        Timer timeTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refresh();
            }
        });
        timeTimer.start();
    }
    
    private void refresh(){
        textArea.setText(gm.getFinance().getFinancialDataString());
    }
}

