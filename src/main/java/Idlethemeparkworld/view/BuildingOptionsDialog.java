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
    JPanel statsPanel;
    JLabel nameLabel;
    JLabel descriptionLabel;
    JLabel capacityTextLabel;
    JLabel capacityNumberLabel;
    JLabel occupiedTextLabel;
    JLabel occupiedNumberLabel;
    JLabel foodPriceTextLabel;
    JLabel foodPriceNumberLabel;
    JLabel foodQualityTextLabel;
    JLabel foodQualityNumberLabel;
    JLabel upKeepCostTextLabel;
    JLabel upKeepCostNumberLabel;
    JLabel conditionTextLabel;
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
            descriptionLabel = new JLabel("Free real estate to build buildings on.");
            descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);
            
            this.getContentPane().add(nameLabel);
            this.getContentPane().add(descriptionLabel);
        } else if(currentBuilding instanceof Attraction){
        } else if(currentBuilding instanceof FoodStall){
            nameLabel = new JLabel(currentBuilding.getName());
            nameLabel.setAlignmentX(CENTER_ALIGNMENT);
            
            statsPanel = new JPanel(new GridLayout(6, 2));
            capacityTextLabel = new JLabel("Capacity: ");
            capacityNumberLabel = new JLabel(((FoodStall) currentBuilding).getCapacity() + "");
            occupiedTextLabel = new JLabel("Occupied: ");
            occupiedNumberLabel = new JLabel(((FoodStall) currentBuilding).getOccupied()+ "");
            foodPriceTextLabel = new JLabel("Food price: ");
            foodPriceNumberLabel = new JLabel(((FoodStall) currentBuilding).getFoodPrice()+ "");
            foodQualityTextLabel = new JLabel("Food quality: ");
            foodQualityNumberLabel = new JLabel(((FoodStall) currentBuilding).getFoodQuality()+ "");
            upKeepCostTextLabel = new JLabel("Upkeep cost: ");
            upKeepCostNumberLabel = new JLabel(((FoodStall) currentBuilding).getUpKeepCost()+ "");
            conditionTextLabel = new JLabel("Condition: ");
            conditionNumberLabel = new JLabel(((FoodStall) currentBuilding).getCondition()+ "");
            
            upgradeButton = new JButton("Upgrade: costs " + currentBuilding.getUpgradeCost() + "$");
            upgradeButton.setAlignmentX(CENTER_ALIGNMENT);
            //Fejlesztés gomb eseménykezelése
            
            demolishButton = new JButton("Demolish: returns " + currentBuilding.getBuildingCost()/2 + "$");
            demolishButton.setAlignmentX(CENTER_ALIGNMENT);
            //Lebontás gomb eseménykezelése
            
            this.getContentPane().add(nameLabel);
            statsPanel.add(capacityTextLabel);
            statsPanel.add(capacityNumberLabel);
            statsPanel.add(occupiedTextLabel);
            statsPanel.add(occupiedNumberLabel);
            statsPanel.add(foodPriceTextLabel);
            statsPanel.add(foodPriceNumberLabel);
            statsPanel.add(foodQualityTextLabel);
            statsPanel.add(foodQualityNumberLabel);
            statsPanel.add(upKeepCostTextLabel);
            statsPanel.add(upKeepCostNumberLabel);
            this.getContentPane().add(statsPanel);
            this.getContentPane().add(upgradeButton);
            this.getContentPane().add(demolishButton);
        } else if(currentBuilding instanceof Infrastructure){
        } else {
            System.err.println(currentBuilding.getName() + "Típusú épület nem létezhet.");
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
