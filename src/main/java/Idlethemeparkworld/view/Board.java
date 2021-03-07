package Idlethemeparkworld.view;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.Park;
import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import Idlethemeparkworld.model.buildable.food.FoodStall;
import Idlethemeparkworld.model.buildable.infrastucture.Infrastructure;
import Idlethemeparkworld.model.buildable.infrastucture.Pavement;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Board extends JPanel {

    private Park park;
    private GridButton[][] buttonGrid;
    private final int CELL_SIZE = 32;

    public Board(Park park) {
        this.park = park;
        this.park.build(BuildType.TOILET, 2, 5);
        
        this.park.build(BuildType.PAVEMENT, 5, 0);
        this.park.build(BuildType.PAVEMENT, 5, 1);
        this.park.build(BuildType.PAVEMENT, 5, 2);
        this.park.build(BuildType.PAVEMENT, 5, 3);
        this.park.build(BuildType.PAVEMENT, 5, 4);
        this.park.build(BuildType.PAVEMENT, 5, 5);
        this.park.build(BuildType.PAVEMENT, 4, 5);
        this.park.build(BuildType.PAVEMENT, 3, 5);
        
        this.park.build(BuildType.CAROUSEL, 6, 1);
        
        setBackground(Color.BLACK);
        resizeMap(10, 10);
        updateMap();
    }

    public boolean refresh() {
        Dimension dim = new Dimension(10 * CELL_SIZE, 10 * CELL_SIZE);
        setPreferredSize(dim);
        setMaximumSize(dim);
        setSize(dim);
        repaint();
        return true;
    }
    
    private void resizeMap(int rows, int columns){
        removeAll();
        setLayout(new GridLayout(rows, columns));
        buttonGrid = new GridButton[rows][columns];
        for(int row=0; row<buttonGrid.length; row++){
            for(int column=0; column<buttonGrid[0].length; column++){
                addGridButton(row, column);
            }
        }
    }

    private void addGridButton(int row, int column){
        buttonGrid[row][column] = new GridButton();
        buttonGrid[row][column].setSize(32, 32);
        buttonGrid[row][column].addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                showBuildingOptions(row, column);
            }
        });
        add(buttonGrid[row][column]);
    }
    
    private void showBuildingOptions(int row, int column){
        Building currentBuilding = park.getTile(row, column).getBuilding();
        JFrame parentFrame = (JFrame) getRootPane().getParent();
        BuildingOptionsDialog buildingOptions = new BuildingOptionsDialog(parentFrame, currentBuilding);
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
                        gd.setBackground(Color.BLUE);
                    }
                    gd.changeTile(park.getTile(x, y).getBuilding().getName());
                }
                
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
    }
}
