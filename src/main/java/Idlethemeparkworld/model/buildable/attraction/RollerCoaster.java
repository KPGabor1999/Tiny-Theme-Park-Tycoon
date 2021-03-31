package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;

public class RollerCoaster extends Attraction {
    
    public RollerCoaster(int x, int y, GameManager gm) {
        super(gm);
        this.x    = x;
        this.y    = y;
        this.value        = BuildType.ROLLERCOASTER.getBuildCost();
        this.currentLevel = 1;
        this.buildingType = BuildType.ROLLERCOASTER;
        this.upkeepCost   = 13;
        this.fun          = 12;
        this.capacity     = 10;
        this.occupied     = 0;
        this.runtime      = 2;
        this.entryFee     = 24;
        this.condition    = 100;
        this.upgradeCost  = this.value*2;
    }
    
    @Override
    public void level2Upgrade(){
        this.currentLevel += 1;
        this.fun          *= 1.5;
        this.capacity     += 4;
        this.runtime      -=1;      //rövidül a menetidõ
        this.condition    =  100;
        this.value        += upgradeCost;
        this.upgradeCost  *= 2;
    }
    
    @Override
    public void level3Upgrade(){
        this.currentLevel += 1;
        this.fun          *= 1.5;
        this.capacity     += 4;
        this.condition    = 100;
        this.value        += upgradeCost;
        this.upgradeCost  = 0;
    }
        
    protected void start(){
        //TODO
    }
}
