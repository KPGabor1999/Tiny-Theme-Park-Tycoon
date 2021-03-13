package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;

public class Pavement extends Infrastructure {
    
    public Pavement(int xLocation, int yLocation){
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.buildingType = BuildType.PAVEMENT;
    }
        
    @Override
    public void level2Upgrade(){}       //They're not meant to be upgradeable but they could be.
    
    @Override
    public void level3Upgrade(){}
}
