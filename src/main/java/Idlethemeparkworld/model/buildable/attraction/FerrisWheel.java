package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;

public class FerrisWheel extends Attraction {
    
    public FerrisWheel(int xLocation, int yLocation){
        this.xLocation    = xLocation;
        this.yLocation    = yLocation;
        this.value        = BuildType.FERRISWHEEL.getBuildCost();
        this.currentLevel = 1;
        this.buildingType = BuildType.FERRISWHEEL;
        this.upkeepCost   = 1;
        this.fun          = 1;
        this.capacity     = 1;
        this.occupied     = 0;
        this.runtime      = 1;
        this.entryFee     = 1;
        this.isRunning    = false;
        this.condition    = 100;
        this.upgradeCost  = 1;
    }
    
    @Override
    public void level2Upgrade(){
        this.currentLevel += 1;
        this.upkeepCost   += 1;
        this.fun          += 1;
        this.capacity     += 1;
        this.occupied     = 0;
        this.runtime      = 1;
        this.entryFee     = 1;
        this.isRunning    = false;
        this.condition    = 100;
        this.value        += upgradeCost;
        this.upgradeCost  *= 1000000;
    }
    
    @Override
    public void level3Upgrade(){
        this.currentLevel += 1;
        this.upkeepCost   += 1;
        this.fun          += 1;
        this.capacity     += 1;
        this.occupied     = 0;
        this.runtime      = 1;
        this.entryFee     = 1;
        this.isRunning    = false;
        this.condition    = 100;
        this.value        += upgradeCost;
        this.upgradeCost  = 0;
    }
        
    protected void start(){
        //TODO
    }
}
