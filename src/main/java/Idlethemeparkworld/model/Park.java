package Idlethemeparkworld.model;

import Idlethemeparkworld.model.buildable.Building;
import Idlethemeparkworld.model.buildable.attraction.Attraction;

public class Park {
    private int funds;
    private Tile[][] tiles;
    
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
    
    public Building getBuildings(){
        return null;
    }
    
    public Attraction getRandomAttraction(){
        return null;
    }
    
    public Building findBuilding(){
        return null;
    }
    
    //Build type will be an enum
    public boolean canBuild(int buildType){
        return true; 
    }
    
    public void build(){
        
    }
}
