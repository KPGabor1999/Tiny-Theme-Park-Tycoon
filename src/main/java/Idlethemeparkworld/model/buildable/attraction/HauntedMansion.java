package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;

public class HauntedMansion extends Attraction {
    
    public HauntedMansion(int x, int y, GameManager gm) {
        super(gm);
        this.x    = x;
        this.y    = y;
        this.value        = BuildType.HAUNTEDMANSION.getBuildCost();
        this.buildingType = BuildType.HAUNTEDMANSION;
        this.upkeepCost   = 6*12;
        this.fun          = 20;
        this.capacity     = 10;
        this.runtime      = 15;
        this.entryFee     = 32;
        this.upgradeCost  = this.value*2;
    }
    
    @Override
    public void innerUpgrade(){
        switch(currentLevel){
            case 1: 
                this.fun          *= 1.5;
                this.capacity     += 5;
                this.upgradeCost  *= 2;
                break;
            case 2:
                this.fun          *= 1.5;
                this.capacity     += 5;
                break;
            default: break;
        }
    }
}
