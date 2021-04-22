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
import Idlethemeparkworld.view.InformationBar;
import Idlethemeparkworld.view.popups.CreditPanel;
import Idlethemeparkworld.view.popups.FinancePanel;
import Idlethemeparkworld.view.popups.StatsPanel;
import Idlethemeparkworld.view.popups.VisitorsPanel;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class Main extends JFrame {

    Font custom;
    private final InformationBar infoBar;
    private final JComboBox buildingChooser;
    private final JPanel controlPanel;
    private AdministrationDialog adminDialog;
    private Board board;
    private Timer gameEndTimer;

    GameManager gm;
    Highscores campaignHighscores;
    Highscores sandboxHighscores;

    public Board getBoard() {
        return board;
    }

    public Main() {
        createFont();
        setFont(new FontUIResource(new Font("Retro Gaming", Font.PLAIN, 14)));

        gm = new GameManager();
        //setUndecorated(true);
        setTitle("Tiny Theme Park Tycoon");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //setUndecorated(true);
        //URL url = MainWindow.class.getClassLoader().getResource("assets/icon.png");
        //setIconImage(Toolkit.getDefaultToolkit().getImage(url));

        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Game");

        JMenuItem menuNewGame = new JMenuItem(new AbstractAction("New game") {
            @Override
            public void actionPerformed(ActionEvent e) {
                startNewGame();
                board.refresh();
                board.drawParkRender();
                setControlPanel(true);
                gameEndTimer.restart();
            }
        });

        this.campaignHighscores = new Highscores(10, true, "campaign leaderboards");
        this.sandboxHighscores = new Highscores(10, false, "sandbox leaderboards");
        JMenuItem menuHighScores = new JMenuItem(new AbstractAction("Leaderboards") {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HighscoreWindow(campaignHighscores.getHighscores(), sandboxHighscores.getHighscores(), Main.this);
            }
        });

        JMenuItem statistics = new JMenuItem(new AbstractAction("Statistics") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Statistics");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new StatsPanel(gm));
                frame.pack();
                frame.setLocationRelativeTo(Main.this);
                frame.setVisible(true);
            }
        });
        
        JMenuItem finances = new JMenuItem(new AbstractAction("Finances") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Finances");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new FinancePanel(gm));
                frame.pack();
                frame.setLocationRelativeTo(Main.this);
                frame.setVisible(true);
            }
        });

        JMenuItem visitors = new JMenuItem(new AbstractAction("Visitors") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Visitors");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new VisitorsPanel(gm));
                frame.pack();
                frame.setLocationRelativeTo(Main.this);
                frame.setVisible(true);
            }
        });
        
        JMenuItem credit = new JMenuItem(new AbstractAction("Credits") {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame("Credits");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.getContentPane().add(new CreditPanel());
                frame.pack();
                frame.setResizable(false);
                frame.setLocationRelativeTo(Main.this);
                frame.setVisible(true);
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
        menuGame.add(statistics);
        menuGame.add(visitors);
        menuGame.add(finances);
        menuGame.addSeparator();
        menuGame.add(credit);
        menuGame.add(menuGameExit);
        menuBar.add(menuGame);
        setJMenuBar(menuBar);

        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        /*---------------------------------------------------------*/
        infoBar = new InformationBar(gm);
        add(infoBar);

        /*---------------------------------------------------------*/
        JButton buildButton = new javax.swing.JButton();
        JButton administrationButton = new javax.swing.JButton();
        JButton slowButton = new javax.swing.JButton();
        JButton pauseButton = new javax.swing.JButton();
        JButton accelerateButton = new javax.swing.JButton();

        buildingChooser = new javax.swing.JComboBox<>();

        buildingChooser.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{
            BuildType.PAVEMENT.getName() + " (" + BuildType.PAVEMENT.getBuildCost() + ")",
            BuildType.TRASHCAN.getName() + " (" + BuildType.TRASHCAN.getBuildCost() + ")",
            BuildType.TOILET.getName() + " (" + BuildType.TOILET.getBuildCost() + ")",
            BuildType.ICECREAMPARLOR.getName() + " (" + BuildType.ICECREAMPARLOR.getBuildCost() + ")",
            BuildType.HOTDOGSTAND.getName() + " (" + BuildType.HOTDOGSTAND.getBuildCost() + ")",
            BuildType.BURGERJOINT.getName() + " (" + BuildType.BURGERJOINT.getBuildCost() + ")",
            BuildType.CAROUSEL.getName() + " (" + BuildType.CAROUSEL.getBuildCost() + ")",
            BuildType.SWINGINGSHIP.getName() + " (" + BuildType.SWINGINGSHIP.getBuildCost() + ")",
            BuildType.HAUNTEDMANSION.getName() + " (" + BuildType.HAUNTEDMANSION.getBuildCost() + ")",
            BuildType.FERRISWHEEL.getName() + " (" + BuildType.FERRISWHEEL.getBuildCost() + ")",
            BuildType.ROLLERCOASTER.getName() + " (" + BuildType.ROLLERCOASTER.getBuildCost() + ")",}));

        buildButton.setText("Build");
        administrationButton.setText("Administration");
        slowButton.setText("<<");
        pauseButton.setText("||");
        accelerateButton.setText(">>");

        this.controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ((FlowLayout) controlPanel.getLayout()).setHgap(10);

        controlPanel.setBackground(Color.darkGray);
        controlPanel.add(buildingChooser);
        controlPanel.add(buildButton);
        controlPanel.add(administrationButton);
        controlPanel.add(slowButton);
        controlPanel.add(pauseButton);
        controlPanel.add(accelerateButton);
        controlPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        administrationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.this.adminDialog = new AdministrationDialog(Main.this, "Administration", board);
                adminDialog.setLocationRelativeTo(Main.this);
            }
        });

        buildButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] boxItem = ((String) buildingChooser.getSelectedItem()).split("\\(");
                String type = boxItem[0].trim();
                type = type.replaceAll("\\s+", "").toUpperCase();
                board.enterBuildMode(BuildType.valueOf(type));
            }
        });

        slowButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.decreaseGameSpeed();
            }
        });

        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.togglePause();
            }
        });

        accelerateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gm.increaseGameSpeed();
            }
        });

        add(controlPanel);

        JPanel gameArea = new JPanel();
        board = new Board(gm, this, gameArea);
        gm.setBoard(board);

        Dimension d = board.getPreferredSize();
        d.height *= 1.5;
        d.width *= 1.5;
        gameArea.setPreferredSize(d);
        gameArea.setBackground(new Color(0, 130, 14, 255));
        gameArea.setLayout(new GridBagLayout());
        gameArea.add(board);

        JScrollPane scroller = new JScrollPane(gameArea);
        scroller.setPreferredSize(new Dimension(600, 600));
        scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scroller.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        add(scroller);

        MouseAdapter ma = new MouseAdapter() {
            private Point origin;

            @Override
            public void mousePressed(MouseEvent e) {
                origin = new Point(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (origin != null) {
                    JViewport viewPort = (JViewport) SwingUtilities.getAncestorOfClass(JViewport.class, gameArea);
                    if (viewPort != null) {
                        int deltaX = origin.x - e.getX();
                        int deltaY = origin.y - e.getY();

                        Rectangle view = viewPort.getViewRect();
                        view.x += deltaX;
                        view.y += deltaY;

                        gameArea.scrollRectToVisible(view);
                    }
                }
            }
        };

        gameEndTimer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gm.gameOver()) {
                    String name = JOptionPane.showInputDialog(Main.this, "Game over! \n Please enter a name:");
                    setControlPanel(false);
                    sandboxHighscores.putHighscore(name, gm.getScore());
                    gameEndTimer.stop();
                }
                if(gm.gameWon()){
                    String name = JOptionPane.showInputDialog(Main.this, "You have completed the campaign! \n Please enter a name:");
                    setControlPanel(false);
                    campaignHighscores.putHighscore(name, gm.getScore());
                    int result = JOptionPane.showConfirmDialog(Main.this,"Do you want to continue in sandbox mode?", "Sandbox mode",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                    if(result == JOptionPane.YES_OPTION){
                        setControlPanel(true);
                        gm.enableSandbox();
                        gm.unFreeze();
                    } else {
                        gameEndTimer.stop();
                    } 
                }
            }
        });
        gameEndTimer.start();

        gameArea.addMouseListener(ma);
        gameArea.addMouseMotionListener(ma);

        setResizable(false);
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
    }

    public InformationBar getInfoBar() {
        return infoBar;
    }

    private void setControlPanel(boolean isEnabled) {
        controlPanel.setEnabled(isEnabled);

        Component[] components = controlPanel.getComponents();

        for (int i = 0; i < components.length; i++) {
            components[i].setEnabled(isEnabled);
        }
    }

    private void startNewGame() {
        gm.startNewGame();
    }

    public static void main(String[] args) {
        Main m = new Main();

        m.gm.startUpdateCycle();
    }

    private void setFont(FontUIResource myFont) {
        UIManager.put("CheckBoxMenuItem.acceleratorFont", myFont);
        UIManager.put("Button.font", myFont);
        UIManager.put("ToggleButton.font", myFont);
        UIManager.put("RadioButton.font", myFont);
        UIManager.put("CheckBox.font", myFont);
        UIManager.put("ColorChooser.font", myFont);
        UIManager.put("ComboBox.font", myFont);
        UIManager.put("Label.font", myFont);
        UIManager.put("List.font", myFont);
        UIManager.put("MenuBar.font", myFont);
        UIManager.put("Menu.acceleratorFont", myFont);
        UIManager.put("RadioButtonMenuItem.acceleratorFont", myFont);
        UIManager.put("MenuItem.acceleratorFont", myFont);
        UIManager.put("MenuItem.font", myFont);
        UIManager.put("RadioButtonMenuItem.font", myFont);
        UIManager.put("CheckBoxMenuItem.font", myFont);
        UIManager.put("OptionPane.buttonFont", myFont);
        UIManager.put("OptionPane.messageFont", myFont);
        UIManager.put("Menu.font", myFont);
        UIManager.put("PopupMenu.font", myFont);
        UIManager.put("OptionPane.font", myFont);
        UIManager.put("Panel.font", myFont);
        UIManager.put("ProgressBar.font", myFont);
        UIManager.put("ScrollPane.font", myFont);
        UIManager.put("Viewport.font", myFont);
        UIManager.put("TabbedPane.font", myFont);
        UIManager.put("Slider.font", myFont);
        UIManager.put("Table.font", myFont);
        UIManager.put("TableHeader.font", myFont);
        UIManager.put("TextField.font", myFont);
        UIManager.put("Spinner.font", myFont);
        UIManager.put("PasswordField.font", myFont);
        UIManager.put("TextArea.font", myFont);
        UIManager.put("TextPane.font", myFont);
        UIManager.put("EditorPane.font", myFont);
        UIManager.put("TabbedPane.smallFont", myFont);
        UIManager.put("TitledBorder.font", myFont);
        UIManager.put("ToolBar.font", myFont);
        UIManager.put("ToolTip.font", myFont);
        UIManager.put("Tree.font", myFont);
        UIManager.put("FormattedTextField.font", myFont);
        UIManager.put("IconButton.font", myFont);
        UIManager.put("InternalFrame.optionDialogTitleFont", myFont);
        UIManager.put("InternalFrame.paletteTitleFont", myFont);
        UIManager.put("InternalFrame.titleFont", myFont);
    }

    private static void createFont() {
        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, ClassLoader.getSystemClassLoader().getResourceAsStream("resources/RetroGaming.ttf")));
        } catch (IOException | FontFormatException e) {
            System.err.println(e);
        }
    }
}
