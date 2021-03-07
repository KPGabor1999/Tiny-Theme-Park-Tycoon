package Idlethemeparkworld.view;

import Idlethemeparkworld.model.buildable.Building;
import java.awt.Frame;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

/**
 *
 * @author KrazyXL
 */
public class BuildingOptionsDialog extends JDialog{
    JLabel nameLabel;
    JLabel descriptionLabel;
    
    public BuildingOptionsDialog(Frame owner, Building currentBuilding){
        super(owner, "Building options");
        
        if(currentBuilding == null){        //Inkább BuildingType Enum azonosítók?
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
            this.setResizable(false);
            
            nameLabel = new JLabel("Grass");
            nameLabel.setAlignmentX(CENTER_ALIGNMENT);
            descriptionLabel = new JLabel("Free real estate to build buildings on.");
            descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);
            
            this.getContentPane().add(nameLabel);
            this.getContentPane().add(descriptionLabel);
            
            this.pack();
            
            int width = (Toolkit.getDefaultToolkit().getScreenSize().width - this.getWidth())/2;
            int height = (Toolkit.getDefaultToolkit().getScreenSize().height - this.getHeight())/2;
            this.setLocation(width, height);
        } 
    }
}
