package Idlethemeparkworld.view;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.JButton;

public class GridButton extends JButton{
    private static int DEFAULT_SIZE=64;
    
    private Color c;
    
    public GridButton(){
        super("");
        this.setSize(64, 64);
        setMargin(new Insets(0,0,0,0));
    }
    
    public void darken(){
        setBackground(getBackground().darker().darker());
    }
    
    public void setColor(Color c){
        this.c = c;
        setBackground(c);
    }
    
    public void resetColor(){
        setBackground(c);
    }
    
    public void changeTile(String type){
        setText(type);
    }
}
