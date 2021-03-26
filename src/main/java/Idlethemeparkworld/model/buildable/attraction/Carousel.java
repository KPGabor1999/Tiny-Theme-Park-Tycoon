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
        this.upgradeCost  = this.value*2;
        
        this.upkeepCost   = 11;
        this.fun          = 5;
        this.capacity     = 10;
        this.runtime      = 3;
        this.entryFee     = 8;
        
        this.status = BuildingStatus.OPEN;
        this.stats = new AttractionStats(new int[] {10,20, 0,0, 0,0, 0,0, 35,5});
        
        this.occupied     = 0;
        this.condition    = 100;
        
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
