package Idlethemeparkworld.model;

import Idlethemeparkworld.model.buildable.Building;

public class Tile {
    private int x, y;
    private boolean isBase;
    private Building building;
    
    public Tile(){
        
    }
    
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.isBase = false;
        this.building = null;
    }
    
    public Building getBuilding(){
        return building;
    }
    
    public void setBuilding(boolean isBase){
        
    }
    
    public void unsetBuilding(){
        
    }
}
