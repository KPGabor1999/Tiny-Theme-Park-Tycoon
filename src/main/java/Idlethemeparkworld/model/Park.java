package Idlethemeparkworld.model;

import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;
import java.util.ArrayList;

public class Park {
    private int funds;
    private Tile[][] tiles;
    private ArrayList<Building> buildings;
    
    public Park(){
        tiles = new Tile[10][10];
        initializePark();
    }
    
    public Park(int size){
        tiles = new Tile[size][size];
        initializePark();
    }
    
    private void initializePark(){
        //1.Make sure all tiles are empty
        this.buildings = new ArrayList<>();
        for(int row=0; row<tiles.length; row++){
            for(int column=0; column<tiles[0].length; column++){
                tiles[row][column] = new Tile(column, row);
            }
        }
        //2.Spawn in the gate tile
    }
    
    public Tile getTile(int x, int y){
        return tiles[y][x];
    }
    
    public ArrayList<Building> getBuildings(){
        return buildings;
    }
    
    public Building findBuilding(String type){
        for(int i=0; i<buildings.size(); i++){
            if(buildings.get(i).getName().equals(type)){
                return buildings.get(i);
            }
        }
        return null;
    }
    
    //Build type will be an enum
    public boolean canBuild(BuildType type){
        return true; 
    }
    
    public void build(){
        
    }
}
