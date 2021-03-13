package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;

public class Entrance extends Infrastructure {
    
    public Entrance(){
        this.buildingType = BuildType.ENTRANCE;
    }
    
    @Override
    public void level2Upgrade(){}       //They're not meant to be upgradeable but they could be.
    
    @Override
    public void level3Upgrade(){}
}
