package Idlethemeparkworld;

import Idlethemeparkworld.misc.Highscores;
import Idlethemeparkworld.view.HighscoreWindow;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.WindowConstants;

public class Main extends JFrame{  
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
