package Idlethemeparkworld;

import Idlethemeparkworld.misc.Highscores;
import Idlethemeparkworld.view.HighscoreWindow;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import Idlethemeparkworld.view.AdministrationDialog;

public class Main extends JFrame{  
    private final JLabel timeLabel; 
    private final JLabel moneyLabel; 
    private final JLabel visitorCountLabel;
    private final JLabel happinessLabel; 
    private final JButton[][] buttonGrid;
    Highscores highscores;
    
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
        
        this.highscores = new Highscores(10);
        JMenuItem menuHighScores = new JMenuItem(new AbstractAction("Leaderboards") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighscoreWindow(highscores.getHighscores(), Main.this);
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
        
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        
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
        informationPanel.setMaximumSize( new Dimension(Integer.MAX_VALUE,50));
        
        informationPanel.setBackground(Color.darkGray);
        informationPanel.add(timeLabel);
        informationPanel.add(moneyLabel);
        informationPanel.add(visitorCountLabel);
        informationPanel.add(happinessLabel);
        
        add(informationPanel);
        
        /*---------------------------------------------------------*/
        
        JComboBox buildingChooser = new javax.swing.JComboBox<>();
        JButton buildButton = new javax.swing.JButton();
        JButton administrationButton = new javax.swing.JButton();
        JButton slowButton = new javax.swing.JButton();
        JButton pauseButton = new javax.swing.JButton();
        JButton accelerateButton = new javax.swing.JButton();
        
        buildingChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Trash can",
            "Bathrooms",
            "Hot dog stand",
            "Ice cream parlor",
            "Burger joint",
            "Carousel",
            "Ferris Wheel",
            "Swinging Ship",
            "Roller coaster",
            "Haunted mansion"}));
        
        buildButton.setText("Build");
        administrationButton.setText("Administration");
        slowButton.setText("<<");
        pauseButton.setText("||");
        accelerateButton.setText(">>");

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ((FlowLayout)controlPanel.getLayout()).setHgap(20);
        informationPanel.setMaximumSize( new Dimension(Integer.MAX_VALUE,50));
        
        controlPanel.setBackground(Color.darkGray);
        controlPanel.add(buildingChooser);
        controlPanel.add(buildButton);
        controlPanel.add(administrationButton);
        controlPanel.add(slowButton);
        controlPanel.add(pauseButton);
        controlPanel.add(accelerateButton);
        controlPanel.setMaximumSize( new Dimension(Integer.MAX_VALUE,50));
        
        administrationButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                setPricesAndEmployees();
            }
            
        });
        
        add(controlPanel);
        
        JPanel buildingPanel = new JPanel(new GridLayout(10, 10));              //Játéktábla legenerálása.
        buttonGrid = new JButton[10][10];
        for(int row=0; row<buttonGrid.length; row++){
            for(int column=0; column<buttonGrid[0].length; column++){
                JButton currentButton = buttonGrid[row][column];
                currentButton = new JButton("B");
                currentButton.setSize(32, 32);
                currentButton.setBackground(Color.green);
                buildingPanel.add(currentButton);
            }
        }
        add(buildingPanel);
        System.out.println("buttonGrid generation complete!");
        
        setLocationRelativeTo(null);
        //pack();
        setVisible(true);
    }
    
    public void setPricesAndEmployees(){
        AdministrationDialog adminDialog = new AdministrationDialog(this, "Administration");
        adminDialog.setVisible(true);
    }
    
    public static void main(String[] args) {
        try {
            new Main();
        } catch (IOException ex) {}
    }    
}
