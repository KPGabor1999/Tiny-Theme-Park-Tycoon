package Idlethemeparkworld.view;

import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;
import java.awt.Frame;
import java.awt.GridLayout;
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
    //Itt csak azok legyenek, amik mindegyik épülettípusra vonatkoznak, a többi legyen csak az if feltétel ágakban.
    JPanel statsPanel;
    JLabel nameLabel;
    JLabel capacityTextLabel;
    JLabel capacityNumberLabel;
    JLabel occupiedTextLabel;
    JLabel occupiedNumberLabel;
    JLabel upkeepCostTextLabel;
    JLabel upkeepCostNumberLabel;
    
    JLabel conditionTextLabel;      //Ez csak az attrakciókhoz kell.
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
            nameLabel = new JLabel(currentBuilding.getInfo().getName());
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
            
            upgradeButton = new JButton("Upgrade: costs " + currentBuilding.getInfo().getUpgradeCost() + "$");
            upgradeButton.setAlignmentX(CENTER_ALIGNMENT);
            //Fejlesztés gomb eseménykezelése
            
            demolishButton = new JButton("Demolish: returns " + currentBuilding.getInfo().getBuildCost()/2 + "$");
            demolishButton.setAlignmentX(CENTER_ALIGNMENT);
            //Lebontás gomb eseménykezelése
            
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
            nameLabel = new JLabel(currentBuilding.getInfo().getName());
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
            
            upgradeButton = new JButton("Upgrade: costs " + currentBuilding.getInfo().getUpgradeCost() + "$");
            upgradeButton.setAlignmentX(CENTER_ALIGNMENT);
            //Fejlesztés gomb eseménykezelése
            
            demolishButton = new JButton("Demolish: returns " + currentBuilding.getInfo().getBuildCost()/2 + "$");
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
            statsPanel.add(upkeepCostTextLabel);
            statsPanel.add(upkeepCostNumberLabel);
            this.getContentPane().add(statsPanel);
            this.getContentPane().add(upgradeButton);
            this.getContentPane().add(demolishButton);
        } else if(currentBuilding instanceof Infrastructure){
            nameLabel = new JLabel(currentBuilding.getInfo().getName());
            nameLabel.setAlignmentX(CENTER_ALIGNMENT);
            
            //Ha járda, description panel.
            if(currentBuilding instanceof Pavement){
                nameLabel = new JLabel("Pavement");
                nameLabel.setAlignmentX(CENTER_ALIGNMENT);
                JLabel descriptionLabel = new JLabel("Use pavement tiles to connect buildings and help people get around.");
                descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);
            
                this.getContentPane().add(nameLabel);
                this.getContentPane().add(descriptionLabel);
            } else if(currentBuilding instanceof TrashCan){
                statsPanel = new JPanel(new GridLayout(2, 2));
                capacityTextLabel = new JLabel("Capacity: ");
                capacityNumberLabel = new JLabel(((TrashCan) currentBuilding).getCapacity() + "");
                JLabel filledTextLabel = new JLabel("Filled: ");
                JLabel filledNumberLabel = new JLabel(((TrashCan) currentBuilding).getFilled() + "");
                
                statsPanel.add(capacityTextLabel);
                statsPanel.add(capacityNumberLabel);
                statsPanel.add(filledTextLabel);
                statsPanel.add(filledNumberLabel);
                this.getContentPane().add(statsPanel);
            } else if(currentBuilding instanceof Toilet){
                statsPanel = new JPanel(new GridLayout(3, 2));
                capacityTextLabel = new JLabel("Capacity: ");
                capacityNumberLabel = new JLabel(((Infrastructure) currentBuilding).getCapacity() + "");
                occupiedTextLabel = new JLabel("Occupied: ");
                occupiedNumberLabel = new JLabel(((Infrastructure) currentBuilding).getOccupied()+ "");
                JLabel cleanlinessTextLabel = new JLabel("Cleanliness: ");
                JLabel cleanlinessNumberLabel = new JLabel(((Toilet) currentBuilding).getCleanliness()+ "");
                
                statsPanel.add(capacityTextLabel);
                statsPanel.add(capacityNumberLabel);
                statsPanel.add(occupiedTextLabel);
                statsPanel.add(occupiedNumberLabel);
                statsPanel.add(cleanlinessTextLabel);
                statsPanel.add(cleanlinessNumberLabel);
                this.getContentPane().add(statsPanel);
            } else if(currentBuilding instanceof Entrance){
                nameLabel = new JLabel("Entrance");
                nameLabel.setAlignmentX(CENTER_ALIGNMENT);
                JLabel descriptionLabel = new JLabel("The entrance... what else can i say");
                descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);
            
                this.getContentPane().add(nameLabel);
                this.getContentPane().add(descriptionLabel);
            } 
            
            
            
            /*upgradeButton = new JButton("Upgrade: costs " + currentBuilding.getUpgradeCost() + "$");
            upgradeButton.setAlignmentX(CENTER_ALIGNMENT);
            //Fejlesztés gomb eseménykezelése
            
            demolishButton = new JButton("Demolish: returns " + currentBuilding.getBuildingCost()/2 + "$");
            demolishButton.setAlignmentX(CENTER_ALIGNMENT);
            //Lebontás gomb eseménykezelése*/
            
        } else {
            System.err.println(currentBuilding.getInfo().getName() + "Típusú épület nem létezhet.");
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
