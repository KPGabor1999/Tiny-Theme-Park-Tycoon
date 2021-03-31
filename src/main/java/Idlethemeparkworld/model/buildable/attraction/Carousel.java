package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.buildable.BuildingStatus;

public class Carousel extends Attraction {
    
    public Carousel(int x, int y, GameManager gm) {
        super(gm);
        this.x    = x;
        this.y    = y;
        this.buildingType = BuildType.CAROUSEL;
        this.value        = buildingType.getBuildCost();
        this.upgradeCost  = this.value*2;
        
        this.upkeepCost   = 11;
        this.fun          = 5;
        this.capacity     = 10;
        this.runtime      = 3;
        this.entryFee     = 8;
        
        this.status = BuildingStatus.OPEN;
        //this.stats = new AttractionStats(new int[] {10,20, 0,0, 0,0, 0,0, 35,5});
    }
    
    @Override
    public void level2Upgrade(){
        this.fun          *= 1.5;
        this.capacity     += 5;
        this.upgradeCost  *= 2;
    }
    
    @Override
    public void level3Upgrade(){
        this.fun          *= 1.5;
        this.capacity     += 5;
    }
}
