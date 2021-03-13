package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;

public class SwingingShip extends Attraction  {
    
    public SwingingShip(){
        this.currentLevel = 1;
        this.buildingType = BuildType.SWINGINGSHIP;
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
        this.upgradeCost  = 0;
    }
        
    protected void start(){
        //TODO
    }
}
