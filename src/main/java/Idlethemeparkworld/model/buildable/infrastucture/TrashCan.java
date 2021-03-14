package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;

public class TrashCan extends Infrastructure {
    private int capacity;
    private int filled;
    
    public TrashCan(int xLocation, int yLocation){
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.buildingType = BuildType.TRASHCAN;
        this.capacity = 30;
        this.filled = 0;
        this.value = BuildType.TRASHCAN.getBuildCost();
    }

    public int getCapacity() {
        return capacity;
    }

    public int getFilled() {
        return filled;
    }
    
        
    @Override
    public void level2Upgrade(){}       //They're not meant to be upgradeable but they could be.
    
    @Override
    public void level3Upgrade(){}
}
