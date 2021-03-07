package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.buildable.Building;

public abstract class Attraction extends Building {
    protected int fun;
    protected int capacity;
    protected int occupied;
    protected int runtime;      //seconds?
    protected int entryFee;
    protected boolean isRunning;
    protected int condition;    //gets lower after each run

    public int getFun() {
        return fun;
    }

    public int getCapacity() {
        return capacity;
    }
    
    public int getOccupied() {
        return occupied;
    }

    public int getRuntime() {
        return runtime;
    }

    public int getEntryFee() {
        return entryFee;
    }

    public boolean isIsRunning() {
        return isRunning;
    }

    public int getCondition() {
        return condition;
    }
    
    
    
    public void upgrade(){
   
    }
    
    public void interact(){
        
    }
    
    //consider using an observer/event listener
    protected void start(){
        
    }
}
