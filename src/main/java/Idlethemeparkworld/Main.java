package Idlethemeparkworld;

import Idlethemeparkworld.misc.Highscores;
import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.view.HighscoreWindow;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;
import Idlethemeparkworld.view.AdministrationDialog;
import Idlethemeparkworld.view.Board;
import javax.swing.Timer;

public class Main extends JFrame{  
    private final JLabel timeLabel; 
    private final JLabel moneyLabel; 
    private final JLabel visitorCountLabel;
    private final JLabel happinessLabel; 
    private final JComboBox buildingChooser;
    private AdministrationDialog adminDialog;
    private Board board;
    
    GameManager gm;
    Highscores highscores;
    
    public Board getBoard(){
        return board;
    }
    
    public Main() {
        gm = new GameManager();
        
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
                startNewGame();
                board.refresh();
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

        JPanel informationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ((FlowLayout)informationPanel.getLayout()).setHgap(50);
        informationPanel.setMaximumSize( new Dimension(Integer.MAX_VALUE,50));
        
        informationPanel.setBackground(Color.darkGray);
        informationPanel.add(timeLabel);
        informationPanel.add(moneyLabel);
        informationPanel.add(visitorCountLabel);
        informationPanel.add(happinessLabel);
        
        add(informationPanel);
        
        updateInfobar();
        Timer timeTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateInfobar();
            }
        });
        timeTimer.start();
        
        /*---------------------------------------------------------*/
        
        JButton buildButton = new javax.swing.JButton();
        JButton administrationButton = new javax.swing.JButton();
        JButton slowButton = new javax.swing.JButton();
        JButton pauseButton = new javax.swing.JButton();
        JButton accelerateButton = new javax.swing.JButton();
        
        buildingChooser = new javax.swing.JComboBox<>();
        /*buildingChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            "Pavement",
            "Trash can",
            "Toilet",
            "Hot dog stand",
            "Ice cream parlor",
            "Burger joint",
            "Carousel",
            "Ferris Wheel",
            "Swinging Ship",
            "Roller coaster",
            "Haunted mansion"}));*/
        
        buildingChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {
            BuildType.PAVEMENT.getName() + " (" + BuildType.PAVEMENT.getBuildCost() + ")",
            BuildType.TRASHCAN.getName() + " (" + BuildType.TRASHCAN.getBuildCost() + ")",
            BuildType.TOILET.getName() + " (" + BuildType.TOILET.getBuildCost() + ")",
            BuildType.HOTDOGSTAND.getName() + " (" + BuildType.HOTDOGSTAND.getBuildCost() + ")",
            BuildType.ICECREAMPARLOR.getName() + " (" + BuildType.ICECREAMPARLOR.getBuildCost() + ")",
            BuildType.BURGERJOINT.getName() + " (" + BuildType.BURGERJOINT.getBuildCost() + ")",
            BuildType.CAROUSEL.getName() + " (" + BuildType.CAROUSEL.getBuildCost() + ")",
            BuildType.FERRISWHEEL.getName() + " (" + BuildType.FERRISWHEEL.getBuildCost() + ")",
            BuildType.SWINGINGSHIP.getName() + " (" + BuildType.SWINGINGSHIP.getBuildCost() + ")",
            BuildType.ROLLERCOASTER.getName() + " (" + BuildType.ROLLERCOASTER.getBuildCost() + ")",
            BuildType.HAUNTEDMANSION.getName() + " (" + BuildType.HAUNTEDMANSION.getBuildCost() + ")",}));
        
        buildButton.setText("Build");
        administrationButton.setText("Administration");
        slowButton.setText("<<");
        pauseButton.setText("||");
        accelerateButton.setText(">>");

        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ((FlowLayout)controlPanel.getLayout()).setHgap(10);
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
                Main.this.adminDialog = new AdministrationDialog(Main.this, "Administration", board);
                //adminDialog.setVisible(true);
            }
        });
        
        buildButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] boxItem = ((String)buildingChooser.getSelectedItem()).split("\\(");
                String type = boxItem[0].trim();
                type = type.replaceAll("\\s+","").toUpperCase();
                board.enterBuildMode(BuildType.valueOf(type));
            }
        });
        
        slowButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.decreaseGameSpeed();
            }
        });
        
        pauseButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.togglePause();
            }
        });
        
        accelerateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.increaseGameSpeed();
            }
        });
        
        add(controlPanel);

        board = new Board(gm, buildButton, this);

        add(board);
        
        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }
    
    public void updateInfobar(){
        //System.out.println(gm.getTime().toString());
        timeLabel.setText(gm.getTime().toString());
        moneyLabel.setText(gm.getFinance().toString());
    }
    
    private void startNewGame(){
        gm.startNewGame();
    }
    
    public static void main(String[] args) {
        Main m = new Main();
        
        m.gm.startUpdateCycle();
    }    
}
