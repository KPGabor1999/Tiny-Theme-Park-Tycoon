package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.buildable.Building;

public abstract class Attraction extends Building {
    protected int fun;
    protected int capacity;
    protected int runtime;
    protected int entryFee;
    protected boolean isRunning;
    
    public void upgrade(){
   
    }
    
    public void interact(){
        
    }
    
    //consider using an observer/event listener
    protected void start(){
        
    }
}
