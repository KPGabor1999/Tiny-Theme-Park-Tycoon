package Idlethemeparkworld.view;

import java.awt.Color;
import java.awt.Insets;
import javax.swing.JButton;

public class GridButton extends JButton{
    private Color c;
    
    public GridButton(){
        super("");
        setMargin(new Insets(1,1,1,1));
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
