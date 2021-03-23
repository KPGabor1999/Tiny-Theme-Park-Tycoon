package Idlethemeparkworld.model;

import Idlethemeparkworld.model.buildable.Building;

public class Tile {
    private int x, y;
    private Building building;
    
    public Tile(){
        this.x = 0;
        this.y = 0;
        this.building = null;
    }
    
    public Tile(int x, int y){
        this.x = x;
        this.y = y;
        this.building = null;
    }
    
    public boolean isEmpty(){
        return building == null;
    }
    
    public Building getBuilding(){
        return building;
    }
    
    public void setBuilding(Building building){
        this.building = building;
    }
    
    public void unsetBuilding(){
        building = null;
    }
}
