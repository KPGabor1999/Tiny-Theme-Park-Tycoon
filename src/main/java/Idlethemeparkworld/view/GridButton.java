package Idlethemeparkworld.view;

import Idlethemeparkworld.model.Tile;
import Idlethemeparkworld.model.buildable.Building;
import javax.swing.JButton;

/**
 *
 * @author KrazyXL
 */
public class GridButton extends JButton{
    
    public GridButton(){
        super("");
    }
    
    public void changeTile(String type){
        setText(type);
    }
}
