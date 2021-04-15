package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;
import Idlethemeparkworld.model.GameManager;
import Idlethemeparkworld.model.buildable.BuildingStatus;

public class Pavement extends Infrastructure {
    
    public Pavement(int x, int y, GameManager gm) {
        super(gm);
        this.maxLevel = 0;
        this.x = x;
        this.y = y;
        this.buildingType = BuildType.PAVEMENT;
        this.value = BuildType.PAVEMENT.getBuildCost();
        this.littering = 0;
    }
    
    @Override
    public int getRecommendedMax(){
        return (status == BuildingStatus.OPEN || status == BuildingStatus.OPEN) ? 2 : 0;
    }
}
