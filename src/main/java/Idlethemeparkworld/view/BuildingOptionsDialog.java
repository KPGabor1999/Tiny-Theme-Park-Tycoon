package Idlethemeparkworld.view;

import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author KrazyXL
 */
public class BuildingOptionsDialog extends JDialog{
    private Park park;
    private Building currentBuilding;

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
    
    //Adjuk �t az eg�sz parkot.
    public BuildingOptionsDialog(Frame owner, Park park, int x, int y){
        super(owner, "Building options");
        
        this.park = park;
        this.currentBuilding = park.getTile(x, y).getBuilding();
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
            nameLabel = new JLabel(currentBuilding.getInfo().getName() + " (Level " + currentBuilding.getCurrentLevel() + ")");
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
            upgradeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BuildingOptionsDialog.this.upgrade();
                }
            });
            
            if(((Attraction)currentBuilding).getCurrentLevel() == ((Attraction)currentBuilding).getMaxLevel()){
                upgradeButton.setEnabled(false);
            }
            //Fejleszt�s gomb esem�nykezel�se
            
            demolishButton = new JButton("Demolish: returns " + currentBuilding.getInfo().getBuildCost()/2 + "$");
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
            //Fejleszt�s gomb esem�nykezel�se
            
            demolishButton = new JButton("Demolish: returns " + currentBuilding.getInfo().getBuildCost()/2 + "$");
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
            nameLabel = new JLabel(currentBuilding.getInfo().getName());
            nameLabel.setAlignmentX(CENTER_ALIGNMENT);
            
            //Ha j�rda, description panel.
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
            //Fejleszt�s gomb esem�nykezel�se
            
            demolishButton = new JButton("Demolish: returns " + currentBuilding.getBuildingCost()/2 + "$");
            demolishButton.setAlignmentX(CENTER_ALIGNMENT);
            //Lebont�s gomb esem�nykezel�se*/
            
        } else {
            System.err.println(currentBuilding.getInfo().getName() + "T�pus� �p�let nem l�tezhet.");
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
    
    private void upgrade(){
        System.out.println("L�tom a k�ls� oszt�lyt!");
        int funds = park.getFunds();
        int upgradeCost = currentBuilding.getUpgradeCost();
        if(funds <= upgradeCost){
            JDialog insufficientFundsDialog = new JDialog(this.getOwner(), "Probl�ma");
            insufficientFundsDialog.setLayout(new BoxLayout(insufficientFundsDialog.getContentPane(), BoxLayout.Y_AXIS));
            insufficientFundsDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            insufficientFundsDialog.setSize(200, 60);
            
            JLabel errorMessage = new JLabel("Insufficient funds for upgrade.");
            errorMessage.setAlignmentX(CENTER_ALIGNMENT);
            
            insufficientFundsDialog.getContentPane().add(errorMessage);
            
            this.pack();
            
            int XLocation = (Toolkit.getDefaultToolkit().getScreenSize().width - insufficientFundsDialog.getWidth())/2;
            int YLocation = (Toolkit.getDefaultToolkit().getScreenSize().height - insufficientFundsDialog.getHeight())/2;
            insufficientFundsDialog.setLocation(XLocation, YLocation);
            
            insufficientFundsDialog.setVisible(true);
        } else {
            park.spend(upgradeCost);
            currentBuilding.upgrade();
            this.dispose();
            JDialog upgradeSuccessfulDialog = new JDialog(this.getOwner(), "Siker");
            upgradeSuccessfulDialog.setLayout(new BoxLayout(upgradeSuccessfulDialog.getContentPane(), BoxLayout.Y_AXIS));
            upgradeSuccessfulDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            upgradeSuccessfulDialog.setSize(200, 60);
            
            JLabel sucessMessage = new JLabel("Building successfully upgraded.");
            sucessMessage.setAlignmentX(CENTER_ALIGNMENT);
            
            upgradeSuccessfulDialog.getContentPane().add(sucessMessage);
            
            this.pack();
            
            int XLocation = (Toolkit.getDefaultToolkit().getScreenSize().width - upgradeSuccessfulDialog.getWidth())/2;
            int YLocation = (Toolkit.getDefaultToolkit().getScreenSize().height - upgradeSuccessfulDialog.getHeight())/2;
            upgradeSuccessfulDialog.setLocation(XLocation, YLocation);
            
            upgradeSuccessfulDialog.setVisible(true);
            //Sikeres fejleszt�s �zenet
        }
    }
}
