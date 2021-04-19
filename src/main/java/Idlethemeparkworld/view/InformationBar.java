package Idlethemeparkworld.view;

import Idlethemeparkworld.model.GameManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class InformationBar extends JPanel {

    private GameManager gm;
    private final JLabel timeLabel;
    private final JLabel moneyLabel;
    private final JLabel visitorCountLabel;
    private final JLabel happinessLabel;

    public InformationBar(GameManager gm) {
        this.gm = gm;
        timeLabel = new JLabel("time");
        moneyLabel = new JLabel("money");
        visitorCountLabel = new JLabel("visitor");
        happinessLabel = new JLabel("happiness");

        timeLabel.setForeground(Color.cyan);
        moneyLabel.setForeground(Color.GREEN);
        visitorCountLabel.setForeground(Color.RED);
        happinessLabel.setText("Happiness: 100%");
        happinessLabel.setForeground(Color.YELLOW);

        ((FlowLayout) getLayout()).setHgap(50);
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        setBackground(Color.darkGray);
        add(timeLabel);
        add(moneyLabel);
        add(visitorCountLabel);
        add(happinessLabel);

        updateInfobar();
        Timer timeTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInfobar();
            }
        });
        timeTimer.start();
    }

    public void updateInfobar() {
        timeLabel.setText(gm.getTime().toString());
        moneyLabel.setText(gm.getFinance().toString());
        visitorCountLabel.setText("Visitors: " + gm.getAgentManager().getVisitorCount());
        happinessLabel.setText("Happiness: " + String.format("%.02f", gm.getAgentManager().getVisitorHappinessRating()));
    }
}
