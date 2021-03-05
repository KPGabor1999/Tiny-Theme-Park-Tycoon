package Idlethemeparkworld;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Main extends JFrame{  
    private final JLabel timeLabel; 
    private final JLabel moneyLabel; 
    private final JLabel visitorCountLabel;
    private final JLabel happinessLabel; 
    public Main() throws IOException{
        setTitle("Idle Theme Park World");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //URL url = MainWindow.class.getClassLoader().getResource("assets/icon.png");
        //setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");

        JMenuItem menuNewGame = new JMenuItem(new AbstractAction("New game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //startNewGame();
            }
        });
        
        JMenuItem menuHighScores = new JMenuItem(new AbstractAction("Leaderboards") {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });
        
        JMenuItem menuGameExit = new JMenuItem(new AbstractAction("Exit") {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        menuGame.add(menuNewGame);
        menuGame.add(menuHighScores);
        menuGame.addSeparator();
        menuGame.add(menuGameExit);
        menuBar.add(menuGame);
        setJMenuBar(menuBar);
        
        setLayout(new BorderLayout(0, 10));
        
        /*---------------------------------------------------------*/
        
        timeLabel = new JLabel("time");
        moneyLabel = new JLabel("money");
        visitorCountLabel = new JLabel("visitor");
        happinessLabel = new JLabel("happiness");
        
        timeLabel.setText("time:0");
        timeLabel.setForeground(Color.cyan);
        moneyLabel.setText("money:1000");
        moneyLabel.setForeground(Color.GREEN);
        visitorCountLabel.setText("visitor:20");
        visitorCountLabel.setForeground(Color.RED);
        happinessLabel.setText("happiness:100%");
        happinessLabel.setForeground(Color.YELLOW);

        JPanel informationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ((FlowLayout)informationPanel.getLayout()).setHgap(50);
        informationPanel.setBackground(Color.darkGray);
        informationPanel.add(timeLabel);
        informationPanel.add(moneyLabel);
        informationPanel.add(visitorCountLabel);
        informationPanel.add(happinessLabel);
        
        add(informationPanel, BorderLayout.NORTH);
        
        setLocationRelativeTo(null);
        //pack();
        setVisible(true);
    }
    
    public static void main(String[] args) {
        try {
            new Main();
        } catch (IOException ex) {}
    }    
}
