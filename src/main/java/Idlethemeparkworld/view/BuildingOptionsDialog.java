package Idlethemeparkworld.view;

import Idlethemeparkworld.Main;
import Idlethemeparkworld.model.GameManager;
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
    private Board board;
    private Building currentBuilding;

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
    JDialog insufficientFundsDialog;
    JDialog upgradeSuccessfulDialog;
    JButton demolishButton;
    JDialog confirmDemolitionDialog;
    
    private static int instanceCount;
    
    //Adjuk át az egész parkot.
    public BuildingOptionsDialog(Frame owner, Board board, int x, int y){
        super(owner, "Building options");
        
        if(instanceCount == 0){
            instanceCount++;
            this.board = board;
            this.currentBuilding = board.getGameManager().getPark().getTile(x, y).getBuilding();
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    instanceCount--;
                    BuildingOptionsDialog.this.dispose();
                }
                
            });
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
                        BuildingOptionsDialog.this.upgradeBuilding();
                    }
                });

                if(((Attraction)currentBuilding).getCurrentLevel() == ((Attraction)currentBuilding).getMaxLevel()){
                    upgradeButton.setEnabled(false);
                }

                demolishButton = new JButton("Demolish: returns " + currentBuilding.getValue()/2 + "$");
                demolishButton.setAlignmentX(CENTER_ALIGNMENT);
                demolishButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BuildingOptionsDialog.this.demolishBuilding();
                    }
                });
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
                nameLabel = new JLabel(currentBuilding.getInfo().getName() + " (Level " + currentBuilding.getCurrentLevel() + ")");
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
                upgradeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BuildingOptionsDialog.this.upgradeBuilding();
                    }
                });

                if(((FoodStall)currentBuilding).getCurrentLevel() == ((FoodStall)currentBuilding).getMaxLevel()){
                    upgradeButton.setEnabled(false);
                }
                //Fejlesztés gomb eseménykezelése

                demolishButton = new JButton("Demolish: returns " + currentBuilding.getValue()/2 + "$");
                demolishButton.setAlignmentX(CENTER_ALIGNMENT);
                demolishButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BuildingOptionsDialog.this.demolishBuilding();
                    }
                });
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

                if(!(currentBuilding instanceof Entrance)){
                    demolishButton = new JButton("Demolish: returns " + currentBuilding.getValue()/2 + "$");
                    demolishButton.setAlignmentX(CENTER_ALIGNMENT);
                    demolishButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            BuildingOptionsDialog.this.demolishBuilding();
                        }
                    });
                    this.getContentPane().add(demolishButton);
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
            this.pack();
            this.setVisible(true);
        }
    }
    
    private void upgradeBuilding(){
        int funds = board.getGameManager().getFinance().getFunds();
        int upgradeCost = currentBuilding.getUpgradeCost();
        if(funds <= upgradeCost){
            insufficientFundsDialog = new JDialog(this.getOwner(), "Problem");
            insufficientFundsDialog.setLayout(new BoxLayout(insufficientFundsDialog.getContentPane(), BoxLayout.Y_AXIS));
            insufficientFundsDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            
            JLabel errorMessage = new JLabel("Insufficient funds for upgrade.");
            errorMessage.setAlignmentX(CENTER_ALIGNMENT);
            JButton OKButton = new JButton("OK");
            OKButton.setAlignmentX(CENTER_ALIGNMENT);
            OKButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BuildingOptionsDialog.this.insufficientFundsDialog.dispose();
                }
            });
            
            insufficientFundsDialog.getContentPane().add(errorMessage);
            insufficientFundsDialog.getContentPane().add(OKButton);
            
            insufficientFundsDialog.pack();
            
            int xLocation = this.getOwner().getX() + this.getOwner().getWidth()/2 - insufficientFundsDialog.getWidth()/2;
            int yLocation = this.getOwner().getY() + this.getOwner().getHeight()/2 - insufficientFundsDialog.getHeight()/2;
            insufficientFundsDialog.setLocation(xLocation, yLocation);
            
            insufficientFundsDialog.setVisible(true);
        } else {
            board.getGameManager().getFinance().pay(upgradeCost);
            currentBuilding.upgrade();
            instanceCount--;
            this.dispose();
            upgradeSuccessfulDialog = new JDialog(this.getOwner(), "Success");
            upgradeSuccessfulDialog.setLayout(new BoxLayout(upgradeSuccessfulDialog.getContentPane(), BoxLayout.Y_AXIS));
            upgradeSuccessfulDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            
            JLabel sucessMessage = new JLabel("Building successfully upgraded.");
            sucessMessage.setAlignmentX(CENTER_ALIGNMENT);
            JButton OKButton = new JButton("OK");
            OKButton.setAlignmentX(CENTER_ALIGNMENT);
            OKButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    BuildingOptionsDialog.this.upgradeSuccessfulDialog.dispose();
                }
            });
            
            upgradeSuccessfulDialog.getContentPane().add(sucessMessage);
            upgradeSuccessfulDialog.getContentPane().add(OKButton);
            
            upgradeSuccessfulDialog.pack();
            
            int xLocation = this.getOwner().getX() + this.getOwner().getWidth()/2 - upgradeSuccessfulDialog.getWidth()/2;
            int yLocation = this.getOwner().getY() + this.getOwner().getHeight()/2 - upgradeSuccessfulDialog.getHeight()/2;
            upgradeSuccessfulDialog.setLocation(xLocation, yLocation);
            
            upgradeSuccessfulDialog.setVisible(true);
        }
    }
    
    private void demolishBuilding(){
        confirmDemolitionDialog = new JDialog(this, "Confirm");    //this?
        confirmDemolitionDialog.setLayout(new BoxLayout(confirmDemolitionDialog.getContentPane(), BoxLayout.Y_AXIS));
        
        JLabel confirmDemolitionLabel = new JLabel("Are you sure?");
        confirmDemolitionLabel.setAlignmentX(CENTER_ALIGNMENT);
        JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton yesButton = new JButton("YES");
        yesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuildingOptionsDialog.this.confirmDemolitionDialog.dispose();
                BuildingOptionsDialog.this.instanceCount--;
                BuildingOptionsDialog.this.dispose();
                BuildingOptionsDialog.this.board.getGameManager().getFinance().earn(currentBuilding.getValue()/2); //Az épület addigi teljes értékének(!) a felét adja vissza.
                BuildingOptionsDialog.this.board.getGameManager().getPark().demolish(currentBuilding.getxLocation(), currentBuilding.getyLocation());
                BuildingOptionsDialog.this.board.refresh();
            }
        });
        JButton noButton = new JButton("NO");
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuildingOptionsDialog.this.confirmDemolitionDialog.dispose();
            }
        });
        
        confirmDemolitionDialog.getContentPane().add(confirmDemolitionLabel);
        optionsPanel.add(yesButton);
        optionsPanel.add(noButton);
        confirmDemolitionDialog.getContentPane().add(optionsPanel);
        confirmDemolitionDialog.pack();
        int xLocation = this.getOwner().getX() + this.getOwner().getWidth()/2 - confirmDemolitionDialog.getWidth()/2;
        int yLocation = this.getOwner().getY() + this.getOwner().getHeight()/2 - confirmDemolitionDialog.getHeight()/2;
        confirmDemolitionDialog.setLocation(xLocation, yLocation);
        
        confirmDemolitionDialog.setVisible(true);
        
        //Csak akkor történik meg, ha a játékos biztos benne:
        /**/
    }
}
