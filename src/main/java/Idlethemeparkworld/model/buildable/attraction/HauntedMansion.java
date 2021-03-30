package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;

public class HauntedMansion extends Attraction {
    
    public HauntedMansion(int x, int y, GameManager gm) {
        super(gm);
        this.x    = x;
        this.y    = y;
        this.value        = BuildType.HAUNTEDMANSION.getBuildCost();
        this.currentLevel = 1;
        this.buildingType = BuildType.HAUNTEDMANSION;
        this.upkeepCost   = 6 * 12;     //több beöltözött színész
        this.fun          = 20;
        this.capacity     = 10;
        this.occupied     = 0;
        this.runtime      = 15;
        this.entryFee     = 32;
        this.condition    = 100;
        this.upgradeCost  = this.value*2;
    }
    
    @Override
    public void level2Upgrade(){
        this.currentLevel += 1;
        this.fun          *= 1.5;
        this.capacity     += 5;
        this.condition    =  100;
        this.value        += upgradeCost;
        this.upgradeCost  *= 2;
    }
    
    @Override
    public void level3Upgrade(){
        this.currentLevel += 1;
        this.fun          *= 1.5;
        this.capacity     += 5;
        this.condition    = 100;
        this.value        += upgradeCost;
        this.upgradeCost  = 0;
    }
        
    protected void start(){
        //TODO
    }
}
