package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;

public class FerrisWheel extends Attraction {
    
    public FerrisWheel(int x, int y, GameManager gm) {
        super(gm);
        this.x    = x;
        this.y    = y;
        this.value        = BuildType.FERRISWHEEL.getBuildCost();
        this.buildingType = BuildType.FERRISWHEEL;
        this.upkeepCost   = 12;
        this.fun          = 9;
        this.capacity     = 40;
        this.runtime      = 9;
        this.entryFee     = 18;
        this.upgradeCost  = this.value*2;
    }
    
    @Override
    public void innerUpgrade(){
        switch(currentLevel){
            case 1: 
                this.fun          *= 1.5;
                this.capacity     += 20;
                this.upgradeCost  *= 2;
                break;
            case 2:
                this.fun          *= 1.5;
                this.capacity     += 20;
                break;
            default: break;
        }
    }
}
