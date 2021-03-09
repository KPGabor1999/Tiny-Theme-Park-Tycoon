package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.buildable.Building;

public abstract class Infrastructure extends Building {
    protected int capacity;
    protected int occupied;
    protected int duration;
    //protected int rubbish;

    public int getCapacity() {
        return capacity;
    }

    public int getOccupied() {
        return occupied;
    }

    public int getDuration() {
        return duration;
    }
    
    
    
    public void upgrade(){
    
    }
    
    public void clean(){
        
    }
    
    public void interact(){
    
    }
}
