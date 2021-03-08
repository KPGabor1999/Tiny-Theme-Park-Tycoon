package Idlethemeparkworld.view;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JPanel {

    private Park park;
    private GridButton[][] buttonGrid;
    private final int CELL_SIZE = 32;
    
    private boolean buildMode;
    private BuildType type;
    private boolean[] canBuild;
    private int[] pos;

    public Board(Park park) {
        this.park = park;
        this.buildMode = false;
        this.type = null;
        this.pos = new int[2];
        this.canBuild = new boolean[1];
        
        this.park.build(BuildType.CAROUSEL, 0, 0);
        this.park.build(BuildType.FERRISWHEEL, 0, 1);
        this.park.build(BuildType.HAUNTEDMANSION, 0, 2);
        this.park.build(BuildType.ROLLERCOASTER, 0, 3);
        this.park.build(BuildType.SWINGINGSHIP, 0, 4);
        
        this.park.build(BuildType.BURGERJOINT, 1, 0);
        this.park.build(BuildType.HOTDOGSTAND, 1, 1);
        this.park.build(BuildType.ICECREAMPARLOR, 1, 2);
        
        this.park.build(BuildType.PAVEMENT, 2, 0);
        this.park.build(BuildType.TOILET, 2, 1);
        this.park.build(BuildType.TRASHCAN, 2, 2);
        
        resizeMap(park.getHeight(), park.getWidth());
        updateMap();
    }
    
    private void resizeMap(int rows, int columns){
        removeAll();
        setLayout(new GridLayout(rows, columns));
        buttonGrid = new GridButton[rows][columns];
        for(int y=0; y<buttonGrid.length; y++){
            for(int x=0; x<buttonGrid[0].length; x++){
                addGridButton(x, y);
            }
        }
    }

    private void addGridButton(int x, int y){
        buttonGrid[y][x] = new GridButton();
        buttonGrid[y][x].setSize(CELL_SIZE, CELL_SIZE);
        buttonGrid[y][x].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                showBuildingOptions(x, y);
            }
        });
        buttonGrid[y][x].addMouseListener(new MouseAdapter(){
            @Override
            public void mouseEntered(MouseEvent e) {
                if(buildMode){
                    canBuild[0]=park.canBuild(type, x, y);
                    pos[0]=x;
                    pos[1]=y;
                    System.out.println("("+x+","+y+") --- "+type.toString()+" --- "+(canBuild[0]?"Can build":"Can't build"));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if(buildMode){
                    if(canBuild[0]){
                        park.build(type, pos[0], pos[1]);
                        updateMap();
                        System.out.println("can build");
                        //Add new building
                        //Update playfield 
                    } else {
                        System.out.println("cannot build");
                    }
                    exitBuildMode();
                }
            }
        });
        add(buttonGrid[y][x]);
    }
    
    private void showBuildingOptions(int x, int y){
        Building currentBuilding = park.getTile(x, y).getBuilding();
        JFrame parentFrame = (JFrame) getRootPane().getParent();
        BuildingOptionsDialog buildingOptions = new BuildingOptionsDialog(parentFrame, currentBuilding);
        buildingOptions.setLocationRelativeTo(this);
        buildingOptions.setVisible(true);
    }
    
    private void updateMap() {
        for (int y = 0; y < park.getHeight(); y++) {
            for (int x = 0; x < park.getWidth(); x++) {
                GridButton gd = buttonGrid[y][x];
                if(park.getTile(x, y).isEmpty()){
                    gd.setBackground(Color.green);
                    gd.changeTile("Grass");
                } else {
                    Building b = park.getTile(x, y).getBuilding();
                    if(b instanceof Pavement){
                        gd.setBackground(Color.LIGHT_GRAY);
                    }else if(b instanceof Attraction){
                        gd.setBackground(Color.RED);
                    } else if(b instanceof FoodStall){
                        gd.setBackground(Color.ORANGE);
                    } else if(b instanceof Infrastructure){
                        gd.setBackground(Color.CYAN);
                    }
                    gd.changeTile(park.getTile(x, y).getBuilding().getName());
                }
            }
        }
    }
    
    public void enterBuildMode(BuildType type){
        buildMode = true;
        this.type = type;
        Component[] comps = getComponents();
        for (Component component : comps) {
            component.setEnabled(false);
        }
    }
    
    public void exitBuildMode(){
        buildMode = false;
        this.type = null;
        Component[] comps = getComponents();
        for (Component component : comps) {
            component.setEnabled(true);
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
    }
}
