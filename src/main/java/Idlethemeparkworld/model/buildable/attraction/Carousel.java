package Idlethemeparkworld.model.buildable.attraction;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.buildable.BuildingStatus;

public class Carousel extends Attraction {
    
    public Carousel(int x, int y){
        this.x    = x;
        this.y    = y;
        this.buildingType = BuildType.CAROUSEL;
        this.value        = buildingType.getBuildCost();
        this.currentLevel = 1;
        this.upgradeCost  = 1;
        
        this.upkeepCost   = 1;
        this.fun          = 1;
        this.capacity     = 1;
        this.runtime      = 1;
        this.entryFee     = 1;
        
        this.status = BuildingStatus.OPEN;
        this.stats = new AttractionStats(new int[] {10,20, 0,0, 0,0, 0,0, 35,5});
        
        this.occupied     = 0;
        this.condition    = 100;
        
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
        this.condition    = 100;
        this.value        += upgradeCost;
        this.upgradeCost  *= 100;
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
        this.condition    = 100;
        this.value        += upgradeCost;
        this.upgradeCost  = 0;
    }
    
    protected void start(){
        //TODO
    }
}
