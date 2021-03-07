package Idlethemeparkworld.view;

import java.awt.Insets;
import javax.swing.JButton;

/**
 *
 * @author KrazyXL
 */
public class GridButton extends JButton{
    
    public GridButton(){
        super("");
        setMargin(new Insets(1,1,1,1));
    }
    
    public void changeTile(String type){
        setText(type);
    }
}
