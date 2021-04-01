package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;

public class SwingingShip extends Attraction  {
    
    public SwingingShip(int x, int y, GameManager gm) {
        super(gm);
        this.x    = x;
        this.y    = y;
        this.value        = BuildType.SWINGINGSHIP.getBuildCost();
        this.buildingType = BuildType.SWINGINGSHIP;
        this.upkeepCost   = 12;
        this.fun          = 9;
        this.capacity     = 45;
        this.runtime      = 3;
        this.entryFee     = 18;
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
