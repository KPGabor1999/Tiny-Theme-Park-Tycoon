package Idlethemeparkworld.view;

import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Entrance;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.LockedTile;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import Idlethemeparkworld.model.buildable.infrastucture.Toilet;
import Idlethemeparkworld.model.buildable.infrastucture.TrashCan;
import Idlethemeparkworld.view.popups.Confirm;
import Idlethemeparkworld.view.popups.Notification;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javafx.util.Pair;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class BuildingOptionsDialog extends JDialog{
    private Board board;
    private Building currentBuilding;

    //Itt csak azok legyenek, amik mindegyik plettpusra vonatkoznak, a tbbi legyen csak az if felttel gakban.
    private JPanel statsPanel;
    private JLabel descriptionLabel;
    private JLabel nameLabel;
    private JLabel capacityTextLabel;
    private JLabel capacityNumberLabel;
    private JLabel occupiedTextLabel;
    private JLabel occupiedNumberLabel;
    private JLabel upkeepCostTextLabel;
    private JLabel upkeepCostNumberLabel;
    
    private JButton upgradeButton;
    private JButton demolishButton;

    private JButton unlockButton;
    
    private static int instanceCount;
    
    //Adjuk t az egsz parkot.
    public BuildingOptionsDialog(Frame owner, Board board, int x, int y){
        super(owner, "Building options");
        
        if(instanceCount == 0){
            instanceCount++;
            this.board = board;
            this.currentBuilding = board.getGameManager().getPark().getTile(x, y).getBuilding();
            //setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
                descriptionLabel = new JLabel("Free real estate to build buildings on.");
                descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);

                this.getContentPane().add(nameLabel);
                this.getContentPane().add(descriptionLabel);
            } else if(currentBuilding instanceof Attraction){
                nameLabel = new JLabel(currentBuilding.getInfo().getName() + " (Level " + currentBuilding.getCurrentLevel() + ")");
                nameLabel.setAlignmentX(CENTER_ALIGNMENT);

                //isRunning
                ArrayList<JLabel> infoLabels = new ArrayList<>();
                ArrayList<Pair<String, String>> info = currentBuilding.getAllData();
                statsPanel = new JPanel(new GridLayout(info.size(), 2));
                for(Pair<String, String> p : info){
                    statsPanel.add(new JLabel(p.getKey()));
                    statsPanel.add(new JLabel(p.getValue()));
                }

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
                //Lebonts gomb esemnykezelse

                this.getContentPane().add(nameLabel);
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
                //Fejleszts gomb esemnykezelse

                demolishButton = new JButton("Demolish: returns " + currentBuilding.getValue()/2 + "$");
                demolishButton.setAlignmentX(CENTER_ALIGNMENT);
                demolishButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        BuildingOptionsDialog.this.demolishBuilding();
                    }
                });
                //Lebonts gomb esemnykezelse

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

                //Ha jrda, description panel.
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
                    JLabel entryFeeLabel = new JLabel(String.valueOf(board.getPark().getEntranceFee()));
                    entryFeeLabel.setAlignmentX(CENTER_ALIGNMENT);
                    JLabel descriptionLabel = new JLabel("The entrance... what else can I say");
                    descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);

                    this.getContentPane().add(nameLabel);
                    this.getContentPane().add(entryFeeLabel);
                    this.getContentPane().add(descriptionLabel);
                } else if (currentBuilding instanceof LockedTile){
                    descriptionLabel = new JLabel("Unlock to use it as a building ground.");
                    descriptionLabel.setAlignmentX(CENTER_ALIGNMENT);

                    this.getContentPane().add(descriptionLabel);
                    //Felold gomb + esemnykezels
                }

                if(!(currentBuilding instanceof Entrance) && !(currentBuilding instanceof LockedTile)){
                    demolishButton = new JButton("Demolish: returns " + currentBuilding.getValue()/2 + "$");
                    demolishButton.setAlignmentX(CENTER_ALIGNMENT);
                    demolishButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            BuildingOptionsDialog.this.demolishBuilding();
                        }
                    });
                    this.getContentPane().add(demolishButton);
                } else if(currentBuilding instanceof LockedTile){
                    unlockButton = new JButton("Unlock for " + ((LockedTile)currentBuilding).getUnlockCost() + "$");
                    unlockButton.setAlignmentX(CENTER_ALIGNMENT);
                    unlockButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            BuildingOptionsDialog.this.instanceCount--;
                            BuildingOptionsDialog.this.dispose();
                            BuildingOptionsDialog.this.board.getGameManager().getPark().demolish(currentBuilding.getX(), currentBuilding.getY());
                            BuildingOptionsDialog.this.board.refresh();

                        }
                    });
                    this.getContentPane().add(unlockButton);
                }

                /*upgradeButton = new JButton("Upgrade: costs " + currentBuilding.getUpgradeCost() + "$");
                upgradeButton.setAlignmentX(CENTER_ALIGNMENT);
                //Fejleszts gomb esemnykezelse

                demolishButton = new JButton("Demolish: returns " + currentBuilding.getBuildingCost()/2 + "$");
                demolishButton.setAlignmentX(CENTER_ALIGNMENT);
                //Lebonts gomb esemnykezelse*/

            } else {
                System.err.println(currentBuilding.getInfo().getName() + "Tpus plet nem ltezhet.");
            }
            this.pack();
            this.setVisible(true);
        }
    }
    
    private void upgradeBuilding(){
        int funds = board.getGameManager().getFinance().getFunds();
        int upgradeCost = currentBuilding.getUpgradeCost();
        if(funds <= upgradeCost){
            new Notification(this.getOwner(), "Problem", "Insufficient funds for upgrade.");
        } else {
            board.getGameManager().getFinance().pay(upgradeCost);
            currentBuilding.upgrade();
            instanceCount--;
            this.dispose();
            new Notification(this.getOwner(), "Success", "Building successfully upgraded.");
        }
    }
    
    private void demolishBuilding(){
        new Confirm(this.getOwner(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuildingOptionsDialog.this.instanceCount--;
                BuildingOptionsDialog.this.dispose();
                BuildingOptionsDialog.this.board.getGameManager().getFinance().earn(currentBuilding.getValue()/2); //Az plet addigi teljes rtknek(!) a felt adja vissza.
                BuildingOptionsDialog.this.board.getGameManager().getPark().demolish(currentBuilding.getX(), currentBuilding.getY());
                BuildingOptionsDialog.this.board.refresh();
            }
        });
    }
}
