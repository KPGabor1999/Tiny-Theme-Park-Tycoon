package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.buildable.Building;

public abstract class Attraction extends Building {
    protected int fun;
    protected int capacity;
    protected int occupied;
    protected int runtime;
    protected int entryFee;
    protected boolean isRunning;
    protected int condition;

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
    
    public void increaseOccupied(int num){
        this.occupied += num;
    }
    
    //consider using an observer/event listener
    protected abstract void start();
}
