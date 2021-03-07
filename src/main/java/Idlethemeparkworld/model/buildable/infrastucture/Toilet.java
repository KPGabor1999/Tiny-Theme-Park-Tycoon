package Idlethemeparkworld.model.buildable.infrastucture;

import Idlethemeparkworld.model.BuildType;

public class Toilet extends Infrastructure {
    private int cleanliness;
    
    public Toilet(){
        this.buildingType = BuildType.TOILET;
        this.name = "Toilet";
        this.buildingCost = 5000;
        this.capacity = 10;
        this.occupied = 0;
        this.cleanliness = 100;
    }

    public int getCleanliness() {
        return cleanliness;
    }


}
