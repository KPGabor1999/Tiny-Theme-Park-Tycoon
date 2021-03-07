package Idlethemeparkworld.view;

import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author KrazyXL
 */
public class BuildingOptionsDialog extends JDialog{
    //Itt csak azok legyenek, amik mindegyik �p�lett�pusra vonatkoznak, a t�bbi legyen csak az if felt�tel �gakban.
    JPanel statsPanel;
    JLabel nameLabel;
    JLabel capacityTextLabel;
    JLabel capacityNumberLabel;
    JLabel occupiedTextLabel;
    JLabel occupiedNumberLabel;
    JLabel upkeepCostTextLabel;
    JLabel upkeepCostNumberLabel;
    
    JLabel conditionTextLabel;      //Ez csak az attrakci�khoz kell.
    JLabel conditionNumberLabel;
    
    JButton upgradeButton;
    JButton demolishButton;
    
    public BuildingOptionsDialog(Frame owner, Building currentBuilding){
        super(owner, "Building options");
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
        this.setResizable(false);
        if(currentBuilding == null){
            nameLabel = new JLabel("Grass");
            nameLabel.setAlignmentX(CENTER_ALIGNMENT);
            JLabel descriptionLabel = new JLabel("Free real estate to build buildings on.");
            descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);
            
            this.getContentPane().add(nameLabel);
            this.getContentPane().add(descriptionLabel);
        } else if(currentBuilding instanceof Attraction){
            nameLabel = new JLabel(currentBuilding.getName());
            nameLabel.setAlignmentX(CENTER_ALIGNMENT);
            
            //isRunning
            statsPanel = new JPanel(new GridLayout(7, 2));
            JLabel funTextLabel = new JLabel("Fun: ");
            JLabel funNumberLabel = new JLabel(((Attraction) currentBuilding).getFun()+ "");
            capacityTextLabel = new JLabel("Capacity: ");
            capacityNumberLabel = new JLabel(((Attraction) currentBuilding).getCapacity() + "");
            occupiedTextLabel = new JLabel("Occupied: ");
            occupiedNumberLabel = new JLabel(((Attraction) currentBuilding).getOccupied() + "");
            JLabel runTimeTextLabel = new JLabel("Runtime: ");
            JLabel runTimeNumberLabel = new JLabel(((Attraction) currentBuilding).getRuntime() + "");
            JLabel entryFeeTextLabel = new JLabel("Entry fee: ");
            JLabel entryFeeNumberLabel = new JLabel(((Attraction) currentBuilding).getEntryFee() + "");
            upkeepCostTextLabel = new JLabel("Upkeep cost: ");
            upkeepCostNumberLabel = new JLabel(((Attraction) currentBuilding).getUpkeepCost() + "");
            JLabel conditionTextLabel = new JLabel("Condition: ");
            JLabel conditionNumberLabel = new JLabel(((Attraction) currentBuilding).getCondition() + "");
            
            upgradeButton = new JButton("Upgrade: costs " + currentBuilding.getUpgradeCost() + "$");
            upgradeButton.setAlignmentX(CENTER_ALIGNMENT);
            //Fejleszt�s gomb esem�nykezel�se
            
            demolishButton = new JButton("Demolish: returns " + currentBuilding.getBuildingCost()/2 + "$");
            demolishButton.setAlignmentX(CENTER_ALIGNMENT);
            //Lebont�s gomb esem�nykezel�se
            
            this.getContentPane().add(nameLabel);
            statsPanel.add(funTextLabel);
            statsPanel.add(funNumberLabel);
            statsPanel.add(capacityTextLabel);
            statsPanel.add(capacityNumberLabel);
            statsPanel.add(occupiedTextLabel);
            statsPanel.add(occupiedNumberLabel);
            statsPanel.add(runTimeTextLabel);
            statsPanel.add(runTimeNumberLabel);
            statsPanel.add(entryFeeTextLabel);
            statsPanel.add(entryFeeNumberLabel);
            statsPanel.add(upkeepCostTextLabel);
            statsPanel.add(upkeepCostNumberLabel);
            statsPanel.add(conditionTextLabel);
            statsPanel.add(conditionNumberLabel);
            this.getContentPane().add(statsPanel);
            this.getContentPane().add(upgradeButton);
            this.getContentPane().add(demolishButton);
        } else if(currentBuilding instanceof FoodStall){
            nameLabel = new JLabel(currentBuilding.getName());
            nameLabel.setAlignmentX(CENTER_ALIGNMENT);
            
            statsPanel = new JPanel(new GridLayout(5, 2));
            capacityTextLabel = new JLabel("Capacity: ");
            capacityNumberLabel = new JLabel(((FoodStall) currentBuilding).getCapacity() + "");
            occupiedTextLabel = new JLabel("Occupied: ");
            occupiedNumberLabel = new JLabel(((FoodStall) currentBuilding).getOccupied()+ "");
            JLabel foodPriceTextLabel = new JLabel("Food price: ");
            JLabel foodPriceNumberLabel = new JLabel(((FoodStall) currentBuilding).getFoodPrice()+ "");
            JLabel foodQualityTextLabel = new JLabel("Food quality: ");
            JLabel foodQualityNumberLabel = new JLabel(((FoodStall) currentBuilding).getFoodQuality()+ "");
            upkeepCostTextLabel = new JLabel("Upkeep cost: ");
            upkeepCostNumberLabel = new JLabel(((FoodStall) currentBuilding).getUpkeepCost()+ "");
            
            upgradeButton = new JButton("Upgrade: costs " + currentBuilding.getUpgradeCost() + "$");
            upgradeButton.setAlignmentX(CENTER_ALIGNMENT);
            //Fejleszt�s gomb esem�nykezel�se
            
            demolishButton = new JButton("Demolish: returns " + currentBuilding.getBuildingCost()/2 + "$");
            demolishButton.setAlignmentX(CENTER_ALIGNMENT);
            //Lebont�s gomb esem�nykezel�se
            
            this.getContentPane().add(nameLabel);
            statsPanel.add(capacityTextLabel);
            statsPanel.add(capacityNumberLabel);
            statsPanel.add(occupiedTextLabel);
            statsPanel.add(occupiedNumberLabel);
            statsPanel.add(foodPriceTextLabel);
            statsPanel.add(foodPriceNumberLabel);
            statsPanel.add(foodQualityTextLabel);
            statsPanel.add(foodQualityNumberLabel);
            statsPanel.add(upkeepCostTextLabel);
            statsPanel.add(upkeepCostNumberLabel);
            this.getContentPane().add(statsPanel);
            this.getContentPane().add(upgradeButton);
            this.getContentPane().add(demolishButton);
        } else if(currentBuilding instanceof Infrastructure){
        } else {
            System.err.println(currentBuilding.getName() + "T�pus� �p�let nem l�tezhet.");
        }
        
        /*{
            nameLabel = new JLabel(currentBuilding.getName());
            nameLabel.setAlignmentX(CENTER_ALIGNMENT);
            descriptionLabel = new JLabel("OWO.");
            descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);
            
            this.getContentPane().add(nameLabel);
            this.getContentPane().add(descriptionLabel);
        }*/
        this.pack();
    }
}
