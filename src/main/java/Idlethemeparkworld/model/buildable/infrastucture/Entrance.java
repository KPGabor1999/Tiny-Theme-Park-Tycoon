package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;

public class Entrance extends Infrastructure {
    
    public Entrance(int xLocation, int yLocation){
        this.maxLevel = 0;
        this.currentLevel = 1;
        this.x = xLocation;
        this.y = yLocation;
        this.buildingType = BuildType.ENTRANCE;
    }
    
    @Override
    public void level2Upgrade(){}       //They're not meant to be upgradeable but they could be.
    
    @Override
    public void level3Upgrade(){}
}
