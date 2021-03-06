package Idlethemeparkworld.view;

import Idlethemeparkworld.model.Tile;
import Idlethemeparkworld.model.buildable.Building;
import javax.swing.JButton;

/**
 *
 * @author KrazyXL
 */
public class GridButton extends JButton{
    private Tile assignedTile;
    
    public GridButton(Tile assignedTile){
        super("GRASS");
        this.assignedTile = assignedTile ;
        
    }
}
