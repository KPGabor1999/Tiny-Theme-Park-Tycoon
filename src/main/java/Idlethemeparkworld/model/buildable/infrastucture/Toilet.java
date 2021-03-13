package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;

public class Toilet extends Infrastructure {
    private int cleanliness;
    
    public Toilet(){
        this.buildingType = BuildType.TOILET;
        this.capacity = 10;
        this.occupied = 0;
        this.cleanliness = 100;
    }

    public int getCleanliness() {
        return cleanliness;
    }
        
    @Override
    public void level2Upgrade(){}       //They're not meant to be upgradeable but they could be.
    
    @Override
    public void level3Upgrade(){}

}
