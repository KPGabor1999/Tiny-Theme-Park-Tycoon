package Idlethemeparkworld;

import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class Main extends JFrame{  
    public Main() throws IOException{
        setTitle("Idle Theme Park World");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //URL url = MainWindow.class.getClassLoader().getResource("assets/icon.png");
        //setIconImage(Toolkit.getDefaultToolkit().getImage(url));
        
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
