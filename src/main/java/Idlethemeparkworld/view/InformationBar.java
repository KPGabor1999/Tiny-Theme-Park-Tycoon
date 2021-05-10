package Idlethemeparkworld.view;

import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.Weather;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class InformationBar extends JPanel {

    private GameManager gm;
    private final JLabel timeLabel;
    private final JLabel moneyLabel;
    private final JLabel visitorCountLabel;
    private final JLabel happinessLabel;
    private final JLabel weatherLabel;

    public InformationBar(GameManager gm) {
        this.gm = gm;
        timeLabel = new JLabel("time");
        moneyLabel = new JLabel("money");
        visitorCountLabel = new JLabel("visitor");
        happinessLabel = new JLabel("happiness");
        weatherLabel = new JLabel();

        timeLabel.setForeground(Color.cyan);
        moneyLabel.setForeground(Color.GREEN);
        visitorCountLabel.setForeground(Color.RED);
        happinessLabel.setText("Happiness: 100%");
        happinessLabel.setForeground(Color.YELLOW);

        setLayout(new GridBagLayout());        
        GridBagConstraints gbc = new GridBagConstraints(); 

        setBackground(Color.darkGray);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 25;   
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(timeLabel,gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 25;   
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(moneyLabel,gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 25;   
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(visitorCountLabel,gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 25;   
        gbc.gridx = 3;
        gbc.gridy = 0;
        add(happinessLabel,gbc);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 25;   
        gbc.gridx = 4;
        gbc.gridy = 0;
        add(weatherLabel,gbc);

        updateInfobar();
        Timer timeTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTime();
            }
        });
        timeTimer.start();
        
        Timer dataTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInfobar();
            }
        });
        dataTimer.start();
    }

    public void updateTime(){
        timeLabel.setText(gm.getTime().toString());
    }
    
    public void updateInfobar() {
        moneyLabel.setText(gm.getFinance().toString());
        visitorCountLabel.setText("Visitors:" + gm.getAgentManager().getVisitorCount());
        happinessLabel.setText("Rating:" + String.format("%.02f", gm.getAgentManager().getVisitorHappinessRating()));
        ImageIcon icon = new ImageIcon(Weather.getInstance().getWeather().getAsset().getAsset());
        weatherLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
    }
}
